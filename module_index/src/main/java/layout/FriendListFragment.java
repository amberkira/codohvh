package layout;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.codo.amber_sleepeanuty.library.Constant;
import com.codo.amber_sleepeanuty.library.bean.FriendListBean;
import com.codo.amber_sleepeanuty.library.network.APIService;
import com.codo.amber_sleepeanuty.library.ui.StickyItemDecoration;
import com.codo.amber_sleepeanuty.library.util.SpUtil;
import com.codo.amber_sleepeanuty.module_index.R;
import com.codo.amber_sleepeanuty.module_index.adapter.FriendlListAdapter;

import java.util.HashMap;
import java.util.List;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by amber_sleepeanuty on 2017/6/28.
 */

public class FriendListFragment extends Fragment {
    RecyclerView mRecyclerView;
    FriendlListAdapter mAdapter;
    StickyItemDecoration mItemDecoration;
    List<FriendListBean.Info> mList;
    HashMap<Integer,String> mDecrotationMap;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_friendlist,container,false);
        mDecrotationMap = new HashMap<>();
        initData();
        mItemDecoration = new StickyItemDecoration(getContext());
        mItemDecoration.setKeyMap(mDecrotationMap);

        mAdapter = new FriendlListAdapter(getContext(),mList);
        mRecyclerView = (RecyclerView) v.findViewById(R.id.friendlist_rv);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecyclerView.addItemDecoration(mItemDecoration);
        mRecyclerView.setAdapter(mAdapter);

        return super.onCreateView(inflater, container, savedInstanceState);
    }

    public void initData(){
        APIService.Factory.createService(getContext()).friendList(SpUtil.getString(Constant.CURRENT_LOGIN_ID,null),
                SpUtil.getString(Constant.SESSION_ID,null))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Subscriber<FriendListBean>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(FriendListBean friendListBean) {
                mList = friendListBean.getServer().getInfo();
                initDecorationMap(mList);
            }
        });

    }

    private void initDecorationMap(List<FriendListBean.Info> mList) {
        int layerCount = mList.size();
        int key = 0;
        for(int i = 0; i < layerCount; i++){
            String value = mList.get(i).getInitial();
            mDecrotationMap.put(key,value);
            key += mList.get(i).getInfolist().size();
        }
    }
}
