package layout;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.codo.amber_sleepeanuty.library.util.LogUtil;
import com.codo.amber_sleepeanuty.module_index.R;
import com.sivin.Banner;
import com.sivin.BannerAdapter;

import java.util.ArrayList;
import java.util.List;

public class fragment_index extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public Banner banner;
    public BannerAdapter<Integer> bannerAdapter;
    public List<Integer> mDatas;

    public fragment_index() {
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment fragment_index.
     */
    // TODO: Rename and change types and number of parameters
    public static fragment_index newInstance(String param1, String param2) {
        fragment_index fragment = new fragment_index();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mDatas = new ArrayList<>();
        mDatas.add(R.drawable.banner);
        mDatas.add(R.drawable.banner);
        mDatas.add(R.drawable.banner);
        mDatas.add(R.drawable.banner);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        LogUtil.e("  frag init!");
        View v = inflater.inflate(R.layout.fragment_fragment_index, container, false);
        banner = (Banner) v.findViewById(R.id.index_banner);
        bannerAdapter = new BannerAdapter<Integer>(mDatas) {
            @Override
            protected void bindTips(TextView tv, Integer list) {
                //none
            }

            @Override
            public void bindImage(ImageView imageView, Integer list) {
                imageView.setBackground(getResources().getDrawable(mDatas.get(0)));
            }
        };
        banner.setBannerAdapter(bannerAdapter);
        banner.notifyDataHasChanged();
        return v;
    }


}
