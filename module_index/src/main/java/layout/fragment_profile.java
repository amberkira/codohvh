package layout;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.JsonReader;
import android.util.JsonToken;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.codo.amber_sleepeanuty.library.Constant;
import com.codo.amber_sleepeanuty.library.network.APIService;
import com.codo.amber_sleepeanuty.library.util.ImageLoader;
import com.codo.amber_sleepeanuty.library.util.LogUtil;
import com.codo.amber_sleepeanuty.library.util.SpUtil;
import com.codo.amber_sleepeanuty.module_index.R;
import com.google.gson.Gson;
import com.google.gson.JsonParser;


import org.json.JSONObject;
import org.json.JSONTokener;

import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.ResponseBody;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

import static android.app.Activity.RESULT_OK;
import static android.content.Context.LOCATION_SERVICE;

/**
 * Created by amber_sleepeanuty on 2017/5/15.
 */

public class fragment_profile extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";


    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private CircleImageView userIcon;
    private View v;


    public fragment_profile() {
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
    public static fragment_profile newInstance(String param1, String param2) {
        fragment_profile fragment = new fragment_profile();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.frangment_profile, container, false);
        userIcon = (CircleImageView) v.findViewById(R.id.circle_img_profile);
        Glide.with(this.getActivity()).load(SpUtil.getString(Constant.USER_AVATAR,null)).
                placeholder(this.getActivity().getResources().getDrawable(R.mipmap.default_avatar))
                .into(userIcon);
        userIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(intent,1);
            }
        });
        return  v;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    public void setUserIcon(){
        Uri uri = Uri.parse(SpUtil.getString("useravatar",null));
        String file = ImageLoader.getImageAbsolutePath(this.getActivity(),uri);
        String id = SpUtil.getString(Constant.CURRENT_LOGIN_ID,null);
        String session = SpUtil.getString(Constant.SESSION_ID,null);
        LogUtil.e(id+" "+session+"  "+file);
        Bitmap bitmap = ImageLoader.loadBitmapFromUri(uri);
        if(file!=null){
            userIcon.setImageBitmap(bitmap);
            APIService.Factory.createService(this.getContext()).uploadAvatar(
                    id,
                    session,
                    APIService.Factory.ImageRequestBodyBuilder(file)
            ).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Subscriber<ResponseBody>() {
                @Override
                public void onCompleted() {

                }

                @Override
                public void onError(Throwable e) {
                    LogUtil.e(e.toString());
                }

                @Override
                public void onNext(ResponseBody responseBody) {
                    String json = responseBody.toString();
                    LogUtil.e("");
                }
            });
        }

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode==1&&resultCode==RESULT_OK){
            Log.e("enter","enter!!!!!!");
            Uri uri = data.getData();
            String userIconUri = uri.toString();
            SpUtil.saveString("useravatar",userIconUri);
            setUserIcon();
        }
    }
}
