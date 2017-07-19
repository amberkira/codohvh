package com.codo.amber_sleepeanuty.library.ui;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;

import com.codo.amber_sleepeanuty.library.util.LogUtil;

import java.util.HashMap;

/**
 *
 * 好友显示里的吸顶效果这里就暂时不考虑复用 直接默认为vertical的项目展示
 * 配置时需要提前设置hashmap
 * Created by amber_sleepeanuty on 2017/6/28.
 */

public class StickyItemDecoration extends RecyclerView.ItemDecoration {

    private int[] attr = new int[]{
            android.R.attr.listDivider
    };
    /**
     * key;存放每一个吸顶开始的位置 value： 需要绘制的title
     */
    private HashMap<Integer,String> mKeyMap;

    private Context mContext;
    private Drawable drawable;//listdivider

    private Paint mTextPaint;//吸顶文本画笔
    private Paint mBackgroundPaint;//吸顶背景画笔

    private float mTextHeight;
    private float mTextBaseline;

    private int mTitleHeight;

    /**
     * 该constructor默认divider用系统的 吸顶背景色为灰色高度为20dp 字体大小暂定14sp
     * @param context
     */
    public StickyItemDecoration(Context context) {
        this.mContext = context;

        TypedArray a = context.obtainStyledAttributes(attr);
        drawable = a.getDrawable(0);
        a.recycle();


        mTextPaint = new Paint();
        mTextPaint.setColor(context.getResources().getColor(android.R.color.black));
        mTextPaint.setAntiAlias(true);

        mTextPaint.setTextSize(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP,14,
                mContext.getResources().getDisplayMetrics()));
        Paint.FontMetrics fm = mTextPaint.getFontMetrics();
        mTextHeight = fm.bottom-fm.top;
        mTextBaseline = fm.bottom;

        mTitleHeight = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,20,
                mContext.getResources().getDisplayMetrics());
        mBackgroundPaint = new Paint();
        mBackgroundPaint.setAntiAlias(true);
        mBackgroundPaint.setColor(Color.parseColor("#AAAAAA"));
    }



    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
        super.onDraw(c, parent, state);
        drawVertical(c,parent);
    }

    private void drawVertical(Canvas c, RecyclerView parent) {
        int left = parent.getPaddingLeft();
        int right = parent.getWidth()-parent.getPaddingRight();
        int top = 0;
        int bottom = 0;
        LinearLayoutManager manager = (LinearLayoutManager) parent.getLayoutManager();
        //当前可见的
        int count = parent.getChildCount();
        for(int i = 0; i < count; i++){
            View child = parent.getChildAt(i);
            RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child.getLayoutParams();
            //获取所在map位置
            int POS = manager.getPosition(child);
            //绘制普通分割线
            if(!mKeyMap.containsKey(POS)){
                top = child.getTop()-params.topMargin-drawable.getIntrinsicHeight();
                bottom = top + drawable.getIntrinsicHeight();
                drawable.setBounds(left,top,right,bottom);
                drawable.draw(c);
            }else if(mKeyMap.containsKey(POS)){
                top = child.getTop()-params.topMargin-mTitleHeight;
                bottom = top+mTitleHeight;
                c.drawRect(left,top,right,bottom,mBackgroundPaint);
                String title = mKeyMap.get(POS);
                float x = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,10,
                        mContext.getResources().getDisplayMetrics());
                float y = bottom - (mTitleHeight-mTextHeight)/2-mTextBaseline;
                LogUtil.e("-------draw Text: X:"+x+" Y:"+y+" title:"+title+"--------");
                c.drawText(title,x,y,mTextPaint);
                LogUtil.e("-------draw Text--------------");

            }

        }
    }

    @Override
    public void onDrawOver(Canvas c, RecyclerView parent, RecyclerView.State state) {
        super.onDrawOver(c, parent, state);
        int firstVisiblePos = ((LinearLayoutManager)parent.getLayoutManager()).findFirstVisibleItemPosition();
        //没有项目
        if(firstVisiblePos == RecyclerView.NO_POSITION){
            return;
        }

        String title = getTitle(firstVisiblePos);

        boolean flag = false;

        if (getTitle(firstVisiblePos + 1) != null && !title.equals(getTitle(firstVisiblePos + 1))) {
            //说明是当前组最后一个元素，但不一定碰撞了
//            Log.e(TAG, "onDrawOver: "+"==============" +firstVisiblePos);
            View child = parent.findViewHolderForAdapterPosition(firstVisiblePos).itemView;
            if (child.getTop() + child.getMeasuredHeight() < mTitleHeight) {
                //进一步检测碰撞
//                Log.e(TAG, "onDrawOver: "+child.getTop()+"$"+firstVisiblePos );
                c.save();//保存画布当前的状态
                flag = true;
                c.translate(0, child.getTop() + child.getMeasuredHeight() - mTitleHeight);//负的代表向上
            }
        }
        int left = parent.getPaddingLeft();
        int right = parent.getWidth() - parent.getPaddingRight();
        int top = parent.getPaddingTop();
        int bottom = top + mTitleHeight;
        c.drawRect(left, top, right, bottom, mBackgroundPaint);
        float x = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 10, mContext.getResources().getDisplayMetrics());
        float y = bottom - (mTitleHeight - mTextHeight) / 2 - mTextBaseline;//计算文字baseLine
        c.drawText(title, x, y, mTextPaint);
        if (flag) {
            //还原画布为初始状态
            c.restore();
        }      }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
        int pos = parent.getChildViewHolder(view).getAdapterPosition();
        if (mKeyMap.containsKey(pos)) {//留出头部偏移
            outRect.set(0, mTitleHeight, 0, 0);
        } else {
            outRect.set(0, drawable.getIntrinsicHeight(), 0, 0);
        }
    }

    /**
     * *如果该位置没有，则往前循环去查找标题，找到说明该位置属于该分组
     *
     * @param position
     * @return
     */
    private String getTitle(int position) {
        while (position >= 0) {
            if (mKeyMap.containsKey(position)) {
                return mKeyMap.get(position);
            }
            position--;
        }
        return null;
    }

    public void setKeyMap(HashMap<Integer,String> keymap){
        this.mKeyMap = keymap;
    }

    public void setTitleHeight(int titleHeight){
        this.mTitleHeight = titleHeight;
    }
}
