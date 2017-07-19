package com.codo.amber_sleepeanuty.library.widget;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;

/**
 * Created by amber_sleepeanuty on 2017/6/12.
 */

public class HeadsetReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        switch (action) {
            //插入和拔出耳机会触发此广播
            case Intent.ACTION_HEADSET_PLUG:
                int state = intent.getIntExtra("state", 0);
                if (state == 1) {
                    MediaManager.changeToHeadsetMode();
                } else if (state == 0) {
                    MediaManager.onResume();
                }
                break;
            //拔出耳机会触发此广播,拔出不会触发,且此广播比上一个早,故可在此暂停播放,收到上一个广播时在恢复播放
            case AudioManager.ACTION_AUDIO_BECOMING_NOISY:
                MediaManager.onPause();

                MediaManager.changeToSpeakerMode();
                break;
            default:
                break;
        }
    }
}
