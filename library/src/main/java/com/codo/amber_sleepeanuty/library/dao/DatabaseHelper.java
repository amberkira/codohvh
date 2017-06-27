package com.codo.amber_sleepeanuty.library.dao;

import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by amber_sleepeanuty on 2017/6/27.
 */

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DB_NAME = "codo.db";

    private static final int DB_VERSION = 1;

    private static final String TABLE_USER_APPLY = "table_user_apply";

    private static final String COLUMN_ID = "_id";
    private static final String USER_NAME = "user_name";
    private static final String REASON = "reason";



    public DatabaseHelper(Context context){
        this(context,DB_NAME,null,DB_VERSION);
    }
    public DatabaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    public DatabaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version, DatabaseErrorHandler errorHandler) {
        super(context, name, factory, version, errorHandler);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
