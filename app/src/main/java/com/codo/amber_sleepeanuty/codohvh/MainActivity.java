package com.codo.amber_sleepeanuty.codohvh;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.codo.amber_sleepeanuty.library.CodoApplication;
import com.codo.amber_sleepeanuty.library.RouterRequest;
import com.codo.amber_sleepeanuty.library.RouterRequestPool;
import com.codo.amber_sleepeanuty.library.router.LocalRouter;
import com.codo.amber_sleepeanuty.library.util.LogUtil;
import com.codo.amber_sleepeanuty.library.util.PermissionCheck;

import java.util.ArrayList;


/**
 * Created by amber_sleepeanuty on 2017/3/29.
 */

public class MainActivity extends Activity{

    //权限请求页面
    private static final int REQUEST_CODE = 0X001;

    //app内部需要的敏感权限
    protected final String[] PERMISSIONS = new String[]{
            Manifest.permission.RECORD_AUDIO,
            Manifest.permission.CAMERA,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };

    private PermissionCheck mPermissionChecker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mPermissionChecker = new PermissionCheck(this);
         Button btn = (Button) findViewById(R.id.btn_main);
        Button btn1 = (Button) findViewById(R.id.btn_pro);
        btn.setText("登陆");
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

        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RouterRequest routerRequest = RouterRequestPool.getAvailableRequest(MainActivity.this,5);
                routerRequest.domain("com.codo.amber_sleepeanuty.codohvh:Pro").provider("Pro")
                        .action("Pro");
                try {
                    LocalRouter.getInstance(CodoApplication.getCodoApplication()).route(MainActivity.this,routerRequest);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(mPermissionChecker.lacksPermissions(PERMISSIONS)){
            startPermissionsActivity();
        }
    }

    private void startPermissionsActivity() {

        PermissionsActivity.startActivityForResult(this, REQUEST_CODE, PERMISSIONS);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE && resultCode == PermissionsActivity.PERMISSIONS_DENIED) {
            finish();
        }
    }
}
