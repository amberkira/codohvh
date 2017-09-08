package layout;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.util.JsonReader;
import android.util.JsonToken;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
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


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;

import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.ResponseBody;
import okio.ByteString;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

import static android.app.Activity.RESULT_OK;
import static android.content.Context.LOCATION_SERVICE;

/**
 * Created by amber_sleepeanuty on 2017/5/15.
 */

public class fragment_profile extends Fragment {


    private CircleImageView mUserIcon;
    private TextView mSetting;
    private TextView mName;
    private TextView mCredit;
    private TextView mInfo;
    private TextView mMobile;
    private TextView mCollect;
    private TextView mExam;
    private TextView mService;
    private TextView mRating;



    public fragment_profile() {
    }


    public static fragment_profile newInstance(String param1, String param2) {
        fragment_profile fragment = new fragment_profile();
        Bundle args = new Bundle();
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
        View v = inflater.inflate(R.layout.frangment_profile, container, false);

        mSetting = (TextView) v.findViewById(R.id.tv_profile_setting);
        mName = (TextView) v.findViewById(R.id.profile_name);
        mMobile = (TextView) v.findViewById(R.id.profile_mobile);
        mCollect = (TextView) v.findViewById(R.id.profile_colloct);
        mExam = (TextView) v.findViewById(R.id.profile_exam);
        mService = (TextView) v.findViewById(R.id.profile_service);
        mRating = (TextView) v.findViewById(R.id.profile_rating);
        mCredit = (TextView) v.findViewById(R.id.profile_credit);
        mInfo = (TextView) v.findViewById(R.id.profile_info);
        mUserIcon = (CircleImageView) v.findViewById(R.id.circle_img_profile);
        String name = SpUtil.getString(Constant.USER_AVATAR,null);
        String sessionId = SpUtil.getString(Constant.SESSION_ID,null);
        Glide.with(this.getActivity()).load(SpUtil.getString(Constant.USER_AVATAR,null))
                .into(mUserIcon);


        mUserIcon.setOnClickListener(new View.OnClickListener() {
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
        Bitmap bitmap = ImageLoader.loadBitmapFromUri(uri);
        if(file!=null){
            mUserIcon.setImageBitmap(bitmap);
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
                }
            });
        }

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode==1&&resultCode==RESULT_OK){
            Uri uri = data.getData();
            String userIconUri = uri.toString();
            SpUtil.saveString(Constant.USER_AVATAR,userIconUri);
            setUserIcon();
        }
    }
}
