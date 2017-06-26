package com.codo.amber_sleepeanuty.module_chat.ui;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;

import com.codo.amber_sleepeanuty.module_chat.R;
import com.github.piasy.biv.view.BigImageView;

/**
 * Created by amber_sleepeanuty on 2017/6/22.
 */

public class BigImageActivity extends Activity{
    BigImageView bivImImage;
    String thumbnailUrl;
    String remoteUrl;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bigimage);
        initView();
        thumbnailUrl = getIntent().getExtras().getString("thumbnail");
        remoteUrl = getIntent().getExtras().getString("remote");
        bivImImage.showImage(Uri.parse(thumbnailUrl),Uri.parse(remoteUrl));
        bivImImage.setProgressIndicator(new ProgressPieIndicator());

    }

    private void initView() {
        bivImImage = (BigImageView) findViewById(R.id.biv_im_image);
    }
}
