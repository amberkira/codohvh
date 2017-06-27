package com.codo.amber_sleepeanuty.library.ui;

/**
 * Created by amber_sleepeanuty on 2017/5/26.
 */

public interface OnOutputEventListener {
    public void sendTextMessage(String s);
    public void sendVoiceMessage(float sec,String path);
    public void sendImg();
    public void takePhoto();
    public void cancelVoice();
    public void videoCall();
}
