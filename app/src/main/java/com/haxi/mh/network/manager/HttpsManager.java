package com.haxi.mh.network.manager;

import android.text.TextUtils;

import com.haxi.mh.MyApplication;
import com.haxi.mh.network.exception.ExceptionFunction;
import com.haxi.mh.network.exception.ResulteFunction;
import com.haxi.mh.network.exception.RetryNetworkException;
import com.haxi.mh.network.listener.HttpOnNextListener;
import com.haxi.mh.utils.model.LogUtils;
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity;

import java.io.InputStream;
import java.security.KeyStore;
import java.security.SecureRandom;
import java.security.cert.CertificateFactory;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManagerFactory;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ConnectionPool;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

/**
 * http交互处理类
 * Created by Han on 2017/12/16
 * Email:yin13753884368@163.com
 * CSDN:http://blog.csdn.net/yin13753884368/article
 * Github:https://github.com/yin13753884368
 */

public class HttpsManager {

    private HttpOnNextListener onNextListener;
    /*rxlifecycle2*/
    private RxAppCompatActivity rxAppCompatActivity;


    public HttpsManager(RxAppCompatActivity rxAppCompatActivity) {
        this.rxAppCompatActivity = rxAppCompatActivity;
    }

    public HttpsManager(HttpOnNextListener onNextListener, RxAppCompatActivity rxAppCompatActivity) {
        this.onNextListener = onNextListener;
        this.rxAppCompatActivity = rxAppCompatActivity;
    }

    public void doHttpDeal(BaseApi baseApi) {
        Retrofit retrofit = getRetrofit(baseApi.getConnectTime(), baseApi.getBaseUrl());
        requestHttp(baseApi.getObservable(retrofit), baseApi);
    }


    /**
     * 返回Retrofit
     *
     * @param connectTime
     * @param bserUrl
     * @return
     */
    public Retrofit getRetrofit(int connectTime, String bserUrl) {

        /**
         * 创建OKHttpClient对象
         */
        OkHttpClient.Builder builder = new OkHttpClient.Builder()
                .addInterceptor(new AddParameterInterceptor())//http请求拦截器 用来拼装公共参数
                .connectionPool(new ConnectionPool(6, 6, TimeUnit.MINUTES))//连接池
                .connectTimeout(connectTime, TimeUnit.SECONDS)//请求超时时间
                .readTimeout(60, TimeUnit.SECONDS)//读取超时时间
                .writeTimeout(60, TimeUnit.SECONDS);//写入超时时间

        SSLSocketFactory socketFactory = getSocketFactory();
        if (socketFactory != null) {
            builder.sslSocketFactory(socketFactory);
            builder.hostnameVerifier(new HostnameVerifier() {
                @Override
                public boolean verify(String hostname, SSLSession session) {
                    return true;
                }
            });
        }
        //请求接口返回数据拦截器
        if (RxRetrofitApp.isDebug()) {
            builder.addInterceptor(getHttpLoggingInterceptor());
        }

        /**
         * 创建Retrofit对象
         *   //增加返回值为Gson的支持(以实体类返回)
         *   //增加返回值为String的支持
         *   //增加返回值为Oservable<T>的支持
         *   addConverterFactory只能选用一个
         */
        Retrofit retrofit = new Retrofit.Builder()
                .client(builder.build())
                //增加返回值为Oservable<T>的支持
                .addCallAdapterFactory(RxJavaCallAdapterFactory.createWithScheduler(rx.schedulers.Schedulers.io()))
                //增加返回值为Gson的支持(以实体类返回)
                //.addConverterFactory(GsonConverterFactory.create())
                //增加返回值为String的支持
                .addConverterFactory(ScalarsConverterFactory.create())
                .baseUrl(bserUrl)
                .build();
        return retrofit;
    }

    /**
     * 请求数据
     *
     * @param observable
     * @param baseApi
     */
    private void requestHttp(Observable observable, BaseApi baseApi) {
        if (observable != null) {
            observable = observable
                    .retryWhen(new RetryNetworkException(baseApi.getCount(), baseApi.getDelay()))
                    //异常处理
                    .onErrorResumeNext(new ExceptionFunction())
                    //Note:手动设置在activity onDestroy的时候取消订阅 注意
                    //.compose(rxAppCompatActivity.bindUntilEvent(ActivityEvent.DESTROY))
                    .map(new ResulteFunction())
                    //http请求线程
                    .subscribeOn(Schedulers.io())
                    .unsubscribeOn(Schedulers.io())
                    //回调在主线程
                    .observeOn(AndroidSchedulers.mainThread());

            if (null != onNextListener) {
                //显示进度加载框
                ProgressObserver observer = new ProgressObserver(baseApi, onNextListener, rxAppCompatActivity);
                observable.subscribe(observer);
            }
        }
    }


    /**
     * 请求接口返回数据拦截器
     *
     * @return
     */
    private HttpLoggingInterceptor getHttpLoggingInterceptor() {
        //日志显示级别
        HttpLoggingInterceptor.Level level = HttpLoggingInterceptor.Level.BODY;
        //新建log拦截器
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
            @Override
            public void log(String message) {
                if (TextUtils.isEmpty(message)) {
                    return;
                }
                String s = message.substring(0, 1);
                if ("{".equals(s) || "[".equals(s)) {
                    //输出日志
                    if (RxRetrofitApp.isDebug()) {
                        LogUtils.e("接口返回数据--->>> " + message);
                    }
                }
            }
        });
        loggingInterceptor.setLevel(level);
        return loggingInterceptor;
    }


    /**
     * 添加证书 支持https请求
     */
    public static SSLSocketFactory getSocketFactory() {
        try {
            CertificateFactory certificateFactory = CertificateFactory.getInstance("X.509", "BC");
            KeyStore keyStore = KeyStore.getInstance(KeyStore.getDefaultType());
            keyStore.load(null);
            InputStream is = MyApplication.getMyApplication().getAssets().open("ca.crt");
            keyStore.setCertificateEntry("0", certificateFactory.generateCertificate(is));
            if (is != null) {
                is.close();
            }
            SSLContext sslContext = SSLContext.getInstance("TLS");
            TrustManagerFactory trustManagerFactory = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
            trustManagerFactory.init(keyStore);
            sslContext.init(null, trustManagerFactory.getTrustManagers(), new SecureRandom());
            return sslContext.getSocketFactory();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
