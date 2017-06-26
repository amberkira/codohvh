package com.codo.amber_sleepeanuty.module_chat;

import com.codo.amber_sleepeanuty.library.base.BaseProvider;

/**
 * Created by amber_sleepeanuty on 2017/6/22.
 */

public class ChatProvider extends BaseProvider{
    @Override
    public void registerActions() {
        registerAction("Chat",new ChatAction());
        registerAction("Video",new VideoAction());
    }
}