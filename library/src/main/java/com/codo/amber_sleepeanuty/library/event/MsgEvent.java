package com.codo.amber_sleepeanuty.library.event;

import com.codo.amber_sleepeanuty.library.base.BaseEvent;

/**
 * Created by amber_sleepeanuty on 2017/6/30.
 */

public class MsgEvent extends BaseEvent<MsgEvent.MsgStates> {

    public MsgEvent(MsgStates msgStates) {
        super(msgStates);
    }

    public enum MsgStates{
        Received,
        Sended,
        Pending,
        Cancel,
        Error,
        Read,
        Unread
    }

}
