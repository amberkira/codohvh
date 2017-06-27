package com.codo.amber_sleepeanuty.library.ui;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.Gravity;
import android.widget.TextView;

import com.codo.amber_sleepeanuty.library.R;

/**
 * Created by amber_sleepeanuty on 2017/5/15.
 */

public class CodoTextView extends TextView {
    public TypedArray mTa;

    private Drawable mLeftDrawable;

    private Drawable mRightDrawable;

    private Context mContext;

    /**左右图形长宽参数*/
    private int mLeftDrawableHeight;
    private int mLeftDrawableWidth;
    private int mRightDrawableHeight;
    private int mRighttDrawableWidth;

    private int mLeftTop;
    private int mLeftLeft;

    public CodoTextView(Context context) {
        this(context,null);
    }

    public CodoTextView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public CodoTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        mTa = mContext.obtainStyledAttributes(attrs,R.styleable.CodoTextView);
        init();
    }

    private void init() {
        mLeftDrawable = getCompoundDrawables()[0];
        mRightDrawable = getCompoundDrawables()[2];


        if(mLeftDrawable!=null){
            mLeftLeft = mTa.getDimensionPixelOffset(R.styleable.CodoTextView_leftDrawableLeft,0);
            mLeftTop = mTa.getDimensionPixelOffset(R.styleable.CodoTextView_leftDrawableTop,0);
            mLeftDrawableHeight = mTa.getDimensionPixelOffset(R.styleable.CodoTextView_textviewleftDrawableHeight,mLeftDrawable.getIntrinsicHeight());
            mLeftDrawableWidth = mTa.getDimensionPixelOffset(R.styleable.CodoTextView_textviewleftDrawableWidth,mLeftDrawable.getIntrinsicWidth());
            mLeftDrawable.setBounds(mLeftLeft,mLeftTop,mLeftDrawableWidth+mLeftLeft,mLeftDrawableHeight+mLeftTop);
        }

        if(mRightDrawable!=null){
            mRightDrawableHeight = mTa.getDimensionPixelOffset(R.styleable.CodoTextView_textviewrightDrawableHeight,mRightDrawable.getIntrinsicHeight());
            mRighttDrawableWidth = mTa.getDimensionPixelOffset(R.styleable.CodoTextView_textviewrightDrawableWidth,mRightDrawable.getIntrinsicWidth());
            mRightDrawable.setBounds(0,0,mRighttDrawableWidth,mRightDrawableHeight);
        }
    }


    @Override
    protected void onDraw(Canvas canvas) {
        float textWidth = getPaint().measureText(getText().toString());
        if (null != mLeftDrawable) {
            setGravity(Gravity.CENTER | Gravity.LEFT);
            float padding = (getWidth()-textWidth)/2-getPaddingLeft()-mLeftDrawable.getIntrinsicWidth();
            if(padding>0){
                this.setCompoundDrawablePadding((int) (padding+0.5));

            }else{
                try {
                    throw new Exception("your drawable original size is too large");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

        super.onDraw(canvas);
    }
}
