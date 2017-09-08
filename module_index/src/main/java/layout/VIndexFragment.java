package layout;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.alibaba.android.vlayout.DelegateAdapter;
import com.alibaba.android.vlayout.VirtualLayoutManager;
import com.alibaba.android.vlayout.layout.GridLayoutHelper;
import com.alibaba.android.vlayout.layout.LinearLayoutHelper;
import com.codo.amber_sleepeanuty.library.bean.VIndexBean;
import com.codo.amber_sleepeanuty.library.network.APIService;
import com.codo.amber_sleepeanuty.module_index.R;
import com.codo.amber_sleepeanuty.module_index.adapter.VIndexAdapter;

import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * Created by amber_sleepeanuty on 2017/8/28.
 */

public class VIndexFragment extends Fragment {
    private static final String TAG = "Vindex";
    public RecyclerView mRecyclerView;
    public VirtualLayoutManager layoutManager;
    public DelegateAdapter adapter;
    public VIndexAdapter header;
    public VIndexBean mBean;




    public View v;
    public Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            if(isLoaded&&msg.what==1){

                LinearLayoutHelper helper2 = new LinearLayoutHelper(1);
                adapter.addAdapter(new VIndexAdapter(getContext(),helper2,1,mBean,VIndexAdapter.BANNER));

                GridLayoutHelper helper3 = new GridLayoutHelper(2,4);
                adapter.addAdapter(new VIndexAdapter(getContext(),helper3,4,mBean,VIndexAdapter.ARTICLE));

                ((VIndexAdapter)adapter.findAdapterByIndex(0)).setHeadState(false);
                adapter.notifyItemChanged(0);
            }
        }
    };
    public boolean isLoaded = false;

    public VIndexFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.e("adapter","oncreate");
    }

    @Nullable
    @Override
    public View onCreateView(final LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.e("adapter","oncreateView");


        View v = inflater.inflate(R.layout.fragment_vindex,container,false);

        mRecyclerView = (RecyclerView) v.findViewById(R.id.rv_vindex);
        layoutManager = new VirtualLayoutManager(getContext());
        mRecyclerView.setLayoutManager(layoutManager);
        adapter = new DelegateAdapter(layoutManager);
        mRecyclerView.setAdapter(adapter);

        LinearLayoutHelper helper1 = new LinearLayoutHelper(1);
        VIndexAdapter adpter1 = new VIndexAdapter(getContext(),helper1,1,null,VIndexAdapter.HEAD);
        adapter.addAdapter(adpter1);


        APIService.Factory.createService(getContext())
                .index()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Action1<VIndexBean>() {
                    @Override
                    public void call(VIndexBean bean) {
                        mBean = bean;
                        isLoaded = true;
                        handler.sendEmptyMessage(1);
                    }
                });
        return v;
    }


}
