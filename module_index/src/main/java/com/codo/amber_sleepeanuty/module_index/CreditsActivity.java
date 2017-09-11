package com.codo.amber_sleepeanuty.module_index;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.codo.amber_sleepeanuty.library.Constant;
import com.codo.amber_sleepeanuty.library.util.SpUtil;


public class CreditsActivity extends AppCompatActivity implements View.OnClickListener {
    TextView mHistory;
    TextView mCurCredits;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_credits);
        initView();
    }

    private void initView() {
        mCurCredits = (TextView) findViewById(R.id.credit);
        mHistory = (TextView) findViewById(R.id.history);
        mCurCredits.setText(SpUtil.getInt(Constant.CREDITS,0)+"分");
        mHistory.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.history){
            Toast.makeText(this,"等着吧",Toast.LENGTH_SHORT).show();
        }
    }
}
