package com.codo.amber_sleepeanuty.library.event;

import com.codo.amber_sleepeanuty.library.base.BaseEvent;
import com.codo.amber_sleepeanuty.library.bean.LoginBean;

/**
 * Created by amber_sleepeanuty on 2017/6/30.
 * 登陆事件之后开始初始化的事件标志
 * 需要缓存好友列表以及好友头像
 *    缓存聊天记录
 */

public class LoginEvent extends BaseEvent<LoginBean> {


    public LoginEvent(LoginBean loginBean) {
        super(loginBean);
    }
}
