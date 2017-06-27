package com.codo.amber_sleepeanuty.module_chat;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.AudioManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.PowerManager;
import android.provider.MediaStore;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.codo.amber_sleepeanuty.library.CodoApplication;
import com.codo.amber_sleepeanuty.library.Constant;
import com.codo.amber_sleepeanuty.library.ui.CodoIMEditText;
import com.codo.amber_sleepeanuty.library.ui.OnOutputEventListener;
import com.codo.amber_sleepeanuty.library.util.FileProvider7;
import com.codo.amber_sleepeanuty.library.util.ImageLoader;
import com.codo.amber_sleepeanuty.library.util.LogUtil;
import com.codo.amber_sleepeanuty.library.widget.HeadsetReceiver;
import com.codo.amber_sleepeanuty.library.widget.MediaManager;
import com.codo.amber_sleepeanuty.module_chat.adapter.ImMessageAdapter;
import com.codo.amber_sleepeanuty.module_chat.presenter.ChatPresenter;
import com.codo.amber_sleepeanuty.module_chat.ui.PersonalIMDetailActivity;
import com.codo.amber_sleepeanuty.module_chat.widget.ViewHolderHelper;
import com.hyphenate.EMMessageListener;
import com.hyphenate.chat.EMChatManager;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMConversation;
import com.hyphenate.chat.EMImageMessageBody;
import com.hyphenate.chat.EMMessage;
import com.hyphenate.chat.EMVoiceMessageBody;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


public class ChatRoomActivity extends Activity implements OnOutputEventListener, SensorEventListener, ViewHolderHelper,Contract.IChatView,View.OnClickListener,SwipeRefreshLayout.OnRefreshListener{

    Context mContext;
    TextView mTitleBarName;
    TextView mBack;
    ImageButton mBtnUserInfo;
    CodoIMEditText mInput;
    RecyclerView mRecyclerView;
    ImMessageAdapter mImMessageAdapter;
    SwipeRefreshLayout mRefreshLayout;
    private EMMessageListener mEMMessageListener;
    private ArrayList<EMMessage> mMessageList;



    ChatPresenter mPresenter;

    private PowerManager powerManager;
    private PowerManager.WakeLock wakeLock;
    private SensorManager sensorManager;
    private Sensor sensor;
    private HeadsetReceiver receiver;

    private static final int LIST_REFRESH = 0x112;
    private static final int GARLLEY_SEND = 0x113;
    private static final int CAPTURE_PHOTO_SEND = 0x114;


