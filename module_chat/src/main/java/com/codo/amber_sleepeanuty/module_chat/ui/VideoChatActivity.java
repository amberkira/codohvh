package com.codo.amber_sleepeanuty.module_chat.ui;

import android.app.Activity;
import android.content.Intent;
import android.content.IntentFilter;
import android.hardware.Camera;
import android.media.AudioManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.codo.amber_sleepeanuty.library.Constant;
import com.codo.amber_sleepeanuty.library.widget.HeadsetReceiver;
import com.codo.amber_sleepeanuty.module_chat.R;
import com.hyphenate.chat.EMCallManager;
import com.hyphenate.chat.EMCallStateChangeListener;
import com.hyphenate.chat.EMClient;
import com.hyphenate.exceptions.EMNoActiveCallException;
import com.hyphenate.exceptions.EMServiceNotReadyException;
import com.hyphenate.exceptions.HyphenateException;
import com.hyphenate.media.EMLocalSurfaceView;
import com.hyphenate.media.EMOppositeSurfaceView;
import com.superrtc.sdk.VideoView;


/**
 * Created by amber_sleepeanuty on 2017/6/12.
 */

public class VideoChatActivity extends Activity implements View.OnClickListener{
    RelativeLayout surfaceContainrer;
    Chronometer videoTimer;
    Button btnVideoMute;
    Button btnVideoSwitchCamera;
    Button btnVideoReject;
    LinearLayout btnRejectContainer;
    Button btnVideoAnswer;
    LinearLayout btnAnswerContainer;



    //播放模式
    private static final int HEADSET = 0x100;//听筒
    private static final int EARPOD = 0x101;//耳机
    private static final int SPEAKER = 0x102;//外放
    //静音模式
    private static final int MUTE_ON = 0X301;
    private static final int MUTE_OFF = 0X302;

    //起始状态
    private int currentVoiceMode = SPEAKER;//默认当前播放模式为外放
    private int currentSamplingMode = MUTE_OFF;//默认不静音
    private int currentState;//通过启动intent获取,默认为来电
    private String easemobID;//通过启动intent获取
    private String toUser;//通过启动intent获取

