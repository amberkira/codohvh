package com.codo.amber_sleepeanuty.library.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.codo.amber_sleepeanuty.library.util.LogUtil;

/**
 * Created by amber_sleepeanuty on 2017/6/30.
 */

public class DBprovider {
    private DatabaseHelper mDbHelper;
    private static DBprovider provider;


    private DBprovider(Context context){
        mDbHelper = new DatabaseHelper(context);
    }

    public static DBprovider getInstance(Context context){
        if(provider == null){
            synchronized (DBprovider.class){
                if(provider == null){
                    provider = new DBprovider(context);
                }
            }
        }
        return provider;
    }

    public void Insert(String table, ContentValues values){
        mDbHelper.getWritableDatabase().insert(table,null,values);
    }

    public void Update(String table, ContentValues values, String condition,String[] whereArgs){
        mDbHelper.getWritableDatabase().update(table,values,condition,whereArgs);
    }

    public void Query(String table){
        Cursor cursor = mDbHelper.getWritableDatabase().query(table,null,null,null,null,null,null);
        if(cursor.moveToFirst()){
            do{
                String name = cursor.getString(cursor.getColumnIndex(DatabaseHelper.USER_NAME));
                String url = cursor.getString(cursor.getColumnIndex(DatabaseHelper.USER_AVATAR));
            }while (cursor.moveToNext());
        }
        cursor.close();
    }

    public void Query(String table,String easemobID){
        Cursor cursor = mDbHelper.getWritableDatabase().query(table,new String[]{easemobID},easemobID,null,null,null,null);
    }


}
