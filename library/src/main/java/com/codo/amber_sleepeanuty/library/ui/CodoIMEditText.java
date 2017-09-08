package com.codo.amber_sleepeanuty.library.ui;

import android.app.Activity;
import android.content.Context;
import android.os.Handler;
import android.os.Message;

import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewStub;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.codo.amber_sleepeanuty.library.CodoApplication;
import com.codo.amber_sleepeanuty.library.R;
import com.codo.amber_sleepeanuty.library.ui.OnOutputEventListener;
import com.codo.amber_sleepeanuty.library.widget.AudioRecordingManager;
import com.codo.amber_sleepeanuty.library.widget.VolumnDialogManager;


/**
 * Created by amber_sleepeanuty on 2017/5/25.
 */

public class CodoIMEditText extends LinearLayout implements View.OnClickListener,View.OnTouchListener, View.OnLongClickListener {

    public Context context;
    public View PlusView;
    public View VoiceView;
    public View InputView;
    public EditText InputEditText;
    public TextView BtnSend;
    public Button BtnVoice;
    public ImageView BtnVoiceInvoker;
    public ImageView BtnPlusInvoker;
    public ImageView BtnImg;
    public ImageView BtnVideoChat;
    public ImageView BtnPhoto;
    public OnOutputEventListener messageListener;

    private InputMethodManager imm;


    public VolumnDialogManager DialogManager;
    public AudioRecordingManager AudioManager;
    public float mTime;

    // TODO: 2017/6/22 变更path路径
    String filepath = CodoApplication.getCodoApplication().getFilesDir().toString()+"/"+"hj001_audios";

    private static final int MSG_PREPARED = 0x111;
    private static final int MSG_VOICE_CHANGE = 0x112;
    private static final int MSG_DIALOG_DISMISS = 0x113;


    private boolean isCollapse;
    private boolean isType;


    private BtnState currentBtnState = BtnState.Normal;
    private boolean isRecording;




    public enum BtnState{
        Normal,Recording,Cancel
    }

    public enum IMSTATE{
        Init,Plus,Voice,Edit,
    }


    public CodoIMEditText(Context context) {
        this(context,null);
    }

    public CodoIMEditText(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public CodoIMEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        this.context = context;
        final View v = LayoutInflater.from(context).inflate(R.layout.im_input_layout, this);
        imm = (InputMethodManager) this.context.getSystemService(Context.INPUT_METHOD_SERVICE);
        setState(IMSTATE.Init);
        isCollapse =true;
        isType = true;

        DialogManager = new VolumnDialogManager(v.getContext());
        AudioManager = AudioRecordingManager.getInstance(filepath);
        AudioManager.setAudioStateListener(new AudioRecordingManager.AudioStateListener() {
            @Override
            public void wellPrepared() {
                handler.sendEmptyMessage(MSG_PREPARED);
            }
        });
        BtnVoiceInvoker = (ImageView) v.findViewById(R.id.btn_voice_onoff);
        BtnPlusInvoker = (ImageView) v.findViewById(R.id.btn_im_plus);
        BtnSend = (TextView) findViewById(R.id.btn_im_send);
        BtnVoiceInvoker.setOnClickListener(this);
        BtnPlusInvoker.setOnClickListener(this);
        BtnSend.setOnClickListener(this);


    }

    public void setOnOutputEventListener(OnOutputEventListener listener){
        this.messageListener = listener;
    }


