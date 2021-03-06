package com.haxi.mh.network.exception;

/**
 * 自定义缓存异常
 * Created by Han on 2017/12/18
 * Email:yin13753884368@163.com
 * CSDN:http://blog.csdn.net/yin13753884368/article
 * Github:https://github.com/yin13753884368
 */
public class CustomTimeException extends RuntimeException {
    /*网络错误*/
    public static final int NET_ERROR = 0x1002;
    /*本地无缓存错误*/
    public static final int NO_CACHE_ERROR = 0x1003;
    /*缓存过时错误*/
    public static final int CHACE_TIMEOUT_ERROR = 0x1004;

    public CustomTimeException(int resultCode) {
        getApiExceptionMessage(resultCode);
    }


    public CustomTimeException(String message) {
        super(message);
    }


    public    String getApiExceptionMessage(int code) {
        switch (code) {
            case NET_ERROR:
                return "错误：网络错误";
            case NO_CACHE_ERROR:
                return "错误：无缓存数据";
            case CHACE_TIMEOUT_ERROR:
                return "错误：缓存数据过期";
            default:
                return "错误：未知错误";
        }
    }
}
