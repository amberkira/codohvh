package com.codo.amber_sleepeanuty.library;

import android.content.Context;
import android.os.Parcel;
import android.os.Parcelable;

import java.util.concurrent.Future;

/**
 * Created by amber_sleepeanuty on 2017/3/10.
 */

public class ActionResult implements Parcelable{
    public static final int CODE_ERROR = 0x0010;
    public static final int ACTION_NOT_FOUND = 0x0011;
    public static final int PROVIDER_NOT_FOUND = 0x0012;
    public static final int ACTION_SUCCESSED = 0x0013;
    public static final int INVAILD_PARAM = 0X0014;
    public static final int WIDEROUTER_NOT_CONNECTED = 0x0015;
    public static final int LOCALSERVICE_NOT_RESPOND = 0x0016;
    public static final int WIDEROUTER_NOT_WORKING = 0x0017;
    public static final int LOCALROUTER_FAILED = 0x0018;
    public static final int CODE_CANNOT_BIND_LOCAL = 0x0019;
    public static final int REMOTE_EXCEPTION = 0X0020;
    public static final int MULTIPLE_PROCESS_DECLINED = 0X0021;


    private int code;
    private String msg;

    public boolean isActionAsync;
    public Future<ActionResult> Holder;


    public ActionResult(){
        this.code =0;
        this.msg = "";
        isActionAsync = false;
    }

    public ActionResult(Context context,int code,String msg) {
        this.code = code;
        this.msg = msg;
        isActionAsync = false;
    }

    public ActionResult(Context context,int code,String msg,boolean isAsync){
        this.code = code;
        this.msg = msg;
        isActionAsync = false;
    }

    public ActionResult(Parcel in) {
        this.code = in.readInt();
        this.msg = in.readString();
        this.isActionAsync = in.readByte()!=0;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<ActionResult> CREATOR = new Creator<ActionResult>() {
        @Override
        public ActionResult createFromParcel(Parcel in) {
            return new ActionResult(in);
        }

        @Override
        public ActionResult[] newArray(int size) {
            return new ActionResult[size];
        }
    };

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(code);
        dest.writeString(msg);
        dest.writeByte((byte) (isActionAsync?1:0));
    }
}
