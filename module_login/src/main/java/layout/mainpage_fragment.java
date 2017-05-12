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

import com.codo.amber_sleepeanuty.library.bean.HospitalsBean;
import com.codo.amber_sleepeanuty.library.network.APIService;
import com.codo.amber_sleepeanuty.library.ui.PullLoadRecylerView;
import com.codo.amber_sleepeanuty.library.util.LogUtil;
import com.codo.amber_sleepeanuty.module_login.R;
import com.codo.amber_sleepeanuty.module_login.adapter.HospitalsAdapter;

import java.util.List;

import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;


public class mainpage_fragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener{

    private SwipeRefreshLayout mSwipeRefreshLayout;
    private PullLoadRecylerView mRv;
    private HospitalsAdapter mAdapter;
    private HospitalsBean bean;
    protected View header;
    protected View footer;
    public static  Context c;

    public mainpage_fragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_mainpage_fragment, container, false);
        c = v.getContext();
        mSwipeRefreshLayout = (SwipeRefreshLayout) v.findViewById(R.id.mainpage_swpLayout);
        mSwipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary);
        mSwipeRefreshLayout.setOnRefreshListener(this);
        header = LayoutInflater.from(container.getContext()).inflate(R.layout.mainpage_header_transparent,container,false);
        footer = LayoutInflater.from(container.getContext()).inflate(R.layout.mainpage_footer_transparent,container,false);
        mRv = (PullLoadRecylerView) v.findViewById(R.id.mainpage_rv);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(v.getContext());
        mRv.setLayoutManager(linearLayoutManager);
        mRv.setOnLodaMoreListener(new PullLoadRecylerView.LoadMoreListener() {
            @Override
            public void loadMore(RecyclerView rv) {
                int start = bean.getPage().getStart();
                int count = bean.getPage().getCount();
                int total = bean.getPage().getTotal();
                if(bean.getPage().hasNextPage(total,count,start)){
                    fetchData(start+count-1,count);
                }else{

                }
            }
        });
        preLoad();
        return v;
    }

    private void preLoad() {
        mSwipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                mSwipeRefreshLayout.setRefreshing(true);
                onRefresh();

            }
        });
    }

    @Override
    public void onRefresh() {
        if(mAdapter!=null){
            mAdapter.clear();
        }
        fetchData(0,10);
    }

    public void fetchData(final int start, int count){

        APIService.Factory.createService(getActivity())
                .getHospitalList(start,count)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<HospitalsBean>() {
            @Override
            public void onCompleted() {
                LogUtil.e("oncompleted outlayer");
                if(mSwipeRefreshLayout!=null&&mSwipeRefreshLayout.isRefreshing()){
                    mSwipeRefreshLayout.setRefreshing(false);
                    LogUtil.e("oncompleted in if");
                }
            }

            @Override
            public void onError(Throwable e) {
                e.printStackTrace();
                if(mSwipeRefreshLayout!=null&&mSwipeRefreshLayout.isRefreshing()){
                    mSwipeRefreshLayout.setRefreshing(false);
                }

            }

            @Override
            public void onNext(HospitalsBean hospitalsBean) {
                 bean = hospitalsBean;
                if(null!=hospitalsBean){
                    if(mAdapter==null){
                        mAdapter = new HospitalsAdapter(c,hospitalsBean.getSubjects(),R.layout.item);
                        mRv.setAdapter(mAdapter);
                        mAdapter.setHeader(header);
                        mAdapter.setFooter(footer);
                    }else {
                        mAdapter.updata(hospitalsBean.getSubjects());
                    }

                }
            }
        });

    }
}
