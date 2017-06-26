package com.codo.amber_sleepeanuty.library.bean;

import com.hyphenate.chat.EMMessage;

/**
 * Created by amber_sleepeanuty on 2017/6/22.
 */

public class IMMsgBean {
    private boolean isUnread;
    private EMMessage msg;

    public IMMsgBean(boolean isUnread, EMMessage msg) {
        this.isUnread = isUnread;
        this.msg = msg;
    }

    public boolean isUnread() {
        return isUnread;
    }

    public void setUnread(boolean unread) {
        isUnread = unread;
    }

    public EMMessage getMsg() {
        return msg;
    }

    public void setMsg(EMMessage msg) {
        this.msg = msg;
    }
}
