package com.codo.amber_sleepeanuty.library.ui;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewStub;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.codo.amber_sleepeanuty.library.R;

/**
 * Created by amber_sleepeanuty on 2017/5/15.
 */

public class FooterView extends RelativeLayout{
    public State mState;
    public View mLoadingError;
    public View mLoadingEnd;
    public View mLoadingProgress;

    public TextView mLoadingProgressTextView;

    public enum State{
        Loading,Error,Normal,End
    }

    public FooterView(Context context) {
        this(context,null);
    }

    public FooterView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public FooterView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        inflate(context,R.layout.footer,this);
        setState(State.Normal,true);
    }

    public void setState(State state,boolean isVisible) {
        if (state==mState){
            return;
        }
        switch(state){
            case Normal:{
                if(mLoadingEnd!=null){
                    mLoadingEnd.setVisibility(GONE);
                }
                if (mLoadingError!=null){
                    mLoadingEnd.setVisibility(GONE);
                }
                if(mLoadingProgress!=null){
                    mLoadingProgress.setVisibility(GONE);
                }
                break;
            }

            case Loading:{
                if(mLoadingEnd!=null){
                    mLoadingEnd.setVisibility(GONE);
                }
                if(mLoadingError!=null){
                    mLoadingError.setVisibility(GONE);
                }
                if(mLoadingProgress==null){
                    ViewStub stub = (ViewStub) findViewById(R.id.viewstub_process);
                    mLoadingProgress = stub.inflate();
                    mLoadingProgressTextView = (TextView)mLoadingProgress.findViewById(R.id.footer_tx);
                }else{
                    mLoadingProgress.setVisibility(isVisible?VISIBLE:GONE);
                }
                break;
            }

            case Error:{
                if(mLoadingEnd!=null){
                    mLoadingEnd.setVisibility(GONE);
                }
                if(mLoadingProgress!=null){
                    mLoadingProgress.setVisibility(GONE);
                }
                if(mLoadingError==null){
                    ViewStub stub = (ViewStub) findViewById(R.id.viewstub_error);
                    mLoadingError = stub.inflate();
                    mLoadingError.setVisibility(VISIBLE);
                }else {
                    mLoadingProgress.setVisibility(isVisible?VISIBLE:GONE);
                }
                break;
            }

            case End:{
                if (mLoadingError!=null){
                    mLoadingEnd.setVisibility(GONE);
                }
                if(mLoadingProgress!=null){
                    mLoadingProgress.setVisibility(GONE);
                }
                if(mLoadingEnd==null){
                    ViewStub stub = (ViewStub) findViewById(R.id.viewstub_end);
                    mLoadingEnd = stub.inflate();
                }else {
                    mLoadingEnd.setVisibility(isVisible?VISIBLE:GONE);
                }
                break;
            }
        }

    }
}
