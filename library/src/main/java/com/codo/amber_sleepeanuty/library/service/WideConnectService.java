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

/**
 * Created by amber_sleepeanuty on 2017/3/16.
 */

public class WideConnectService extends Service {

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    IWideRouterAIDL.Stub stub = new IWideRouterAIDL.Stub() {
        @Override
        public boolean respondAsync(RouterRequest request) throws RemoteException {
            return WideRouter.getInstence(CodoApplication.getCodoApplication()).answerLocalRouterActionAsync(request.getDomain(),request);
        }

        @Override
        public ActionResult route(RouterRequest request) throws RemoteException {
            return WideRouter.getInstence(CodoApplication.getCodoApplication()).route(request.getDomain(),request);
        }

        @Override
        public void stopWideRouter() throws RemoteException {
            WideRouter.getInstence(CodoApplication.getCodoApplication()).stopSelf();
        }
    };
}
