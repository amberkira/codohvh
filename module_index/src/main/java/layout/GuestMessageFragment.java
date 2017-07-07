package layout;

import android.content.Context;
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
import android.widget.LinearLayout;

import com.codo.amber_sleepeanuty.library.bean.IMMsgBean;
import com.codo.amber_sleepeanuty.library.ui.IMItemDecoration;
import com.codo.amber_sleepeanuty.library.util.LogUtil;
import com.codo.amber_sleepeanuty.module_index.R;
import com.codo.amber_sleepeanuty.module_index.adapter.MessageItemLoadAdapter;
import com.codo.amber_sleepeanuty.module_index.adapter.OnNewMsgListener;
import com.hyphenate.EMMessageListener;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMConversation;
import com.hyphenate.chat.EMMessage;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Created by amber_sleepeanuty on 2017/6/19.
 */

public class GuestMessageFragment extends Fragment implements OnNewMsgListener{

    private RecyclerView mRecyclerView;
    private MessageItemLoadAdapter mAdapter;
    private HashMap<String,IMMsgBean> mMsgMap;
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            if(msg.what==1){
                mAdapter.refreshList();
            }
        }
    };

    public static GuestMessageFragment newInstance() {
        Bundle args = new Bundle();
        GuestMessageFragment fragment = new GuestMessageFragment();
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
        View v = inflater.inflate(R.layout.fragment_guest_list_in_message,container,false);

        Map<String,EMConversation> rawMap = EMClient.getInstance().chatManager().getAllConversations();
        mMsgMap = reformMap(rawMap);

        mAdapter = new MessageItemLoadAdapter(getContext(), mMsgMap);
        mAdapter.setOnNewMsgListener(this);
        mRecyclerView = (RecyclerView) v.findViewById(R.id.guest_message_list);
        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(manager);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.addItemDecoration(new IMItemDecoration(getContext(), LinearLayout.VERTICAL));

        EMMessageListener msgListener = new EMMessageListener() {
            @Override
            public void onMessageReceived(List<EMMessage> messages) {
                HashMap<String,IMMsgBean> map = new HashMap<>();
                for(EMMessage m:messages){
                    IMMsgBean bean = new IMMsgBean(true,m);
                    map.put(m.getFrom(),bean);
                }
                mAdapter.updateLastestMessage(map);
            }

            @Override
            public void onCmdMessageReceived(List<EMMessage> messages) {
                //收到透传消息
            }

            @Override
            public void onMessageRead(List<EMMessage> messages) {
                //收到已读回执
            }

            @Override
            public void onMessageDelivered(List<EMMessage> message) {
                //收到已送达回执
            }

            @Override
            public void onMessageChanged(EMMessage message, Object change) {
                //消息状态变动
            }
        };
        EMClient.getInstance().chatManager().addMessageListener(msgListener);

        return v;
    }

    @Override
    public void notifyNewMsg() {
        handler.sendEmptyMessage(1);
    }

    /**
     * 取出消息整理到新map发送给adapter使用含以前消息
     * @param rawMap
     * @return
     */
    public HashMap<String,IMMsgBean> reformMap(Map<String,EMConversation> rawMap){
        HashMap<String,IMMsgBean> map = new HashMap<>();
        EMConversation con = null;
        Iterator iter = rawMap.entrySet().iterator();
        while (iter.hasNext()){
            Map.Entry entry = (Map.Entry) iter.next();
            String name = (String)entry.getKey();
            con = (EMConversation) entry.getValue();
            if(con.getLastMessage()!=null){
                IMMsgBean bean = new IMMsgBean(true,con.getLastMessage());
                map.put(name,bean);
            }
        }
        return map;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    public void onStart() {
        super.onStart();
        Map<String,EMConversation> rawMap = EMClient.getInstance().chatManager().getAllConversations();
        mMsgMap = reformMap(rawMap);
        mAdapter.updateLastestMessage(mMsgMap);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }
}
