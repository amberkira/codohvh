package layout;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.codo.amber_sleepeanuty.library.bean.Top250Bean;
import com.codo.amber_sleepeanuty.library.network.APIService;
import com.codo.amber_sleepeanuty.module_login.R;
import com.codo.amber_sleepeanuty.module_login.adapter.RvAdapter;

import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;


public class mainpage_fragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener{

    private RecyclerView mRv;
    private RvAdapter mAdapter;

    public mainpage_fragment() {
        mAdapter = new RvAdapter();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_mainpage_fragment, container, false);
        mRv = (RecyclerView) v.findViewById(R.id.mainpage_rv);
        View header = LayoutInflater.from(container.getContext()).inflate(R.layout.mainpage_header_transparent,mRv,false);
        View footer = LayoutInflater.from(container.getContext()).inflate(R.layout.mainpage_footer_transparent,mRv,false);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(v.getContext());
        mRv.setLayoutManager(linearLayoutManager);
        mRv.setAdapter(mAdapter);
        mAdapter.initData();//这步应该在内部用异步线程执行
        mAdapter.setHeaderView(header);
        mAdapter.setFooterView(footer);
        return v;
    }

    @Override
    public void onRefresh() {

    }

    public void fetchData(final int start, int count){
        APIService.Factory.createService(getActivity()).Top250(start,count)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).subscribe(new Observer<Top250Bean>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(Top250Bean top250Bean) {

            }
        });

    }
}
