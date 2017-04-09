package com.codo.amber_sleepeanuty.codohvh;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.codo.amber_sleepeanuty.library.CodoApplication;
import com.codo.amber_sleepeanuty.library.RouterRequest;
import com.codo.amber_sleepeanuty.library.RouterRequestPool;
import com.codo.amber_sleepeanuty.library.router.LocalRouter;
import com.codo.amber_sleepeanuty.library.util.LogUtil;

/**
 * Created by amber_sleepeanuty on 2017/3/29.
 */

public class MainActivity extends Activity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
         Button btn = (Button) findViewById(R.id.btn_main);
        btn.setText("你大爷");
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RouterRequest routerRequest = RouterRequestPool.getAvailableRequest(MainActivity.this,5);
                LogUtil.d("onclick");
                routerRequest.provider("Login")
                        .action("Login");
                try {
                    LocalRouter.getInstance(CodoApplication.getCodoApplication()).route(MainActivity.this,routerRequest);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
