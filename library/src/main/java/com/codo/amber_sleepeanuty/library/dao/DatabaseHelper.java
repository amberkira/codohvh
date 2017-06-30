package com.codo.amber_sleepeanuty.library.dao;

import android.app.Activity;
import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.codo.amber_sleepeanuty.library.util.LogUtil;

/**
 * Created by amber_sleepeanuty on 2017/6/27.
 */

public class DatabaseHelper extends SQLiteOpenHelper {
    private Context mContext;
    private static final String DB_NAME = "codo.db";

    private static final int DB_VERSION = 1;

    public static final String TABLE_USER_INFORMATION = "table_user_information";

    public static final String COLUMN_ID = "_id";
    public static final String USER_NAME = "user_name";
    public static final String USER_NICKNAME = "user_nickname";
    public static final String USER_EASEMOBID = "easemob_id";
    public static final String Mobile = "mobile";
    public static final String USER_AVATAR = "user_avatar";





    public DatabaseHelper(Context context){
        this(context,DB_NAME,null,DB_VERSION);
    }

    public DatabaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
        mContext = context;
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        String s = "CREATE TABLE "+TABLE_USER_INFORMATION+"("
                +COLUMN_ID+" INTEGER PRIMARY KEY AUTOINCREMENT,"
                +USER_NAME+" TEXT,"
                +USER_NICKNAME+" TEXT,"
                +USER_EASEMOBID+" TEXT,"
                +Mobile+" TEXT,"
                +USER_AVATAR+" TEXT)";
        db.execSQL(s);
        LogUtil.e(s);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
