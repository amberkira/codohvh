package com.codo.amber_sleepeanuty.library.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.annotation.NonNull;


import com.codo.amber_sleepeanuty.library.ActionResult;
import com.codo.amber_sleepeanuty.library.CodoApplication;
import com.codo.amber_sleepeanuty.library.ILocalRouterAIDL;
import com.codo.amber_sleepeanuty.library.RouterRequest;
import com.codo.amber_sleepeanuty.library.router.LocalRouter;

/**
 * Created by amber_sleepeanuty on 2017/3/16.
 */

public class LocalConnectService extends Service {

    @NonNull
    @Override
    public IBinder onBind(Intent intent) {
        return stub;
    }

    ILocalRouterAIDL.Stub stub = new ILocalRouterAIDL.Stub() {
        @Override
        public boolean respondAsync(RouterRequest request) throws RemoteException {
            LocalRouter localRouter = LocalRouter.getInstance(CodoApplication.getCodoApplication());
            return localRouter.answerWideRouterAsync(request);

        }

        @Override
        public ActionResult route(RouterRequest request) throws RemoteException {
            try{
                LocalRouter localRouter = LocalRouter.getInstance(CodoApplication.getCodoApplication());
                ActionResult result = localRouter.route(getApplicationContext(),request);
                return result;
            }catch (Exception e){
                ActionResult result = new ActionResult(getApplicationContext(),
                        ActionResult.LOCALSERVICE_NOT_RESPOND,
                        "Localconnectservice has wrong : "+e.getMessage());
                return result;
            }
        }

        public void connectWideRouter(){
            LocalRouter.getInstance(CodoApplication.getCodoApplication()).connectWideRouterService();
        }

        public void stopWideRouter(){
            LocalRouter.getInstance(CodoApplication.getCodoApplication()).stopWideRouterService();
        }

    };
}
