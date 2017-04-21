package com.codo.amber_sleepeanuty.module_login;

import android.app.Activity;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.View;

/**
 * Created by amber_sleepeanuty on 2017/4/17.
 */

public class HomeActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_layout);
        FloatingActionButton btn = (FloatingActionButton) findViewById(R.id.float_btn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Snackbar.make(v,"xixixi",Snackbar.LENGTH_LONG).show();
            }
        });
    }
}