    private String toUser;
    private String eseamobId;
    private int chatType;
    private String mImgPath;
    private boolean isHeaded;//记录聊天记录是否加载到头
    private EMConversation mPreloadConversation;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LogUtil.e("Activity onCreate");
        setContentView(R.layout.activity_chat_room);
        initView();
        initConfig();
        MediaManager.getInstance();


    }

    private void initView() {
        mContext = this;
        mPresenter = new ChatPresenter(this);
        //初始化界面
        mTitleBarName = (TextView) findViewById(R.id.titlebar_name);

        mBack = (TextView) findViewById(R.id.titlebar_back);
        mBack.setOnClickListener(this);

        mBtnUserInfo = (ImageButton) findViewById(R.id.titlebar_inform);
        mBtnUserInfo.setOnClickListener(this);

        mInput = (CodoIMEditText) findViewById(R.id.input);
        mInput.setOnOutputEventListener(this);

        mRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipelayout_message_list);
        mRefreshLayout.setColorSchemeResources(R.color.colorPrimary);
        mRefreshLayout.setOnRefreshListener(this);

        mRecyclerView = (RecyclerView) findViewById(R.id.rv_message_list);
        mMessageList = new ArrayList<>();
        mImMessageAdapter = new ImMessageAdapter(mContext,mMessageList);
        mImMessageAdapter.setViewHolderHelper(this);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(manager);
        mRecyclerView.setAdapter(mImMessageAdapter);
        mRecyclerView.setOnFlingListener(new RecyclerView.OnFlingListener() {
            @Override
            public boolean onFling(int velocityX, int velocityY) {
                if (velocityY != 0) {
                    hidenSoftKeyboard();
                }
                return false;
            }
        });
    }

    /**
     * 初始化聊天室属性
     */
    private void initConfig(){
        mImgPath = null;
        Bundle bundle = getIntent().getExtras();
        chatType = bundle.getInt(Constant.CHAT_TYPE,Constant.CHAT_TYPE_SINGLE);
        LogUtil.e(chatType+"");
        toUser = bundle.getString(Constant.CHAT_ROOM_NAME);
        LogUtil.e(toUser+"");

        eseamobId = bundle.getString(Constant.CHAT_ROOM_ID);
        LogUtil.e(eseamobId+"");

        mTitleBarName.setText(toUser);

        isHeaded = false;
        mPreloadConversation = EMClient.getInstance().chatManager().getConversation(eseamobId);
        mMessageList = (ArrayList<EMMessage>) mPreloadConversation.getAllMessages();
        LogUtil.e("list size:"+mMessageList.size()+"");

        mImMessageAdapter.preloadMessages(mPreloadConversation.getUnreadMsgCount(),mMessageList);
        mImMessageAdapter.notifyDataSetChanged();



    }

    @Override
    protected void onStart() {
        super.onStart();
        LogUtil.e("Activity onStart");

        //光感监控初始化xs
        powerManager = (PowerManager) getSystemService(POWER_SERVICE);
        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        sensor = sensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY);
        sensorManager.registerListener(this, sensor, SensorManager.SENSOR_DELAY_NORMAL);
        
        //耳机监控初始化
        receiver = new HeadsetReceiver();
        IntentFilter filter = new IntentFilter();
        filter.addAction(Intent.ACTION_HEADSET_PLUG);
        filter.addAction(AudioManager.ACTION_AUDIO_BECOMING_NOISY);
        registerReceiver(receiver, filter);
        
        //新消息监听初始化
        mEMMessageListener = new EMMessageListener() {
            @Override
            public void onMessageReceived(java.util.List<EMMessage> messages) {
                // TODO: 2017/6/23 调用presenter等 
            }

            @Override
            public void onCmdMessageReceived(List<EMMessage> messages) {

            }

            @Override
            public void onMessageRead(List<EMMessage> messages) {

            }

            @Override
            public void onMessageDelivered(List<EMMessage> messages) {

            }

            @Override
            public void onMessageChanged(EMMessage message, Object change) {

            }
        };
        EMChatManager m = EMClient.getInstance().chatManager();
        EMClient.getInstance().chatManager().addMessageListener(mEMMessageListener);
    }

    @Override
    protected void onStop() {
        super.onStop();
        LogUtil.e("Activity onStop");

        sensorManager.unregisterListener(this);
        unregisterReceiver(receiver);
    }



    @Override
    public void onClick(View v) {

        if(v.getId()==R.id.titlebar_inform){
            Intent it = new Intent(ChatRoomActivity.this, PersonalIMDetailActivity.class);
            startActivity(it);
        }
        if(v.getId() == R.id.titlebar_back){
            finish();
        }
    }
    

    @Override
    public void sendTextMessage(String s) {
        mPresenter.sendTxtMessage(s,eseamobId);
    }

    @Override
    public void sendVoiceMessage(float sec, String path) {
        if(chatType==Constant.CHAT_TYPE_SINGLE){
            mPresenter.sendVoice(sec,path,eseamobId);
        }

    }

    @Override
    public void sendImg() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, GARLLEY_SEND);
    }

    @Override
    public void takePhoto() {
        mImgPath = Environment.getExternalStorageDirectory().toString() + "/" + UUID.randomUUID() + ".png";
        //mImgPath = CodoApplication.getCodoApplication().getFilesDir().toString();
        File mPhotoFile = new File(mImgPath);
        Uri fileUri = FileProvider7.getUrifromFile(this,mPhotoFile);
        Intent captureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        captureIntent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);
        startActivityForResult(captureIntent,CAPTURE_PHOTO_SEND);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode == RESULT_OK){
            if(requestCode == GARLLEY_SEND){
                Uri uri = data.getData();
                mImgPath = ImageLoader.getImageAbsolutePath(this,uri);
                mPresenter.sendImg(mImgPath,true,eseamobId);
            }
            if(requestCode == CAPTURE_PHOTO_SEND){
                mPresenter.sendImg(mImgPath,true,eseamobId);
            }
        }
    }

    @Override
    public void cancelVoice() {

    }

    @Override
    public void videoCall() {
        mPresenter.videoCall(toUser,eseamobId);
    }

    @Override
    public void notifyNewMsg() {

    }

    @Override
    public void hidenSoftKeyboard() {
        mInput.setState(CodoIMEditText.IMSTATE.Edit);
        mInput.closeInputMethod(mInput);
    }

    @Override
    public void showProgress() {

    }

    @Override
    public void dismissProgress() {

    }

    @Override
    public void loadMore() {

    }

    @Override
    public void fetchReadedMesssage(int count) {
        LogUtil.e("fetchReadedMesssage");
        if(mMessageList == null || mMessageList.isEmpty()){
            throw new NullPointerException("messagelist 为空，检查初始化是否正常或聊天对象id是否正确");
        }

        String lastMsgID = mImMessageAdapter.getPreloadStartID();
        ArrayList<EMMessage> result = mPresenter.fetchReadedMesssage(eseamobId,lastMsgID,count);
        if(result.size()<count){
            isHeaded = true;
        }
        mImMessageAdapter.addMutipleMessages(0,result);
        mRefreshLayout.setRefreshing(false);
    }

    @Override
    public void notifyMsgsended(EMMessage msg) {
        mImMessageAdapter.addSingleMessage(msg);
        mRecyclerView.scrollToPosition(mImMessageAdapter.getItemCount()-1+mImMessageAdapter.getHeader());
    }

    @Override
    public void notifyMsgsendError(String obj) {
        Toast.makeText(this,obj,Toast.LENGTH_SHORT).show();
    }

    @Override
    public void HandleVoiceClickEvent(EMVoiceMessageBody body) {
        mPresenter.handleVoiceClickEvent(body);
    }

    @Override
    public void HandleImgClickEvent(EMImageMessageBody body) {
        mPresenter.handleImgClickEvent(body);
    }

    /**
     * swiperefreshlayout Listener func
     */
    @Override
    public void onRefresh() {

        if(mRefreshLayout!=null&&!isHeaded){
            fetchReadedMesssage(10);
        }else {
            mRefreshLayout.setRefreshing(false);
            Toast.makeText(this,"已经加载到头啦！",Toast.LENGTH_SHORT).show();
        }
    }


    /**
     * 屏幕亮度控制／耳机监控
     * @param event
     */
    @Override
    public void onSensorChanged(SensorEvent event) {
        if (MediaManager.getCurrentMode() == MediaManager.MODE_HEADSET) {
            return;
        }
        float value = event.values[0];

        if (MediaManager.isPlaying()) {
            if (value == sensor.getMaximumRange()) {
                MediaManager.changeToSpeakerMode();
                setScreenOn();
            } else {
                MediaManager.changeToEarpieceMode();
                setScreenOff();
            }
        } else {
            if (value == sensor.getMaximumRange()) {
                MediaManager.changeToSpeakerMode();
                setScreenOn();
            }
        }
    }

    private void setScreenOff() {
        if (wakeLock == null) {
            wakeLock = powerManager.newWakeLock(PowerManager.PROXIMITY_SCREEN_OFF_WAKE_LOCK, "Main");
        }
        wakeLock.acquire();
    }

    private void setScreenOn() {
        if (wakeLock != null) {
            wakeLock.setReferenceCounted(false);
            wakeLock.release();
            wakeLock = null;
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    @Override
    protected void onDestroy() {
        LogUtil.e("Activity onDestroy");

        super.onDestroy();
    }

    @Override
    protected void onPause() {
        LogUtil.e("Activity onPause");
        EMClient.getInstance().chatManager().markAllConversationsAsRead();
        super.onPause();
    }

    @Override
    protected void onResume() {
        LogUtil.e("Activity onResume");

        super.onResume();
    }

    @Override
    protected void onRestart() {
        LogUtil.e("Activity onRestart");
        super.onRestart();
    }
}
