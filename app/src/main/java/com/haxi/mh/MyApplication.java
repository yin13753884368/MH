package com.haxi.mh;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.Application;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Process;
import android.os.StrictMode;
import android.support.annotation.NonNull;
import android.support.multidex.MultiDex;
import android.text.TextUtils;
import android.util.Log;

import com.haxi.mh.network.manager.RxRetrofitApp;
import com.haxi.mh.utils.model.LogUtils;
import com.haxi.mh.utils.ui.smartrefreshlayout.api.DefaultRefreshFooterCreator;
import com.haxi.mh.utils.ui.smartrefreshlayout.api.DefaultRefreshHeaderCreator;
import com.haxi.mh.utils.ui.smartrefreshlayout.api.RefreshFooter;
import com.haxi.mh.utils.ui.smartrefreshlayout.api.RefreshHeader;
import com.haxi.mh.utils.ui.smartrefreshlayout.api.RefreshLayout;
import com.haxi.mh.utils.ui.smartrefreshlayout.footer.ClassicsFooter;
import com.haxi.mh.utils.ui.smartrefreshlayout.header.ClassicsHeader;
import com.meizu.cloud.pushsdk.PushManager;
import com.meizu.cloud.pushsdk.util.MzSystemUtils;
import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.Logger;
import com.squareup.leakcanary.LeakCanary;
import com.squareup.leakcanary.RefWatcher;
import com.tencent.bugly.Bugly;
import com.tencent.bugly.crashreport.CrashReport;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.umeng.commonsdk.UMConfigure;
import com.umeng.message.IUmengRegisterCallback;
import com.umeng.message.PushAgent;
import com.xiaomi.channel.commonutils.logger.LoggerInterface;
import com.xiaomi.mipush.sdk.MiPushClient;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.List;

import static com.haxi.mh.utils.ui.smartrefreshlayout.SmartRefreshLayout.setDefaultRefreshFooterCreator;
import static com.haxi.mh.utils.ui.smartrefreshlayout.SmartRefreshLayout.setDefaultRefreshHeaderCreator;


/**
 * 1.获取主线程 名称 id
 * 2.获取上下文
 * Created by Han on 2017/12/11
 * Email:yin13753884368@163.com
 * CSDN:http://blog.csdn.net/yin13753884368/article
 * Github:https://github.com/yin13753884368
 */

public class MyApplication extends Application {
    //获取上下文
    public static MyApplication mContext;
    //获取主线程 名称
    private static String mThreadName;
    //获取主线程 id
    private static long mTthreadId;
    //获取到主线程的handler
    private static Handler mMainThreadHandler = null;
    //统计activity 生命周期
    private int appCount = 0;
    //leakcanary
    private RefWatcher watcher;
    private static final String APP_ID = "";
    private static final String APP_KEY = "";

    private String UMENG_APPKEY = "5a3cf80fa40fa30d2200000e";
    private String UMENG_MESSAGE_SECRET = "762ddec636c570e9559a67accf4564ef";
    private String UMENG_CHANNEL = "UMENG";

    static {
        //设置全局的Header构建器
        setDefaultRefreshHeaderCreator(new DefaultRefreshHeaderCreator() {
            @Override
            public RefreshHeader createRefreshHeader(Context context, RefreshLayout layout) {
                layout.setPrimaryColorsId(R.color.white, R.color.grey_df);//全局设置主题颜色
                return new ClassicsHeader(context);//.setTimeFormat(new DynamicTimeFormat("更新于 %s"));//指定为经典Header，默认是 贝塞尔雷达Header
            }
        });
        //设置全局的Footer构建器
        setDefaultRefreshFooterCreator(new DefaultRefreshFooterCreator() {
            @NonNull
            @Override
            public RefreshFooter createRefreshFooter(@NonNull Context context, @NonNull RefreshLayout layout) {
                return new ClassicsFooter(context).setDrawableSize(20);
            }
        });

    }


