package com.codo.amber_sleepeanuty.library.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.annotation.Nullable;

import com.codo.amber_sleepeanuty.library.ActionResult;
import com.codo.amber_sleepeanuty.library.IWideRouterAIDL;
import com.codo.amber_sleepeanuty.library.RouterRequest;

/**
 * Created by amber_sleepeanuty on 2017/3/16.
 */

public class WideConnectService extends Service {
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    IWideRouterAIDL.Stub stub = new IWideRouterAIDL.Stub() {
        @Override
        public void respondAsync(RouterRequest request) throws RemoteException {

        }

        @Override
        public ActionResult route(RouterRequest request) throws RemoteException {

            return null;
        }

        @Override
        public void stopWideRouter() throws RemoteException {

        }
    }
}
