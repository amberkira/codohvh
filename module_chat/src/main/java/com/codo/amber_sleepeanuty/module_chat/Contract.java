package com.codo.amber_sleepeanuty.module_chat;

import com.hyphenate.chat.EMMessage;

import java.util.ArrayList;

/**
 * Created by amber_sleepeanuty on 2017/6/22.
 */

public class Contract {
    public interface IChatView{
        void notifyNewMsg();
        void hidenSoftKeyboard();
        void showProgress();
        void dismissProgress();
        void loadMore();
        void fetchReadedMesssage(int count);
        void notifyMsgsended(EMMessage msg);

        void notifyMsgsendError(String obj);
    }

    public interface IChatModel{

    }


}