    @Override
    public void onCreate() {
        super.onCreate();
        mContext = this;
        Thread thread = Thread.currentThread();
        mThreadName = thread.getName();
        mTthreadId = thread.getId();
        mMainThreadHandler = new Handler();

        //捕获异常 与 bugly 不能同时开启
        //HandlerException.getInstance().init(mContext);

        //Bugly获取渠道号
        CrashReport.UserStrategy strategy = new CrashReport.UserStrategy(mContext);
        strategy.setAppChannel(getChannel());
        //注册Bugly 如果您之前使用过Bugly SDK，请将以下这句注释掉
        //CrashReport.initCrashReport(getApplicationContext(), "58aa13feb7", true, strategy);
        Bugly.init(getApplicationContext(), "58aa13feb7", true, strategy);

        //初始化Logger
        Logger.addLogAdapter(new AndroidLogAdapter());
        //        Logger.clearLogAdapters(); //清除log

        //设置后http请求会被拦截并且输出
        RxRetrofitApp.getInstances().setDebug();

        //友盟统计
        UMConfigure.init(this, UMENG_APPKEY, UMENG_CHANNEL, UMConfigure.DEVICE_TYPE_PHONE, UMENG_MESSAGE_SECRET);

        PushAgent mPushAgent = PushAgent.getInstance(this);
        //注册推送服务，每次调用register方法都会回调该接口
        mPushAgent.register(new IUmengRegisterCallback() {

            @Override
            public void onSuccess(String deviceToken) {
                //注册成功会返回device token
            }

            @Override
            public void onFailure(String s, String s1) {

            }
        });

        /*小米推送 start*/
        if (shouldInit()) {
            MiPushClient.registerPush(this, "2882303761517774776", "5441777412776");
        }

        LoggerInterface newLogger = new LoggerInterface() {
            @Override
            public void setTag(String tag) {
                // ignore
            }

            @Override
            public void log(String content, Throwable t) {
                Log.e("------ xiaomi", content, t);
            }

            @Override
            public void log(String content) {
                Log.e("------ xiaomi", content);
            }
        };

        com.xiaomi.mipush.sdk.Logger.setLogger(this, newLogger);
        /*小米推送 end*/

        /*魅族推送 start*/
        if (MzSystemUtils.isBrandMeizu(this)) {
            PushManager.register(this, APP_ID, APP_KEY);
        }
        /*魅族推送 end*/

        String processName = this.getProcessName();

        if (!TextUtils.isEmpty(processName) && processName.equals(this.getPackageName())) {//判断进程名，保证只有主进程运行
            //在这里进行主进程初始化逻辑操作
            LogUtils.i("myApplication", "oncreate+主进程");
        }

        LogUtils.i("myApplication", "oncreate" + processName);

        //微信支付
        regToWx();

        //监听activity生命周期
        registerActivityLifecycleCallbacks(new ActivityLifecycleCallbacks() {
            @Override
            public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
            }

            @Override
            public void onActivityStarted(Activity activity) {
                appCount++;
            }

            @Override
            public void onActivityResumed(Activity activity) {
            }

            @Override
            public void onActivityPaused(Activity activity) {
            }

            @Override
            public void onActivityStopped(Activity activity) {
                appCount--;
            }

            @Override
            public void onActivitySaveInstanceState(Activity activity, Bundle outState) {

            }

            @Override
            public void onActivityDestroyed(Activity activity) {

            }
        });

        //webRTC 初始化
//        PeerConnectionFactory.initialize(PeerConnectionFactory
//                .InitializationOptions
//                .builder(mContext)
//                .createInitializationOptions());

        /* LeakCanary */
        if (LeakCanary.isInAnalyzerProcess(this)) {
            // This process is dedicated to LeakCanary for heap analysis.
            // You should not init your app in this process.
            return;
        }
        watcher = LeakCanary.install(this);

        //获取打开相机权限
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {
            StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
            StrictMode.setVmPolicy(builder.build());
            builder.detectFileUriExposure();
        }
    }

    public static RefWatcher getRefWatcher() {
        return mContext.watcher;
    }

    private void regToWx() {
        IWXAPI wxapi = WXAPIFactory.createWXAPI(mContext, "");
        wxapi.registerApp("");

    }


    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(base);
    }

    /**
     * 获取上下文
     *
     * @return
     */
    public static MyApplication getMyApplication() {
        return mContext;
    }

    /**
     * 获取主线程 名称
     *
     * @return
     */
    public static String getMainThreadName() {
        return mThreadName;
    }

    /**
     * 获取主线程 id
     *
     * @return
     */
    public static long getMainThreadId() {
        return mTthreadId;
    }

    /**
     * 获取主线程
     *
     * @return
     */
    public static Handler getMainThreadHandler() {
        return mMainThreadHandler;
    }

    /**
     * 获取渠道id
     *
     * @return
     */
    private String getChannel() {
        if (mContext == null) {
            return null;
        }
        String channel = null;
        try {
            ApplicationInfo info = mContext.getPackageManager().getApplicationInfo(mContext.getPackageName(), PackageManager.GET_META_DATA);
            if (info != null && info.metaData != null) {
                channel = info.metaData.getString("UMENG_CHANNEL");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return channel;
    }

    /**
     * 获取当前进程的包名
     *
     * @return
     */
    public String getProcessName() {
        try {
            File file = new File("/proc/" + android.os.Process.myPid() + "/" + "cmdline");
            BufferedReader mBufferedReader = new BufferedReader(new FileReader(file));
            String processName = mBufferedReader.readLine().trim();
            mBufferedReader.close();
            return processName;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 统计APP生命周期的
     *
     * @return
     */
    public int getAppCount() {
        return appCount;
    }

    /**
     * 设置APP生命周期的
     *
     * @param appCount
     */
    public void setAppCount(int appCount) {
        this.appCount = appCount;
    }

    private boolean shouldInit() {
        ActivityManager am = ((ActivityManager) getSystemService(Context.ACTIVITY_SERVICE));
        List<ActivityManager.RunningAppProcessInfo> processInfos = am.getRunningAppProcesses();
        String mainProcessName = getPackageName();
        int myPid = Process.myPid();
        for (ActivityManager.RunningAppProcessInfo info : processInfos) {
            if (info.pid == myPid && mainProcessName.equals(info.processName)) {
                return true;
            }
        }
        return false;
    }
}
