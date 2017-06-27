package com.codo.amber_sleepeanuty.library.ui;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by amber_sleepeanuty on 2017/6/27.
 */

public class IMItemDecoration extends RecyclerView.ItemDecoration {
    private Context context;
    private Drawable drawable;
    private static final int[] attrs = new int[]{
        android.R.attr.listDivider
    };

    private static final int LIST_HORIZONTAL = LinearLayoutManager.HORIZONTAL;
    private static final int LIST_VERTICAL = LinearLayoutManager.VERTICAL;
    private int mOritation;

    public IMItemDecoration(Context context,int oritation) {
        //获取系统divider图片
        final TypedArray ta = context.obtainStyledAttributes(attrs);
        drawable = ta.getDrawable(0);
        ta.recycle();
        setOritation(oritation);

    }

    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
        if(mOritation == LIST_HORIZONTAL){
            drawHorizontal(c,parent);
        }else {
            drawVertical(c,parent);
        }

    }

    private void drawHorizontal(Canvas c, RecyclerView parent) {
        int top = parent.getPaddingTop();
        int bottom = parent.getPaddingBottom();
        int childNumber = parent.getChildCount();
        for(int i = 0;i<childNumber;i++){
            View v = parent.getChildAt(i);
            RecyclerView.LayoutParams layoutparams = (RecyclerView.LayoutParams) v.getLayoutParams();
            int left = v.getRight()+layoutparams.rightMargin;
            int right = left+drawable.getIntrinsicWidth();
            drawable.setBounds(left,top,right,bottom);
            drawable.draw(c);
        }
    }

    private void drawVertical(Canvas c, RecyclerView parent) {
        int left = parent.getPaddingLeft();
        int right = parent.getMeasuredWidth()-parent.getPaddingLeft();
        int childNumber = parent.getChildCount();
        for(int i = 0; i<childNumber;i++){
            View v = parent.getChildAt(i);
            RecyclerView.LayoutParams  layoutParams = (RecyclerView.LayoutParams)v.getLayoutParams();
            int top = v.getBottom() + layoutParams.bottomMargin;
            int bottom = top + drawable.getIntrinsicHeight();
            drawable.setBounds(left,top,right,bottom);
            drawable.draw(c);
        }
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        if(mOritation == LIST_HORIZONTAL){
            outRect.set(0,0,drawable.getIntrinsicWidth(),0);
        }else {
            outRect.set(0,0,0,drawable.getIntrinsicHeight());
        }
    }

    public void setOritation(int oritation){
        if(oritation == LIST_HORIZONTAL||oritation == LIST_VERTICAL){
            mOritation = oritation;
        }else {
            throw new IllegalArgumentException("Oritation参数设置有误");
        }
    }
}
