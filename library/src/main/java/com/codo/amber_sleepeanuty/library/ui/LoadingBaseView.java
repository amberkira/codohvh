package com.codo.amber_sleepeanuty.library.ui;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.RelativeLayout;

import com.codo.amber_sleepeanuty.library.R;

/**
 * Created by amber_sleepeanuty on 2017/5/9.
 */

public abstract class LoadingBaseView extends RelativeLayout {

    public LoadingBaseView(Context context) {
        this(context,null);
    }

    public LoadingBaseView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public LoadingBaseView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        inflate(context, R.layout.footer,null);
    }

    public void setState(State s){
        switch (s){
            case Loading:{

            }
            case Error:{

            }
            case Idle:{

            }
            case Sucsess:{

            }

        }
    }

    public enum State{
        Loading,Error,Sucsess,Idle
    }


}
