package com.codo.amber_sleepeanuty.codohvh;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.View;
import android.widget.Button;

import com.codo.amber_sleepeanuty.library.CodoApplication;
import com.codo.amber_sleepeanuty.library.RouterRequest;
import com.codo.amber_sleepeanuty.library.router.LocalRouter;
import com.codo.amber_sleepeanuty.library.util.LogUtil;
import com.codo.amber_sleepeanuty.module_login.LoginActivity;

/**
 * Created by amber_sleepeanuty on 2017/3/29.
 */

public class MainActivity extends Activity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final Button btn = (Button) findViewById(R.id.btn_main);
        btn.setText("HAHAHA");
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btn.setText("HAHAHA");
                RouterRequest routerRequest = new RouterRequest(MainActivity.this);
                LogUtil.d("onclick");
                routerRequest.provider("login")
                        .action("login");
                try {
                    LocalRouter.getInstance(CodoApplication.getCodoApplication()).route(MainActivity.this,routerRequest);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
