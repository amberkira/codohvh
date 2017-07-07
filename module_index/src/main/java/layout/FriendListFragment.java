package layout;

import android.app.Activity;
import android.content.ContentValues;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.codo.amber_sleepeanuty.library.Constant;
import com.codo.amber_sleepeanuty.library.bean.FriendListBean;
import com.codo.amber_sleepeanuty.library.dao.DBprovider;
import com.codo.amber_sleepeanuty.library.dao.DatabaseHelper;
import com.codo.amber_sleepeanuty.library.event.LoginEvent;
import com.codo.amber_sleepeanuty.library.network.APIService;
import com.codo.amber_sleepeanuty.library.ui.StickyItemDecoration;
import com.codo.amber_sleepeanuty.library.util.LogUtil;
import com.codo.amber_sleepeanuty.library.util.SpUtil;
import com.codo.amber_sleepeanuty.module_index.IndexActivity;
import com.codo.amber_sleepeanuty.module_index.R;
import com.codo.amber_sleepeanuty.module_index.adapter.FriendlListAdapter;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

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

    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            if(msg.what==1){
                initDecorationMap(mList);
                mItemDecoration = new StickyItemDecoration(getContext());
                mItemDecoration.setKeyMap(mDecrotationMap);
                mAdapter = new FriendlListAdapter(getContext(),mList);
                mRecyclerView.addItemDecoration(mItemDecoration);
                mRecyclerView.setAdapter(mAdapter);
            }
        }
    };


    public static FriendListFragment newInstance() {
        Bundle args = new Bundle();
        FriendListFragment fragment = new FriendListFragment();
        fragment.setArguments(args);
        return fragment;
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_friendlist,container,false);
        mDecrotationMap = new HashMap<>();
        mRecyclerView = (RecyclerView) v.findViewById(R.id.friendlist_rv);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        initData();
        return v;
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
                if(friendListBean == null)
                    throw new NullPointerException("获取好友列表失败");
                mList = friendListBean.getServer().getInfo();
                handler.sendEmptyMessage(1);
                EventBus.getDefault().post(new LoginEvent(friendListBean));
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

    boolean isfirst = true;
    @Subscribe(threadMode = ThreadMode.BACKGROUND)
    public void onUpdate(LoginEvent event){
        LogUtil.e("LOGIN EVENT ");
        DBprovider dBprovider = DBprovider.getInstance(IndexActivity.getIndexActivityContext());
        List<FriendListBean.Info> list = event.getEvent().getServer().getInfo();
        for(int i = 0; i<list.size(); i++){
            int count = list.get(i).getInfolist().size();
            for (int j = 0;j<count; j++){
                FriendListBean.Infolist temp = list.get(i).getInfolist().get(j);
                ContentValues values = new ContentValues();
                values.put(DatabaseHelper.USER_NICKNAME,temp.getNickname());
                values.put(DatabaseHelper.USER_AVATAR,temp.getPortrait());
                values.put(DatabaseHelper.USER_EASEMOBID,temp.getName());
                values.put(DatabaseHelper.Mobile,temp.getMobile());
                if(isfirst){
                    values.put(DatabaseHelper.USER_NAME,temp.getName());
                    dBprovider.Insert(DatabaseHelper.TABLE_USER_INFORMATION,values);
                }else {
                    dBprovider.Update(DatabaseHelper.TABLE_USER_INFORMATION,values,DatabaseHelper.USER_NAME+"=?",new String[]{temp.getName()});
                }
            }
        }
        isfirst = false;
        dBprovider.Query(DatabaseHelper.TABLE_USER_INFORMATION);
    }

    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }
}
