package layout;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.codo.amber_sleepeanuty.module_login.R;
import com.codo.amber_sleepeanuty.module_login.adapter.RvAdapter;


public class mainpage_fragment extends Fragment {

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
}
