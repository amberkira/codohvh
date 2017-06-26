package com.codo.amber_sleepeanuty.module_chat;

import com.codo.amber_sleepeanuty.library.CodoApplication;
import com.codo.amber_sleepeanuty.library.base.BaseAppLogic;
import com.codo.amber_sleepeanuty.library.router.LocalRouter;

/**
 * Created by amber_sleepeanuty on 2017/6/22.
 */

public class ChatAppLogic extends BaseAppLogic {
    @Override
    public void onCreate() {
        super.onCreate();
        LocalRouter.getInstance(CodoApplication.getCodoApplication()).registerProvider("Chat", new ChatProvider());
    }
}
