package com.codo.amber_sleepeanuty.codohvh;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.location.LocationProvider;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.telephony.TelephonyManager;
import android.view.View;
import android.widget.Button;

import com.codo.amber_sleepeanuty.library.CodoApplication;
import com.codo.amber_sleepeanuty.library.Constant;
import com.codo.amber_sleepeanuty.library.RouterRequest;
import com.codo.amber_sleepeanuty.library.RouterRequestPool;
import com.codo.amber_sleepeanuty.library.event.InitEvent;
import com.codo.amber_sleepeanuty.library.router.LocalRouter;
import com.codo.amber_sleepeanuty.library.util.LogUtil;
import com.codo.amber_sleepeanuty.library.util.PermissionCheck;
import com.codo.amber_sleepeanuty.library.util.SpUtil;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by amber_sleepeanuty on 2017/3/29.
 */

public class MainActivity extends Activity {

    //权限请求页面
    private static final int REQUEST_CODE = 0X001;

    //app内部需要的敏感权限
    protected final String[] PERMISSIONS = new String[]{
            Manifest.permission.RECORD_AUDIO,
            Manifest.permission.CAMERA,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_PHONE_STATE,
            Manifest.permission.ACCESS_FINE_LOCATION
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
                RouterRequest routerRequest = RouterRequestPool.getAvailableRequest(MainActivity.this, 5);
                LogUtil.d("onclick");
                routerRequest.provider("Login")
                        .action("Login");
                try {
                    LocalRouter.getInstance(CodoApplication.getCodoApplication()).route(MainActivity.this, routerRequest);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RouterRequest routerRequest = RouterRequestPool.getAvailableRequest(MainActivity.this, 5);
                routerRequest.domain("com.codo.amber_sleepeanuty.codohvh:Pro").provider("Pro")
                        .action("Pro");
                try {
                    LocalRouter.getInstance(CodoApplication.getCodoApplication()).route(MainActivity.this, routerRequest);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * 获取经纬度信息，手机硬件号
     * @param event
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void InitConfig(InitEvent event) {
        LogUtil.e("响应event");
        LocationManager manager = (LocationManager) getSystemService(LOCATION_SERVICE);
        List<String> list = manager.getAllProviders();
        String provider = null;
        if (list.contains(manager.GPS_PROVIDER))
            provider = LocationManager.GPS_PROVIDER;

        if (list.contains(manager.NETWORK_PROVIDER))
            provider = LocationManager.NETWORK_PROVIDER;


        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        Location loc = manager.getLastKnownLocation(provider);
        if(loc!=null){
            SpUtil.saveString(Constant.LATITUDE,loc.getLatitude()+"");
            SpUtil.saveString(Constant.LONGITUDE,loc.getLongitude()+"");
        }


        TelephonyManager tm = (TelephonyManager)getSystemService(TELEPHONY_SERVICE);
        String DEVICE_ID = tm.getDeviceId();
        if(DEVICE_ID!=null){
            SpUtil.saveString(Constant.DEVICE_ID,DEVICE_ID);
        }
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

    @Override
    protected void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);

    }
}
