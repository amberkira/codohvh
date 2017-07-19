package com.codo.amber_sleepeanuty.library.widget;

import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.codo.amber_sleepeanuty.library.R;

/**
 * Created by amber_sleepeanuty on 2017/6/22.
 */

public class VolumnDialogManager {
    private Dialog mDialog;

    private ImageView mIcon;
    private ImageView mVoice;
    private TextView mState;

    private Context mContext;

    public VolumnDialogManager(Context mContext) {
        this.mContext = mContext;
    }

    public void init() {
        mDialog = new Dialog(mContext,R.style.ThemeAudioDialog);
        //LayoutInflater inflater = LayoutInflater.from(mContext);
        //View v = inflater.inflate(R.layout.dialog_layout,null);
        View v = LayoutInflater.from(mContext).inflate(R.layout.dialog_layout,null,false);
        mDialog.setContentView(v);
        //mDialog.
        mDialog.getWindow().setGravity(Gravity.CENTER);

        mIcon = (ImageView) mDialog.findViewById(R.id.img_recording_state);
        mVoice = (ImageView) mDialog.findViewById(R.id.recording_volumn_level);
        mState = (TextView) mDialog.findViewById(R.id.tx_recording_state);
        mDialog.show();
    }

    public void showRecording(){
        if(mDialog!=null&&mDialog.isShowing()){
            mIcon.setVisibility(View.VISIBLE);
            mVoice.setVisibility(View.VISIBLE);
            mState.setVisibility(View.VISIBLE);


            mIcon.setImageResource(R.mipmap.recorder);
            mState.setText("手指上滑，取消发送");
        }

    }

    public void showCancel(){
        if(mDialog!=null&&mDialog.isShowing()){
            mIcon.setImageResource(R.mipmap.cancel);
            mIcon.setVisibility(View.VISIBLE);
            mVoice.setVisibility(View.GONE);
            mState.setVisibility(View.VISIBLE);
            mState.setText("松开手指，取消发送");
        }
    }

    public void showTooShort(){
        if(mDialog!=null&&mDialog.isShowing()){
            mIcon.setVisibility(View.VISIBLE);
            mVoice.setVisibility(View.GONE);
            mState.setVisibility(View.VISIBLE);

            mIcon.setImageResource(R.mipmap.voice_to_short);
            mState.setText("说话时间太短！");
        }
    }

    public void dismiss(){
        if(mDialog!=null&&mDialog.isShowing()){
            mDialog.dismiss();
            mDialog = null;
        }
    }

    public void refreshVoice(int lv){
        if(mDialog!=null&&mDialog.isShowing()){
            mIcon.setVisibility(View.VISIBLE);
            mVoice.setVisibility(View.VISIBLE);
            mState.setVisibility(View.VISIBLE);

            int voiceResId = mContext.getResources().getIdentifier("v"+lv,"mipmap",mContext.getPackageName());
            mVoice.setImageResource(voiceResId);
        }
    }
}
