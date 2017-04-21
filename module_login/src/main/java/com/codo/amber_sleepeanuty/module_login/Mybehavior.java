package com.codo.amber_sleepeanuty.module_login;

import android.content.Context;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

/**
 * Created by amber_sleepeanuty on 2017/4/18.
 */

public class Mybehavior extends CoordinatorLayout.Behavior<FloatingActionButton> {

    public Mybehavior(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public Mybehavior() {
    }

    @Override
    public boolean layoutDependsOn(CoordinatorLayout parent, FloatingActionButton child, View dependency) {

        return dependency instanceof Snackbar.SnackbarLayout;
    }

    @Override
    public boolean onDependentViewChanged(CoordinatorLayout parent, FloatingActionButton child, View dependency) {

        child.setY(child.getY()+dependency.getHeight());

        return super.onDependentViewChanged(parent, child, dependency);
    }



}
