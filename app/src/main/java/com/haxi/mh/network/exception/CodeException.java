package com.haxi.mh.network.exception;

import android.support.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;


/**
 * 自定义错误code类型:注解写法
 * 可自由扩展
 * Created by Han on 2017/12/16
 * Email:yin13753884368@163.com
 * CSDN:http://blog.csdn.net/yin13753884368/article
 * Github:https://github.com/yin13753884368
 */

public class CodeException {

    /*网络异常*/
    public static final int NET_ERROR = 0x101;

    /*超时异常*/
    public static final int RUNTIME_ERROR = 0x102;

    /*http异常*/
    public static final int HTTP_ERROR = 0x103;

    /*json异常*/
    public static final int JSON_ERROR = 0x104;

    /*无法解析该域名异常*/
    public static final int UNKOWNHOST_ERROR = 0x105;

    /*未知异常*/
    public static final int UNKNOWN_ERROR = 0x106;

    // 定义适用于参数的注解，限定取值范围为{NET_ERROR, HTTP_ERROR, RUNTIME_ERROR, UNKNOWN_ERROR, JSON_ERROR, UNKOWNHOST_ERROR}
    @IntDef({NET_ERROR, HTTP_ERROR, RUNTIME_ERROR, UNKNOWN_ERROR, JSON_ERROR, UNKOWNHOST_ERROR})
    @Retention(RetentionPolicy.SOURCE)


    public @interface CodeEp {
    }

}
