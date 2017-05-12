package com.codo.amber_sleepeanuty.library.ui;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.AttributeSet;

import com.codo.amber_sleepeanuty.library.adapter.RecyclerViewBaseAdapter;

/**
 * Created by amber_sleepeanuty on 2017/5/8.
 */

public class PullLoadRecylerView extends RecyclerView {
    private LoadMoreListener mLoadMorelistener;
    public PullLoadRecylerView(Context context) {
        super(context);
    }

    public PullLoadRecylerView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public PullLoadRecylerView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public void setOnLodaMoreListener(LoadMoreListener listener){
        mLoadMorelistener = listener;
        this.addOnScrollListener(null);
    }

    @Override
    public void addOnScrollListener(OnScrollListener listener) {
        if(listener==null){
            listener = new OnScrollListener() {
                @Override
                public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                    int lastChildPosition = -1;
                    if(newState==RecyclerView.SCROLL_STATE_IDLE&&mLoadMorelistener!=null){
                        LayoutManager manager = recyclerView.getLayoutManager();
                        if(manager instanceof LinearLayoutManager){
                            lastChildPosition = ((LinearLayoutManager) manager).findLastVisibleItemPosition();
                        }else if(manager instanceof GridLayoutManager){
                            lastChildPosition = ((GridLayoutManager) manager).findLastVisibleItemPosition();
                        }else if(manager instanceof StaggeredGridLayoutManager){
                            int[] lastPositions = new int[((StaggeredGridLayoutManager) manager).getSpanCount()];
                            ((StaggeredGridLayoutManager) manager).findLastVisibleItemPositions(lastPositions);
                            lastChildPosition = findLast(lastPositions);
                        };
                        if(lastChildPosition == ((RecyclerViewBaseAdapter)recyclerView.getAdapter()).getBottomPos()){
                            mLoadMorelistener.loadMore(recyclerView);
                        }
                    }

                    super.onScrollStateChanged(recyclerView, newState);
                }

                @Override
                public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                    super.onScrolled(recyclerView, dx, dy);
                }

                public int findLast(int[] pos){
                    int max = pos[0];
                    for (int value:pos
                         ) {
                        if(value>max){
                            max = value;
                        }
                    }
                    return max;
                }
            };
        }
        super.addOnScrollListener(listener);
    }

    public interface LoadMoreListener{
        public abstract void loadMore(RecyclerView rv);
    }
}
