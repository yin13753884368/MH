package com.haxi.mh.mvp.present;

import com.haxi.mh.mvp.base.BaseActivityM;
import com.haxi.mh.mvp.entity.WxInfoDao;
import com.haxi.mh.mvp.model.OnLoadDataListener;
import com.haxi.mh.mvp.model.WxModel;
import com.haxi.mh.mvp.view.IWxView;

import java.util.List;

/**
 * 微信支付 present
 * Created by Han on 2018/8/29
 * Email:yin13753884368@163.com
 * CSDN:http://blog.csdn.net/yin13753884368/article
 * Github:https://github.com/yin13753884368
 */
public class WxPresent implements OnLoadDataListener<Object> {
    private WxModel wxModel;
    private BaseActivityM baseActivityM;
    private IWxView iWxView;


    public WxPresent(BaseActivityM baseActivityM, IWxView iWxView) {
        this.baseActivityM = baseActivityM;
        this.iWxView = iWxView;
        wxModel = new WxModel(baseActivityM, this);
    }

    public void loadData() {
        iWxView.loading();
        wxModel.load();
        //定时器
//        Observable.timer(2000, TimeUnit.MILLISECONDS)
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new Observer<Long>() {
//            @Override
//            public void onSubscribe(Disposable d) {
//
//            }
//
//            @Override
//            public void onNext(Long aLong) {
//                iWxView.loadingSuccess();
//                iWxView.setText(aLong);
//            }
//
//            @Override
//            public void onError(Throwable e) {
//                iWxView.loadingError();
//            }
//
//            @Override
//            public void onComplete() {
//
//            }
//        });


    }

    public void onClick() {
        wxModel.onClick();
    }

    @Override
    public void error(int type) {
        if (type == 1) {
            iWxView.loadingError();
        }
    }

    @Override
    public void success(int type, Object object) {
        iWxView.loadingSuccess();
        switch (type) {
            case 1:
                if (object != null) {
                    iWxView.setText((Long) object);
                }
                break;
            case 2:
                if (object != null) {
                    List<WxInfoDao> daoList = (List<WxInfoDao>) object;
                    if (daoList != null && daoList.size() > 0) {
                        iWxView.setAdapter(daoList);
                    }
                }
                break;
        }

    }

    @Override
    public void destroy() {
        if (wxModel != null) {
            wxModel.destroy();
        }
    }


    public void loadAdapterData() {
        wxModel.loadAdapterData();
    }
}
