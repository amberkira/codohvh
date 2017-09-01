package com.codo.amber_sleepeanuty.codohvh;


import android.content.Context;
import android.content.IntentFilter;
import android.util.Log;

import com.amber.module_info.InfoAppLogic;
import com.amber.module_info.InfoConnectService;
import com.codo.amber_sleepeanuty.library.CodoApplication;
import com.codo.amber_sleepeanuty.library.receiver.CallReceiver;
import com.codo.amber_sleepeanuty.library.router.WideRouter;
import com.codo.amber_sleepeanuty.library.util.LogUtil;
import com.codo.amber_sleepeanuty.library.util.ProcessNameUtil;
import com.codo.amber_sleepeanuty.module_chat.ChatAppLogic;
import com.codo.amber_sleepeanuty.module_chat.ChatConnectService;
import com.codo.amber_sleepeanuty.module_chat.ChatRoomActivity;
import com.codo.amber_sleepeanuty.module_login.LoginAppLogic;
import com.codo.amber_sleepeanuty.module_login.LoginConnectService;
import com.codo.amber_sleepeanuty.module_index.IndexAppLogic;
import com.codo.amber_sleepeanuty.module_index.IndexConnectService;
import com.codo.amber_sleepeanuty.module_multipleprocess.ProAppLogic;
import com.codo.amber_sleepeanuty.module_multipleprocess.ProConnectService;
import com.github.piasy.biv.BigImageViewer;
import com.github.piasy.biv.loader.glide.GlideImageLoader;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMOptions;
import com.umeng.message.IUmengRegisterCallback;
import com.umeng.message.PushAgent;

import java.util.logging.Logger;

/**
 * Created by amber_sleepeanuty on 2017/3/28.
 */

public class MainApplication extends CodoApplication {

    private String TAG = "APP_MAINAPPLICATION";
    public Context context;
    private int pid;
    private String processName;
    private CallReceiver callReceiver;


    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
        pid = ProcessNameUtil.getMyProcessId();
        processName = ProcessNameUtil.getProcessName(context,pid);
        initEaseMob();
        BigImageViewer.initialize(GlideImageLoader.with(context));

        PushAgent agent = PushAgent.getInstance(this);
        agent.register(new IUmengRegisterCallback() {
            @Override
            public void onSuccess(String s) {
                Log.e("Token",s);
            }

            @Override
            public void onFailure(String s, String s1) {
                Log.e("Token1",s);
                Log.e("Token2",s1);
            }
        });
    }

    private void initEaseMob() {
        if(processName==null||!processName.equalsIgnoreCase(context.getPackageName())){
            LogUtil.e(TAG, "enter the service process!");
            return;
        }
        EMOptions opts = initOpts();
        initSDk(opts);
        initReceiver();
    }

    private void initSDk(EMOptions opts) {
        EMClient.getInstance().init(context,opts);
        //开启环信debug模式--正式发布或其他调试设置false
        EMClient.getInstance().setDebugMode(true);
    }

    private EMOptions initOpts() {
        EMOptions opts = new EMOptions();
        //添加好友需要验证
        opts.setAcceptInvitationAlways(false);
        //进入组群需要验证
        opts.setAutoAcceptGroupInvitation(false);
        //退群后删除聊天记录
        opts.setDeleteMessagesAsExitGroup(true);
        //群聊最大人数
        opts.setGCMNumber("200");
        return opts;
    }

    private void initReceiver() {
        // 设置通话广播监听器
        IntentFilter callFilter = new IntentFilter(EMClient.getInstance().callManager().getIncomingCallBroadcastAction());
        if (callReceiver == null) {
            callReceiver = new CallReceiver();
        }
        //注册通话广播接收者
        this.registerReceiver(callReceiver, callFilter);
    }

    @Override
    public void initialLogicWithinSameProcess() {
        registerApplicationLogic("com.codo.amber_sleepeanuty.codohvh",1000,LoginAppLogic.class);
        registerApplicationLogic("com.codo.amber_sleepeanuty.codohvh",1000,IndexAppLogic.class);
        registerApplicationLogic("com.codo.amber_sleepeanuty.codohvh:Pro",1000,ProAppLogic.class);
        registerApplicationLogic("com.codo.amber_sleepeanuty.codohvh",1000,ChatAppLogic.class);
        registerApplicationLogic("com.codo.amber_sleepeanuty.codohvh",1000,InfoAppLogic.class);


    }

    @Override
    public void initAllProcessesRouter() {
        WideRouter.registerLocalConnectService("com.codo.amber_sleepeanuty.codohvh",MainRouterConnectService.class);
        WideRouter.registerLocalConnectService("com.codo.amber_sleepeanuty.codohvh:Login",LoginConnectService.class);
        WideRouter.registerLocalConnectService("com.codo.amber_sleepeanuty.codohvh:Index",IndexConnectService.class);
        WideRouter.registerLocalConnectService("com.codo.amber_sleepeanuty.codohvh:Pro",ProConnectService.class);
        WideRouter.registerLocalConnectService("com.codo.amber_sleepeanuty.codohvh:Chat",ChatConnectService.class);
        WideRouter.registerLocalConnectService("com.codo.amber_sleepeanuty.codohvh:Chat",InfoConnectService.class);

    }

}
