package com.codo.amber_sleepeanuty.library.bean;

import com.codo.amber_sleepeanuty.library.event.MsgEvent;
import com.hyphenate.chat.EMMessage;

/**
 * Created by amber_sleepeanuty on 2017/6/30.
 */

public class MsgContainStatesBean {
    private MsgEvent.MsgStates state;
    private EMMessage msg;

    public MsgContainStatesBean() {
    }

    public MsgContainStatesBean(MsgEvent.MsgStates state, EMMessage msg) {
        this.state = state;
        this.msg = msg;
    }

    public MsgEvent.MsgStates getState() {
        return state;
    }

    public void setState(MsgEvent.MsgStates state) {
        this.state = state;
    }

    public EMMessage getMsg() {
        return msg;
    }

    public void setMsg(EMMessage msg) {
        this.msg = msg;
    }
}
