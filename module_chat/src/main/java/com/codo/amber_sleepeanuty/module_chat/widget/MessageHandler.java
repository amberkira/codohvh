package com.codo.amber_sleepeanuty.module_chat.widget;

import android.os.Handler;
import android.os.Message;

import com.codo.amber_sleepeanuty.module_chat.adapter.ImMessageAdapter;
import com.hyphenate.chat.EMMessage;

/**
 * Created by amber_sleepeanuty on 2017/6/22.
 */

public class MessageHandler extends Handler {
    public static final int LIST_REFRESH = 0x112;

    public ImMessageAdapter mAdapter;

    public MessageHandler(ImMessageAdapter adapter) {
        this.mAdapter = adapter;
    }



    @Override
    public void handleMessage(Message msg) {
        if (msg.what == LIST_REFRESH){
            mAdapter.addSingleMessage((EMMessage)msg.obj);
        }
    }
}
