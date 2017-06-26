package com.codo.amber_sleepeanuty.module_chat.ui;

import android.view.LayoutInflater;
import android.view.View;

import com.codo.amber_sleepeanuty.module_chat.R;
import com.filippudak.ProgressPieView.ProgressPieView;
import com.github.piasy.biv.indicator.ProgressIndicator;
import com.github.piasy.biv.view.BigImageView;

import java.util.Locale;

/**
 * Created by amber_sleepeanuty on 2017/6/22.
 */

public class ProgressPieIndicator implements ProgressIndicator {
    private ProgressPieView mProgressPieView;

    @Override
    public View getView(BigImageView parent) {
        mProgressPieView = (ProgressPieView) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.ui_progress_pie_indicator, parent, false);
        mProgressPieView.getLayoutParams().height = 60*3;
        mProgressPieView.getLayoutParams().width = 60*3;
        return mProgressPieView;
    }


    @Override
    public void onStart() {
        // not interested
    }

    @Override
    public void onProgress(int progress) {
        if (progress < 0 || progress > 100 || mProgressPieView == null) {
            return;
        }
        mProgressPieView.setProgress(progress);
        mProgressPieView.setText(String.format(Locale.getDefault(), "%d%%", progress));
    }

    @Override
    public void onFinish() {
        // not interested
    }
}