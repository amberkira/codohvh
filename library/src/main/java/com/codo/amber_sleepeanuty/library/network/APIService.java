package com.codo.amber_sleepeanuty.library.network;

import com.codo.amber_sleepeanuty.library.bean.LoginBean;
import com.codo.amber_sleepeanuty.library.bean.RegisterBean;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**ApiService
 * Created by zhangstar on 2017/3/10.
 */

public interface APIService {
    @GET("/hvh/")
    Call<RegisterBean> register(@Query("action")String action, @Query("mobile")String number, @Query("phw") String phw);

    @GET("/hvh")
    Call<LoginBean> login(@Query("action")String action, @Query("mobile")String number, @Query("phw") String phw);
}
