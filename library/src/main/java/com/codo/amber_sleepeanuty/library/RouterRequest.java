package com.codo.amber_sleepeanuty.library;

import android.content.Context;
import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;

import com.codo.amber_sleepeanuty.library.util.ProcessNameUtil;

import java.util.HashMap;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by amber_sleepeanuty on 2017/3/10.
 */

public class RouterRequest implements Parcelable{
    private String DEFAULT_POCESS_NAME="";
    private String domain;
    private String provider;
    private String action;
    private HashMap<String,String> data;
    public AtomicBoolean isIdle;


    public RouterRequest(){
        domain = DEFAULT_POCESS_NAME;
        provider = "";
        action = "";
        data = new HashMap<>();
        isIdle= new AtomicBoolean(true);
    }

    public RouterRequest(Context context){
        domain = getProcessName(context);
        provider = "";
        action = "";
        data = new HashMap<>();
        isIdle = new AtomicBoolean(true);
    }

    protected RouterRequest(Parcel in) {
        DEFAULT_POCESS_NAME = in.readString();
        domain = in.readString();
        provider = in.readString();
        action = in.readString();
        data = in.readHashMap(HashMap.class.getClassLoader());

    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(DEFAULT_POCESS_NAME);
        dest.writeString(domain);
        dest.writeString(provider);
        dest.writeString(action);
        dest.writeMap(data);
    }



    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<RouterRequest> CREATOR = new Creator<RouterRequest>() {
        @Override
        public RouterRequest createFromParcel(Parcel in) {
            return new RouterRequest(in);
        }

        @Override
        public RouterRequest[] newArray(int size) {
            return new RouterRequest[size];
        }
    };

    private String getProcessName(Context context){
        if (TextUtils.isEmpty(DEFAULT_POCESS_NAME)||ProcessNameUtil.UNKNOWN_PROCESS_NAME.equals(DEFAULT_POCESS_NAME))
            DEFAULT_POCESS_NAME = ProcessNameUtil.getProcessName(context,ProcessNameUtil.getMyProcessId());
        return DEFAULT_POCESS_NAME;
    }

    public RouterRequest domain(String domain){
        this.domain = domain;
        return this;
    }

    public RouterRequest provider(String provider){
        this.provider = provider;
        return this;
    }

    public RouterRequest action(String action){
        this.action = action;
        return this;
    }

    public RouterRequest data(HashMap<String,String> data){
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
