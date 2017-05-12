package com.codo.amber_sleepeanuty.library.network;

import android.content.Context;
import android.util.Log;

import com.codo.amber_sleepeanuty.library.bean.HospitalsBean;
import com.codo.amber_sleepeanuty.library.bean.LoginBean;
import com.codo.amber_sleepeanuty.library.bean.RegisterBean;
import com.codo.amber_sleepeanuty.library.util.AppConfig;
import com.codo.amber_sleepeanuty.library.util.AppUtil;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**ApiService
 * Created by zhangstar on 2017/3/10.
 */

public interface APIService {
    @GET("hvh/")
    Observable<RegisterBean> register(@Query("action")String action, @Query("mobile")String number, @Query("pw") String phw);

    @GET("hvh/")
    Observable<LoginBean> login(@Query("action")String action, @Query("mobile")String number, @Query("pw") String phw);

    @GET("hvh/v1/hospital")
    Observable<HospitalsBean> getHospitalList(@Query("start")int start, @Query("count")int count);


    class Factory {
        private static String TAG = "factory";

        public static APIService createService(final Context context) {
            //日志拦截器
            HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
            logging.setLevel(HttpLoggingInterceptor.Level.BODY);

            /**
             * 获取缓存
             */
            Interceptor baseInterceptor = new Interceptor() {
                @Override
                public Response intercept(Chain chain) throws IOException {
                    Request request = chain.request();
                    if (!AppUtil.isNetWorkAvailable(context)) {
                        /**
                         * 离线缓存控制  总的缓存时间=在线缓存时间+设置离线缓存时间
                         */
                        int maxStale = 60 * 60 * 24 * 28; // 离线时缓存保存4周,单位:秒
                        CacheControl tempCacheControl = new CacheControl.Builder()
                                .onlyIfCached()
                                .maxStale(maxStale, TimeUnit.SECONDS)
                                .build();
                        request = request.newBuilder()
                                .cacheControl(tempCacheControl)
                                .build();
                        Log.i(TAG, "intercept:no network ");
                    }
                    return chain.proceed(request);
                }
            };
            //只有 网络拦截器环节 才会写入缓存写入缓存,在有网络的时候 设置缓存时间
            Interceptor rewriteCacheControlInterceptor = new Interceptor() {
                @Override
                public Response intercept(Chain chain) throws IOException {
                    Request request = chain.request();
                    Response originalResponse = chain.proceed(request);
                    int maxAge = 1 * 60; // 在线缓存在1分钟内可读取 单位:秒
                    return originalResponse.newBuilder()
                            .removeHeader("Pragma")// 清除头信息，因为服务器如果不支持，会返回一些干扰信息，不清除下面无法生效
                            .removeHeader("Cache-Control")
                            .header("Cache-Control", "public, max-age=" + maxAge)
                            .build();
                }
            };
            //设置缓存路径 内置存储
            //File httpCacheDirectory = new File(context.getCacheDir(), "responses");
            //外部存储
            File httpCacheDirectory = new File(context.getExternalCacheDir(), "responses");
            //设置缓存 10M
            int cacheSize = 10 * 1024 * 1024;
            Cache cache = new Cache(httpCacheDirectory, cacheSize);

            OkHttpClient client;
            //如果默认为 缓存数据
            if (AppConfig.getIsCache(context)) {
                client = new OkHttpClient.Builder()
                        .cache(cache)
                        .addInterceptor(logging)
                        .addInterceptor(baseInterceptor)
                        .addNetworkInterceptor(rewriteCacheControlInterceptor)
                        .connectTimeout(15, TimeUnit.SECONDS)
                        .build();
            } else {
                client = new OkHttpClient.Builder()
                        .addInterceptor(logging)
                        .connectTimeout(15, TimeUnit.SECONDS)
                        .build();
            }

            return new Retrofit.Builder()
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                    .baseUrl(BaseUrl.BASE_URL)
                    .client(client)
                    .build()
                    .create(APIService.class);
        }
    }
    //endregion

}
