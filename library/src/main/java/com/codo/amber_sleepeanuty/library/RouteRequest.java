package com.codo.amber_sleepeanuty.library;

import android.content.Context;
import android.text.TextUtils;

import com.codo.amber_sleepeanuty.library.util.ProcessNameUtil;

import java.util.HashMap;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by amber_sleepeanuty on 2017/3/10.
 */

public class RouteRequest {
    private String DEFAULT_POCESS_NAME="";
    private String domain;
    private String provider;
    private String action;
    private HashMap<String,String> data;
    private AtomicBoolean isIdle = new AtomicBoolean(true);

    private AtomicInteger mIndex = new AtomicInteger(0);
    private static final int Length = 64;
    private static int RESETNUMBER = 1000;
    private static volatile RouteRequest[] requestPool = new RouteRequest[Length];

    static{
        for (RouteRequest request:requestPool) {
            request = new RouteRequest();
        }
    }

    public RouteRequest(){
        domain = DEFAULT_POCESS_NAME;
        provider = "";
        action = "";
        data = new HashMap<>();
    }

    public RouteRequest(Context context){
        domain = getProcessName(context);
        provider = "";
        action = "";
        data = new HashMap<>();
    }

    private String getProcessName(Context context){
        if (TextUtils.isEmpty(DEFAULT_POCESS_NAME)||ProcessNameUtil.UNKNOWN_PROCESS_NAME.equals(DEFAULT_POCESS_NAME))
            DEFAULT_POCESS_NAME = ProcessNameUtil.getProcessName(context,ProcessNameUtil.getMyProcessId());
        return DEFAULT_POCESS_NAME;
    }

    public RouteRequest domain(String domain){
        this.domain = domain;
        return this;
    }

    public RouteRequest provider(String provider){
        this.provider = provider;
        return this;
    }

    public RouteRequest action(String action){
        this.action = action;
        return this;
    }

    public RouteRequest data(HashMap<String,String> data){
        this.data = data;
        return this;
    }

    public String getDomain() {

        return domain;
    }

    public String getProvider() {
        return provider;
    }

    public String getAction() {
        return action;
    }

    public HashMap<String, String> getData() {
        return data;
    }
}
