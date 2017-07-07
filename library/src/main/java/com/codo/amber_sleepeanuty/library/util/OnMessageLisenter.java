package com.codo.amber_sleepeanuty.library.util;

/**
 * Created by amber_sleepeanuty on 2017/6/22.
 */

public interface OnMessageLisenter {
    public void sendVoiceMessage(float sec,String path);
    public void sendImg();
    public void takePhoto();
    public void cancelVoice();
    public void videConnect();
}
