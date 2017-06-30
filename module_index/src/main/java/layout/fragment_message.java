package layout;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.codo.amber_sleepeanuty.module_index.R;
import com.codo.amber_sleepeanuty.module_index.adapter.MessageFragmentAdapter;
import com.codo.amber_sleepeanuty.module_index.adapter.NewFriendAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by amber_sleepeanuty on 2017/5/17.
 */

public class fragment_message extends Fragment{

    private ViewPager mViewPager;
    private TabLayout mTabLayout;
    private MessageFragmentAdapter mMessageFragmentAdapter;
    private List<Fragment> mFragments;
    private String[] mTabName = {"消息","好友","群组","申请"};

    public fragment_message() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_messager,container,false);
        mViewPager = (ViewPager) v.findViewById(R.id.message_viewpager);
        mTabLayout = (TabLayout) v.findViewById(R.id.message_tablayout);
        initFragments();
        initView();
        return v;
    }

    private void initFragments() {
        mFragments = new ArrayList<>();
        mFragments.add(GuestMessageFragment.newInstance());
        mFragments.add(FriendListFragment.newInstance());
        mFragments.add(GuestMessageFragment.newInstance());
        mFragments.add(NewFriendFragment.newInstance());

    }

    private void initView() {
        mMessageFragmentAdapter = new MessageFragmentAdapter(getChildFragmentManager(),mFragments);
        mViewPager.setAdapter(mMessageFragmentAdapter);
        setTabs(mTabLayout,mTabName);
        mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(mTabLayout));
        mTabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(mViewPager));

    }

    private void setTabs(TabLayout layout, String[] tabName) {
        for (int i = 0; i < tabName.length; i++) {
            TabLayout.Tab tab = layout.newTab();
            tab.setText(tabName[i]);
            layout.addTab(tab);
        }
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
}