    private HeadsetReceiver receiver;
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            if (msg.what ==1){
                setState(Constant.CALL_CONNECTING);
            }
            super.handleMessage(msg);
        }
    };

    //View变量
    private EMLocalSurfaceView localSurface = null;
    private EMOppositeSurfaceView oppositeSurface = null;
    private RelativeLayout.LayoutParams localParams = null;
    private RelativeLayout.LayoutParams oppositeParams = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_chat);

        Bundle bundle = getIntent().getExtras();
        currentState = bundle.getInt(Constant.VIDEO_CALL_TYPE,Constant.CALL_INCOMING);
        easemobID = bundle.getString(Constant.EASEMOB_ID);
        toUser = bundle.getString(Constant.USER_NICKNAME);

        initView();
        //切换现实状态
        setState(currentState);

        initSurfaceView();
        initConfig();
    }

    private void initView() {
        surfaceContainrer = (RelativeLayout) findViewById(R.id.surface_containrer);
        videoTimer = (Chronometer) findViewById(R.id.video_timer);
        btnVideoMute = (Button) findViewById(R.id.btn_video_mute);
        btnVideoSwitchCamera = (Button) findViewById(R.id.btn_video_switch_camera);
        btnVideoReject = (Button) findViewById(R.id.btn_video_reject);
        btnRejectContainer = (LinearLayout) findViewById(R.id.btn_reject_container);
        btnVideoAnswer= (Button) findViewById(R.id.btn_video_answer);
        btnAnswerContainer= (LinearLayout) findViewById(R.id.btn_answer_container);
        btnVideoMute.setOnClickListener(this);
        btnVideoSwitchCamera.setOnClickListener(this);
        btnVideoReject.setOnClickListener(this);
        btnVideoAnswer.setOnClickListener(this);
    }


    @Override
    protected void onStart() {
        super.onStart();
        receiver = new HeadsetReceiver();//耳机监控
        IntentFilter filter = new IntentFilter();
        filter.addAction(Intent.ACTION_HEADSET_PLUG);
        filter.addAction(AudioManager.ACTION_AUDIO_BECOMING_NOISY);
        registerReceiver(receiver, filter);
    }

    @Override
    protected void onStop() {
        super.onStop();
        unregisterReceiver(receiver);//耳机监控
    }

    private void initSurfaceView() {
        oppositeSurface = new EMOppositeSurfaceView(this);
        oppositeParams = new RelativeLayout.LayoutParams(0, 0);
        oppositeParams.width = RelativeLayout.LayoutParams.MATCH_PARENT;
        oppositeParams.height = RelativeLayout.LayoutParams.MATCH_PARENT;
        oppositeSurface.setLayoutParams(oppositeParams);
        surfaceContainrer.addView(oppositeSurface);

        // 初始化显示本地画面控件
        localSurface = new EMLocalSurfaceView(this);
        localParams = new RelativeLayout.LayoutParams(0, 0);
        localParams.width = RelativeLayout.LayoutParams.MATCH_PARENT;
        localParams.height = RelativeLayout.LayoutParams.MATCH_PARENT;
        localParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
        localSurface.setLayoutParams(localParams);
        surfaceContainrer.addView(localSurface);

        localSurface.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
            }
        });
        onCallSurface();
        localSurface.setZOrderOnTop(false);
        localSurface.setZOrderMediaOverlay(true);

        // 设置本地和远端画面的显示方式，是填充，还是居中
        localSurface.setScaleMode(VideoView.EMCallViewScaleMode.EMCallViewScaleModeAspectFill);
        oppositeSurface.setScaleMode(VideoView.EMCallViewScaleMode.EMCallViewScaleModeAspectFill);
        // 设置通话画面显示控件
        EMClient.getInstance().callManager().setSurfaceView(localSurface, oppositeSurface);
    }

    private void onCallSurface() {
        // 更新通话界面控件状态
        int width = 96*3;
        int height = 128*3;
        int rightMargin = 16*3;
        int topMargin =16*3;

        localParams = new RelativeLayout.LayoutParams(width, height);
        localParams.width = width;
        localParams.height = height;
        localParams.rightMargin = rightMargin;
        localParams.topMargin = topMargin;
        localParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
        localSurface.setLayoutParams(localParams);

        localSurface.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
            }
        });

        oppositeSurface.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
            }
        });
    }

    private void initConfig(){
        // 设置通话过程中对方如果离线是否发送离线推送通知，默认 false，这里需要和推送配合使用
        EMClient.getInstance().callManager().getCallOptions().setIsSendPushIfOffline(true);
        /**
         * 设置是否启用外部输入视频数据，默认 false，如果设置为 true，需要自己调用
         * {@link EMCallManager#inputExternalVideoData(byte[], int, int, int)}输入视频数据
         */
        EMClient.getInstance().callManager().getCallOptions().setEnableExternalVideoData(false);
        // 设置视频旋转角度，启动前和视频通话中均可设置
        //EMClient.getInstance().callManager().getCallOptions().setRotation(90);
        // 设置自动调节分辨率，默认为 true
        EMClient.getInstance().callManager().getCallOptions().enableFixedVideoResolution(true);
        // 设置视频通话最大和最小比特率，可以不用设置，比特率会根据分辨率进行计算，默认最大(800)， 默认最小(80)
        EMClient.getInstance().callManager().getCallOptions().setMaxVideoKbps(800);
        EMClient.getInstance().callManager().getCallOptions().setMinVideoKbps(150);
        // 设置视频通话分辨率 默认是(640, 480)
        EMClient.getInstance().callManager().getCallOptions().setVideoResolution(640, 480);
        // 设置通话最大帧率，SDK 最大支持(30)，默认(20)
        EMClient.getInstance().callManager().getCallOptions().setMaxVideoFrameRate(30);
        // 设置音视频通话采样率，一般不需要设置，除非采集声音有问题才需要手动设置
        EMClient.getInstance().callManager().getCallOptions().setAudioSampleRate(48000);
        // 设置录制视频采用 mov 编码 TODO 后期这个而接口需要移动到 EMCallOptions 中
        //EMClient.getInstance().callManager().getVideoCallHelper().setPreferMovFormatEnable(true);

        try {
            // 设置默认摄像头为前置
            EMClient.getInstance().callManager().setCameraFacing(Camera.CameraInfo.CAMERA_FACING_FRONT);
        } catch (HyphenateException e) {
            e.printStackTrace();
        }
        //呼叫对方
        if(currentState == Constant.CALL_DAILING){
            try {
                EMClient.getInstance().callManager().makeVideoCall(toUser);

            } catch (EMServiceNotReadyException e) {
                e.printStackTrace();
            }
        }

        EMClient.getInstance().callManager().addCallStateChangeListener(new EMCallStateChangeListener() {
            @Override
            public void onCallStateChanged(CallState callState, CallError error) {
                switch (callState) {
                    case CONNECTING: // 正在连接对方
                        break;
                    case CONNECTED: // 双方已经建立连接
                        Log.e("CALLLLLL","ED");
                        // TODO: 2017/6/14 显示来电人信息
                        break;

                    case ACCEPTED: // 电话接通成功
                        Log.e("CALLLLLLL","AC");
                        handler.sendEmptyMessage(1);
                        break;
                    case DISCONNECTED: // 电话断了
                        Toast.makeText(VideoChatActivity.this,"通话中断",Toast.LENGTH_LONG).show();
                        onFinish();
                        break;
                    case NETWORK_UNSTABLE: //网络不稳定
                        if(error == CallError.ERROR_NO_DATA){
                            //无通话数据
                        }else{
                        }
                        break;
                    case NETWORK_NORMAL: //网络恢复正常

                        break;
                    default:
                        break;
                }

            }
        });
    }

    //在正常模式下的视频状态界面设置
    public void setState(int state){
        if(state == Constant.CALL_CONNECTING){
            startTimer();
            currentState = Constant.CALL_CONNECTING;
            btnVideoAnswer.setVisibility(View.GONE);
            btnVideoMute.setVisibility(View.VISIBLE);
            btnVideoSwitchCamera.setVisibility(View.VISIBLE);
            videoTimer.setVisibility(View.VISIBLE);
            btnAnswerContainer.setVisibility(View.GONE);
            return;
        }
        if(state == Constant.CALL_DAILING){
            currentState = Constant.CALL_DAILING;
            btnVideoAnswer.setVisibility(View.GONE);
            btnVideoMute.setVisibility(View.GONE);
            btnVideoSwitchCamera.setVisibility(View.GONE);
            videoTimer.setVisibility(View.GONE);
            btnAnswerContainer.setVisibility(View.GONE);
            return;
        }
        if(state == Constant.CALL_INCOMING){
            currentState = Constant.CALL_INCOMING;
            btnVideoAnswer.setVisibility(View.VISIBLE);
            videoTimer.setVisibility(View.GONE);
            btnVideoMute.setVisibility(View.GONE);
            btnVideoSwitchCamera.setVisibility(View.GONE);
            btnAnswerContainer.setVisibility(View.VISIBLE);
            return;
        }
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.btn_video_mute){
            samplingModeSwitch();
            return;
        }
        if(v.getId() == R.id.btn_video_switch_camera){
            cameraSwitch();
            return;
        }
        if(v.getId() == R.id.btn_video_reject){
            rejectCall();
            return;
        }
        if(v.getId() == R.id.btn_video_answer){
            answerCall();
            return;
        }

    }

    private void answerCall() {

        try {
            EMClient.getInstance().callManager().answerCall();
            setState(Constant.CALL_CONNECTING);

        } catch (EMNoActiveCallException e) {
            e.printStackTrace();
        }
    }

    private void startTimer() {
        videoTimer.setBase(SystemClock.elapsedRealtime());
        videoTimer.start();
    }

    private void resetTimer() {
        videoTimer.stop();
    }

    private void rejectCall() {
        if(currentState == Constant.CALL_INCOMING){
            try {
                EMClient.getInstance().callManager().rejectCall();
            } catch (EMNoActiveCallException e) {
                e.printStackTrace();
            }
        }else if(currentState == Constant.CALL_CONNECTING){
            try {
                EMClient.getInstance().callManager().endCall();
            } catch (EMNoActiveCallException e) {
                e.printStackTrace();
            }
        }
        onFinish();
    }



    private void cameraSwitch() {
        try {
            if (EMClient.getInstance().callManager().getCameraFacing() == 1) {
                EMClient.getInstance().callManager().switchCamera();
                EMClient.getInstance().callManager().setCameraFacing(0);
            } else {
                EMClient.getInstance().callManager().switchCamera();
                EMClient.getInstance().callManager().setCameraFacing(1);
            }
        } catch (HyphenateException e) {
            e.printStackTrace();
        }
    }

    private void samplingModeSwitch() {
        if(currentSamplingMode==MUTE_OFF){
            currentSamplingMode = MUTE_ON;
            btnVideoMute.setBackgroundResource(R.mipmap.speak);
            try {
                EMClient.getInstance().callManager().pauseVoiceTransfer();
            } catch (HyphenateException e) {
                e.printStackTrace();
            }
        }else {
            currentSamplingMode = MUTE_OFF;
            btnVideoMute.setBackgroundResource(R.mipmap.mute);
            try {
                EMClient.getInstance().callManager().resumeVoiceTransfer();
            } catch (HyphenateException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        try {
            EMClient.getInstance().callManager().pauseVideoTransfer();
        } catch (HyphenateException e) {
            e.printStackTrace();
        }
    }

    private void onFinish(){
        resetTimer();
        currentSamplingMode = MUTE_OFF;
        finish();
    }


}
