package com.codo.amber_sleepeanuty.library.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;

import com.codo.amber_sleepeanuty.library.ActionResult;
import com.codo.amber_sleepeanuty.library.CodoApplication;
import com.codo.amber_sleepeanuty.library.IWideRouterAIDL;
import com.codo.amber_sleepeanuty.library.RouterRequest;
import com.codo.amber_sleepeanuty.library.router.WideRouter;
import com.codo.amber_sleepeanuty.library.util.LogUtil;

/**
 * Created by amber_sleepeanuty on 2017/3/16.
 */

public class WideConnectService extends Service {

    @Override
    public IBinder onBind(Intent intent) {
        String domain = intent.getStringExtra("domain");
        LogUtil.d("onBind domain:"+domain);
        if (WideRouter.getInstance(CodoApplication.getCodoApplication()).isStop) {
            return null;
        }
        if (domain != null && domain.length() > 0) {
            boolean hasRegistered = WideRouter.getInstance(CodoApplication.getCodoApplication()).checkLocalRouterHasRegistered(domain);
            if (!hasRegistered) {
                LogUtil.e("LogUtil", "Bind error: The local router of process " + domain + " is not bidirectional." +
                        "\nPlease create a Service extend LocalRouterConnectService then register it in AndroidManifest.xml and the initializeAllProcessRouter method of MaApplication." +
                        "\nFor example:" +
                        "\n<service android:name=\"XXXConnectService\" android:process=\"your process name\"/>" +
                        "\nWideRouter.registerLocalRouter(\"your process name\",XXXConnectService.class);");
                return null;
            }
            WideRouter.getInstance(CodoApplication.getCodoApplication()).connectLocalRouter(domain);
        } else {
            LogUtil.e("LogUtil", "Bind error: Intent do not have \"domain\" extra!");
            return null;
        }
        return stub;
    }

    IWideRouterAIDL.Stub stub = new IWideRouterAIDL.Stub() {
        @Override
        public boolean respondAsync(RouterRequest request) throws RemoteException {
            return WideRouter.getInstance(CodoApplication.getCodoApplication()).answerLocalRouterActionAsync(request.getDomain(),request);
        }

        @Override
        public ActionResult route(RouterRequest request) throws RemoteException {
            return WideRouter.getInstance(CodoApplication.getCodoApplication()).route(request.getDomain(),request);
        }

        @Override
        public void stopWideRouter() throws RemoteException {
            WideRouter.getInstance(CodoApplication.getCodoApplication()).stopSelf();
        }
    };
}
