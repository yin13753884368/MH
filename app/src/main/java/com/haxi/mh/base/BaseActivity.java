package com.haxi.mh.base;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.MotionEvent;
import android.view.inputmethod.InputMethodManager;

import com.haxi.mh.MyApplication;
import com.haxi.mh.model.MessageEvent;
import com.haxi.mh.utils.ui.ActivityManager;
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity;
import com.umeng.message.PushAgent;

import org.bouncycastle.jcajce.provider.symmetric.ARC4;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Activity基类
 * Created by Han on 2017/12/13
 * Email:yin13753884368@163.com
 * CSDN:http://blog.csdn.net/yin13753884368/article
 * Github:https://github.com/yin13753884368
 */

public abstract class BaseActivity extends RxAppCompatActivity {
    protected BaseActivity mActivity;
    private Unbinder bind;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutRes());
        bind = ButterKnife.bind(this);
        this.mActivity = this;
        //竖屏
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        EventBus.getDefault().register(this);
        ActivityManager.getInstances().add(this);
        getData();
        //沉浸式状态栏
        //        if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.KITKAT){
        //            //透明状态栏
        //            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        //            //透明导航栏
        //            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        //        }
        MyApplication.getRefWatcher().watch(this);
        PushAgent.getInstance(BaseActivity.this).onAppStart();
    }


    /**
     * 获取资源文件
     *
     * @return
     */
    protected abstract int getLayoutRes();

    /**
     * 获取数据
     */
    protected abstract void getData();

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(MessageEvent event) {

    }


    /**
     * 隐藏输入法
     *
     * @param event
     * @return
     */
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (null != this.getCurrentFocus()) {
            InputMethodManager mInputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            //flags --->> RESULT_UNCHANGED_SHOWN  0
            return mInputMethodManager.hideSoftInputFromWindow(this.getCurrentFocus().getWindowToken(), 0);
        }
        return super.onTouchEvent(event);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        bind.unbind();
        EventBus.getDefault().unregister(this);
        ActivityManager.getInstances().remove(this);
    }


}
