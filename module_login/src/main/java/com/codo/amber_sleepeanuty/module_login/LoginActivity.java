package com.codo.amber_sleepeanuty.module_login;

import android.app.Activity;
import android.content.Context;
import android.database.Observable;
import android.os.Bundle;
import android.os.PersistableBundle;


/**
 * Created by amber_sleepeanuty on 2017/3/29.
 */

public class LoginActivity extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState, PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
        setContentView(R.layout.login_layout);
    }

    public void showProgress(Context context){
        //ProgressUtil.show(context);
    }
}
