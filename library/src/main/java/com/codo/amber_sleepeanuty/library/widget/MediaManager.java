package com.codo.amber_sleepeanuty.library.widget;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Build;

import com.codo.amber_sleepeanuty.library.CodoApplication;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

/**
 * Created by amber_sleepeanuty on 2017/6/4.
 */

public class MediaManager {
    private static MediaManager mInstance;
    private static MediaPlayer mediaPlayer;
    private static AudioManager mAudioManager;

    /**
     * 外放模式
     */
    public static final int MODE_SPEAKER = 0;
    /**
     * 耳机模式
     */
    public static final int MODE_HEADSET = 1;
    /**
     * 听筒模式
     */
    public static final int MODE_EARPIECE = 2;


    private static int currentMode = MODE_SPEAKER;


    public static boolean isPause;

    private MediaManager() {
        initAudioManager();
    }

    public static MediaManager getInstance(){
        if(mInstance==null){
            synchronized (MediaManager.class){
                if(mInstance==null){
                    mInstance = new MediaManager();
                }
            }
        }
        return mInstance;
    }

    public static int getCurrentMode(){
        return currentMode;
    }

    public static void playVioce(String path, MediaPlayer.OnCompletionListener onCompletionListener){
        if(mediaPlayer==null){
            mediaPlayer = new MediaPlayer();
            mediaPlayer.setOnErrorListener(new MediaPlayer.OnErrorListener() {
                @Override
                public boolean onError(MediaPlayer mp, int what, int extra) {
                    mp.reset();
                    return false;
                }
            });
        }else {
            mediaPlayer.reset();
        }

        try {
            mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
            mediaPlayer.setOnCompletionListener(onCompletionListener);
            File file = new File(path);
            FileInputStream fis = new FileInputStream(file);
            mediaPlayer.setDataSource(fis.getFD());
            mediaPlayer.prepare();
            mediaPlayer.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //音频管理
    private static void initAudioManager() {
        mAudioManager = (AudioManager) CodoApplication.getCodoApplication().getSystemService(Context.AUDIO_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB){
            mAudioManager.setMode(AudioManager.MODE_IN_COMMUNICATION);
        } else {
            mAudioManager.setMode(AudioManager.MODE_IN_CALL);
        }
        mAudioManager.setSpeakerphoneOn(true);			//默认为扬声器播放
    }

    /**
     * 切换到听筒模式
     */
    public  static void changeToEarpieceMode(){
        currentMode = MODE_EARPIECE;
        mAudioManager.setSpeakerphoneOn(false);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB){
            mAudioManager.setStreamVolume(AudioManager.STREAM_MUSIC,
                    mAudioManager.getStreamMaxVolume(AudioManager.MODE_IN_COMMUNICATION), AudioManager.FX_KEY_CLICK);
        } else {
            mAudioManager.setStreamVolume(AudioManager.STREAM_MUSIC,
                    mAudioManager.getStreamMaxVolume(AudioManager.MODE_IN_CALL), AudioManager.FX_KEY_CLICK);
        }
    }
    /**
     * 切换到耳机模式
     */
    public static void changeToHeadsetMode(){
        currentMode = MODE_HEADSET;
        mAudioManager.setSpeakerphoneOn(false);
    }
    /**
     * 切换到外放模式
     */
    public static void changeToSpeakerMode(){
        currentMode = MODE_SPEAKER;
        mAudioManager.setSpeakerphoneOn(true);
    }
    public static void resetPlayMode(){
        if (mAudioManager.isWiredHeadsetOn()){
            changeToHeadsetMode();
        } else {
            changeToSpeakerMode();
        }
    }

    public static boolean isPlaying(){
        if(mediaPlayer!=null){
            return mediaPlayer.isPlaying();
        }
        return  false;
    }


    public static void onPause(){
        if(mediaPlayer!=null&&mediaPlayer.isPlaying()){
            mediaPlayer.pause();
            isPause = true;
        }
    }

    public static void onResume(){
        if(mediaPlayer!=null&&isPause){
            mediaPlayer.start();
            isPause = false;
        }
    }

    public static void release(){
        if(mediaPlayer!=null){
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }
}
