package com.codo.amber_sleepeanuty.library.widget;

import android.media.AudioManager;
import android.media.MediaRecorder;
import android.util.Log;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

/**
 * Created by amber_sleepeanuty on 2017/6/22.
 */

public class AudioRecordingManager {
    private static AudioRecordingManager manager;

    private MediaRecorder mMediaRecorder;
    private AudioManager mAudioManager;
    private String mDir;
    private String mCurrentFilePath;
    private boolean isPrepared;


    public  AudioStateListener mListener;

    public String getCurrentPath() {
        return mCurrentFilePath;
    }


    public interface AudioStateListener{
        void wellPrepared();
    }

    private AudioRecordingManager(String dir) {
        mDir = dir;
    }

    public static AudioRecordingManager getInstance(String dir){
        if (manager==null){
            synchronized (AudioRecordingManager.class)
            {
                if(manager==null){
                    manager = new AudioRecordingManager(dir);
                }
            }
        }

        return manager;
    }

    public void setAudioStateListener(AudioStateListener listener){
        mListener = listener;
    }

    public void prepareAudio(){
        Log.e("Audio","enter Audio");
        try {
            isPrepared = false;
            File dir = new File(mDir);
            if(!dir.exists()){
                dir.mkdirs();
            }
            String fileName = generateFileName()+".amr";
            File file = new File(dir,fileName);
            mCurrentFilePath = file.getAbsolutePath();
            mMediaRecorder = new MediaRecorder();

            mMediaRecorder.setAudioSource(MediaRecorder.AudioSource.DEFAULT);
            mMediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.AMR_NB);
            mMediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
            mMediaRecorder.setOutputFile(mCurrentFilePath);
            mMediaRecorder.prepare();
            mMediaRecorder.start();
            isPrepared = true;
            if(mListener!=null){
                mListener.wellPrepared();
            }


        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String generateFileName() {
        return UUID.randomUUID().toString();
    }

    public int getVioceLevel(int maxVoice){
        if(isPrepared){
            try {
                return maxVoice*mMediaRecorder.getMaxAmplitude()/32768+1;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return 1;
    }

    public void release(){
        mMediaRecorder.stop();
        mMediaRecorder.release();
        mMediaRecorder = null;
    }

    public void cancle(){
        release();
        if(mCurrentFilePath!=null){
            File file = new File(mCurrentFilePath);
            file.delete();
            mCurrentFilePath = null;
        }

    }
}
