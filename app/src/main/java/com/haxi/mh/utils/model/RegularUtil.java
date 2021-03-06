package com.haxi.mh.utils.model;


import java.util.regex.Pattern;


/**
 * 正则表达式工具类
 * Created by Han on 2017/12/11
 * Email:yin13753884368@163.com
 * CSDN:http://blog.csdn.net/yin13753884368/article
 * Github:https://github.com/yin13753884368
 */
public class RegularUtil {

    static final String TAG = "RegularUtil";
    /**
     * 正则表达式：验证用户名
     */
    public static final String REGEX_USERNAME = "^[a-zA-Z]\\w{5,17}$";

    /**
     * 正则表达式：验证密码
     */
    public static final String REGEX_PASSWORD = "^[a-zA-Z0-9]{6,16}$";

    /**
     * 正则表达式：验证手机号
     */
    public static final String REGEX_MOBILE = "^((13[0-9])|(15[^4,\\D])|(17[0-9])|(18[0-9]))\\d{8}$";

    /**
     * 正则表达式：验证数字
     */
    public static final String REGEX_NUMBER = "\\d+";


    /**
     * 正则表达式：验证邮箱
     */
    //    public static final String REGEX_EMAIL = "^([a-zA-Z0-9_\\.-])+@([a-zA-Z0-9_-])+(.[a-zA-Z0-9_-])+";
    //    public static final String REGEX_EMAIL = "\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*";
    public static final String REGEX_EMAIL = "^(\\w|\\-)+(\\.(\\w|\\-)+)*@(\\w)+((\\.\\w+)+)$";

    /**
     * 正则表达式：验证汉字
     */
    public static final String REGEX_CHINESE = "^[\\u4e00-\\u9fa5],{0,6}$";

    /**
     * 正则表达式：验证身份证
     */
    public static final String REGEX_ID_CARD = "(^\\d{18}$)|(^\\d{15}$)";

    /**
     * 正则表达式：验证IP地址
     */
    public static final String REGEX_IP_ADDR = "(25[0-5]|2[0-4]\\d|[0-1]\\d{2}|[1-9]?\\d)";


    /**
     * 正则表达式：验证字母
     */
    public static final String REGEX_WORD = "^[a-zA-Z]+$";


    /**
     * 校验字母
     *
     * @param username
     * @return
     */
    public static boolean isWord(String username) {
        return Pattern.matches(REGEX_WORD, username);
    }

    /**
     * 校验用户名
     *
     * @param username
     * @return 校验通过返回true，否则返回false
     */
    public static boolean isUsername(String username) {
        return Pattern.matches(REGEX_USERNAME, username);
    }

    /**
     * 校验密码
     *
     * @param password
     * @return 校验通过返回true，否则返回false
     */
    public static boolean isPassword(String password) {
        return Pattern.matches(REGEX_PASSWORD, password);
    }

    /**
     * 校验手机号
     *
     * @param mobile
     * @return 校验通过返回true，否则返回false
     */
    public static boolean isMobile(String mobile) {
        return Pattern.matches(REGEX_MOBILE, mobile);
    }

    /**
     * 校验邮箱
     *
     * @param email
     * @return 校验通过返回true，否则返回false
     */
    public static boolean isEmail(String email) {
        return Pattern.matches(REGEX_EMAIL, email);
    }

    /**
     * 校验汉字
     *
     * @param chinese
     * @return 校验通过返回true，否则返回false
     */
    public static boolean isChinese(String chinese) {
        if (StringUtil.isEmpty(chinese))
            return false;
        Boolean isChinese;
        for (char ch : chinese.toCharArray()) {
            if (!Pattern.matches(REGEX_CHINESE, String.valueOf(ch))) {
                return false;
            }
        }
        return true;
    }

    /**
     * 校验身份证
     *
     * @param idCard
     * @return 校验通过返回true，否则返回false
     */
    public static boolean isIDCard(String idCard) {
        return Pattern.matches(REGEX_ID_CARD, idCard);
    }


    /**
     * 校验IP地址
     *
     * @param ipAddr
     * @return
     */
    public static boolean isIPAddr(String ipAddr) {
        return Pattern.matches(REGEX_IP_ADDR, ipAddr);
    }

    /**
     * 校验数字
     *
     * @param number
     * @return
     */
    public static boolean isNumber(String number) {
        return Pattern.matches(REGEX_NUMBER, number);
    }


}
