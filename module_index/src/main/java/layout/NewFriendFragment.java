package layout;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.codo.amber_sleepeanuty.library.ui.IMItemDecoration;
import com.codo.amber_sleepeanuty.module_index.R;
import com.codo.amber_sleepeanuty.module_index.adapter.NewFriendAdapter;
import com.hyphenate.EMContactListener;
import com.hyphenate.chat.EMClient;

/**
 * Created by amber_sleepeanuty on 2017/6/26.
 */

public class NewFriendFragment extends Fragment {
    RecyclerView mNewFriend;
    NewFriendAdapter mAdapter;

    public static NewFriendFragment newInstance() {
        Bundle args = new Bundle();
        NewFriendFragment fragment = new NewFriendFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_new_friend,container,false);

        mAdapter = new NewFriendAdapter(getContext());

        mNewFriend = (RecyclerView) v.findViewById(R.id.new_friend_rv);
        mNewFriend.setLayoutManager(new LinearLayoutManager(getActivity()));
        mNewFriend.setAdapter(mAdapter);
        mNewFriend.addItemDecoration(new IMItemDecoration(getContext(), LinearLayout.VERTICAL));
        EMClient.getInstance().contactManager().setContactListener(new EMContactListener() {
            @Override
            public void onContactAdded(String username) {

            }

            @Override
            public void onContactDeleted(String username) {

            }

            @Override
            public void onContactInvited(String username, String reason) {
                mAdapter.addNewApply(username,reason);
            }

            @Override
            public void onFriendRequestAccepted(String username) {

            }

            @Override
            public void onFriendRequestDeclined(String username) {

            }
        });
        return v;
    }


}