    public void setState(IMSTATE state){
        switch (state){
            case Init:{
                if(InputView==null){

                    ViewStub stub = (ViewStub) findViewById(R.id.voice_dissmis);
                    InputView = stub.inflate();
                    InputEditText = (EditText) InputView.findViewById(R.id.edt_im_input);
                    InputEditText.setFocusable(true);
                    InputEditText.setFocusableInTouchMode(true);
                    InputEditText.requestFocusFromTouch();
                    InputEditText.setOnFocusChangeListener(new OnFocusChangeListener() {
                        @Override
                        public void onFocusChange(View v, boolean hasFocus) {
                            if (hasFocus&&v.getId()==R.id.edt_im_input){
                                isCollapse = true;
                                setState(IMSTATE.Edit);
                            }
                        }
                    });
                    closeInputMethod(this);
                }else{
                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            InputView.setVisibility(VISIBLE);
                        }
                    },50);
                }
                if(VoiceView!=null){
                    VoiceView.setVisibility(GONE);
                }
                if(PlusView!=null){
                    PlusView.setVisibility(GONE);
                }
                break;
            }

            case Edit:{
                if(InputView==null){
                    ViewStub stub = (ViewStub) findViewById(R.id.voice_dissmis);
                    InputView = stub.inflate();
                    InputEditText = (EditText) InputView.findViewById(R.id.edt_im_input);
                }else {
                    InputView.setVisibility(VISIBLE);
                }
                if(VoiceView!=null){
                    VoiceView.setVisibility(GONE);
                }
                if(PlusView!=null){
                    PlusView.setVisibility(GONE);
                }
                break;
            }

            case Voice:{
                if(VoiceView==null){
                    ViewStub stub = (ViewStub) findViewById(R.id.voice_invoke);
                    VoiceView = stub.inflate();
                    BtnVoice = (Button) VoiceView.findViewById(R.id.btn_im_voice_speaker);
                    BtnVoice.setOnTouchListener(this);
                    BtnVoice.setOnLongClickListener(this);
                    VoiceView.setVisibility(VISIBLE);
                }else {
                    VoiceView.setVisibility(VISIBLE);
                }
                if(PlusView!=null){
                    PlusView.setVisibility(GONE);
                }
                if(InputView!=null){
                    closeInputMethod(this);
                    InputView.setVisibility(GONE);
                }
                break;
            }

            case Plus:{
                if(PlusView==null){
                    ViewStub stub = (ViewStub) findViewById(R.id.im_plus_layout);
                    PlusView = stub.inflate();
                    BtnVideoChat = (ImageView) PlusView.findViewById(R.id.im_plus_video);
                    BtnImg = (ImageView) PlusView.findViewById(R.id.im_plus_pic);
                    BtnPhoto = (ImageView) PlusView.findViewById(R.id.im_plus_photo);
                    BtnVideoChat.setOnClickListener(this);
                    BtnImg.setOnClickListener(this);
                    BtnPhoto.setOnClickListener(this);
                }else {
                    PlusView.setVisibility(VISIBLE);
                }
                if(VoiceView!=null){
                    VoiceView.setVisibility(GONE);
                }
                if(InputView!=null){
                    closeInputMethod(this);
                    InputView.setVisibility(VISIBLE);
                }
                break;
            }
        }
    }

    public void closeInputMethod(View v) {
        boolean isOpen = imm.isActive();
        if (isOpen) {
            imm.hideSoftInputFromWindow(v.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
            InputEditText.clearFocus();
        }
    }


    /**
     * 点击事件咯
     * @param v
     */
    @Override
    public void onClick(View v) {
        int resID = v.getId();
        //点击发送
        if (resID == R.id.btn_im_send) {
            if (messageListener == null) {
                throw new NullPointerException("请设置OnMessageListener");
            }
            if(msgGet()!=null&&msgGet().length()>0){
                messageListener.sendTextMessage(msgGet());
                msgClear();
                return;
            }
        }

        //点击切换语音输入按钮
        if (resID == R.id.btn_voice_onoff) {
            isCollapse = true;
            if (isType) {
                setState(IMSTATE.Voice);
                isType = false;
            } else {
                setState(IMSTATE.Edit);
                isType = true;
            }
            return;
        }
        //点击额外功能按钮
        if (resID == R.id.btn_im_plus) {
            isType = true;
            if (isCollapse) {
                setState(IMSTATE.Plus);
                isCollapse = false;
            } else {
                setState(IMSTATE.Edit);
                isCollapse = true;
            }
            return;
        }
        //点击额外功能中视频聊天功能
        if (resID == R.id.im_plus_video) {
            if (messageListener != null) {
                messageListener.videoCall();
            } else {
                throw new NullPointerException("请设置OnMessageListener");
            }
            return;
        }
        //
        if (resID == R.id.im_plus_photo){
            if (messageListener != null) {
                messageListener.takePhoto();
            } else {
                throw new NullPointerException("请设置OnMessageListener");
            }
            return;
        }

        //点击额外功能中发送相机图片功能
        if (resID == R.id.im_plus_pic) {
            if (messageListener != null) {
                messageListener.sendImg();
            } else {
                throw new NullPointerException("请设置OnMessageListener");
            }
            return;
        }
    }

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            if(msg.what==MSG_PREPARED){
                isRecording = true;
                DialogManager.showRecording();
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        while(isRecording){
                            try {
                                Thread.sleep(100);
                                mTime+=0.1f;
                                handler.sendEmptyMessage(MSG_VOICE_CHANGE);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }).start();
                return;
            }
            if(msg.what==MSG_VOICE_CHANGE&&currentBtnState==BtnState.Recording){
                DialogManager.refreshVoice(AudioManager.getVioceLevel(7));
                return;
            }
            if(msg.what==MSG_DIALOG_DISMISS){
                DialogManager.dismiss();
                return;
            }
        }
    };

    @Override
    public boolean onLongClick(View v) {
        DialogManager.init();
        AudioManager.prepareAudio();
        return false;
    }

    //语音聊天手势
    @Override
    public boolean onTouch(View v, MotionEvent event) {
        float y = 0f;
        float dy;
        float sloop = 100f;
        if(v.getId()==R.id.btn_im_voice_speaker){

            if (event.getAction()==MotionEvent.ACTION_DOWN){
                currentBtnState = BtnState.Recording;
                y = event.getY();
                BtnVoice.setText("松开 发送");
            }
            if(event.getAction()==MotionEvent.ACTION_MOVE){
                dy = y-event.getY();
                if(isRecording){
                    if(sloop<=dy){
                        currentBtnState = BtnState.Cancel;
                        BtnVoice.setText("松手，取消发送");
                        DialogManager.showCancel();
                    }else{
                        currentBtnState = BtnState.Recording;
                        BtnVoice.setText("松手 发送");
                        DialogManager.showRecording();
                    }
                }
            }
            if (event.getAction()==MotionEvent.ACTION_UP){
                if(messageListener==null){
                    throw new NullPointerException("请设置OnMessageListener");
                }
                if(isRecording&&mTime>0.6f){
                    if(currentBtnState==BtnState.Cancel){
                        messageListener.cancelVoice();
                        AudioManager.cancle();
                    }else if(currentBtnState==BtnState.Recording){
                        String s = "";
                        messageListener.sendVoiceMessage(mTime,AudioManager.getCurrentPath());
                        AudioManager.release();
                    }
                    DialogManager.dismiss();
                }else {
                    DialogManager.showTooShort();
                    if(isRecording){
                        AudioManager.cancle();
                    }
                    handler.sendEmptyMessageDelayed(MSG_DIALOG_DISMISS,1300);
                }

                reset();
            }
        }
        return false;
    }

    private void reset(){
        isRecording = false;
        mTime = 0f;
        currentBtnState = BtnState.Normal;
        BtnVoice.setText("按住 说话");

    }

    public void msgClear(){
        InputEditText.setText("");
    }

    public String msgGet(){
        return InputEditText.getText().toString();
    }



}
