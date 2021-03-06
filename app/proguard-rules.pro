# Add project specific ProGuard rules here.
# You can control the set of applied configuration files using the
# proguardFiles setting in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}

# Uncomment this to preserve the line number information for
# debugging stack traces.
#-keepattributes SourceFile,LineNumberTable

# If you keep the line number information, uncomment this to
# hide the original source file name.
#-renamesourcefileattribute SourceFile

# 所以使用proguard时，我们需要有个配置文件告诉proguard 那些java 元素是不能混淆的。----------

# proguard 配置最常用的配置选项

# -dontwarn 缺省proguard 会检查每一个引用是否正确，但是第三方库里面往往有些不会用到的类，没有正确引用。如果不配置的话，系统就会报错。

# -keep 指定的类和类成员被保留作为入口 。

# -keepclassmembers 指定的类成员被保留。

# -keepclasseswithmembers 指定的类和类成员被保留，假如指定的类成员存在的话。


#---------------------------------1.基本不用动区域----------------------------------
-optimizationpasses 5 #代码混淆的压缩比例，值在0-7之间
-dontusemixedcaseclassnames #混淆后类名都为小写
-dontskipnonpubliclibraryclasses #指定不去忽略非公共的库的类
-dontskipnonpubliclibraryclassmembers #指定不去忽略非公共的库的类的成员
-dontpreverify #不做预校验的操作
-verbose  #生成原类名和混淆后的类名的映射文件
-printmapping proguardMapping.txt #生成原类名和混淆后的类名的映射文件
-optimizations !code/simplification/cast,!field/*,!class/merging/* #指定混淆是采用的算法
-keepattributes *Annotation*,InnerClasses #不混淆Annotation
-keepattributes Signature #不混淆泛型
-keepattributes SourceFile,LineNumberTable #抛出异常时保留代码行号
-ignorewarnings #忽略警告类
#----------------------------------------------------------------------------


# -------------------------------默认保留区 --------------------------
-keep public class * extends Android.app.Fragment
-keep public class * extends Android.app.Activity
-keep public class * extends Android.app.Application
-keep public class * extends Android.app.Service
-keep public class * extends Android.content.BroadcastReceiver
-keep public class * extends Android.content.ContentProvider
-keep public class * extends Android.app.backup.BackupAgentHelper
-keep public class * extends Android.preference.Preference
-keep public class * extends android.view.View
-keep public class com.Android.vending.licensing.ILicensingService
-keep public class * extends Android.support.** {*;}

-keepclasseswithmembernames class * { # 保持native方法不被混淆
    native <methods>;
}
-keepclassmembers class * extends android.app.Activity{
    public void *(android.view.View);
}
-keepclassmembers enum * { # 保持枚举enum类不被混淆
    public static **[] values();
    public static ** valueOf(java.lang.String);
}
-keep public class * extends android.view.View{ # 保持自定义控件不被混淆
    *** get*();
    void set*(***);
    public <init>(android.content.Context);
    public <init>(android.content.Context, android.util.AttributeSet);
    public <init>(android.content.Context, android.util.AttributeSet, int);
}
-keepclasseswithmembers class * { # 保持自定义控件不被混淆
    public <init>(android.content.Context, android.util.AttributeSet);
    public <init>(android.content.Context, android.util.AttributeSet, int);
}
-keep class * implements android.os.Parcelable { # 保持Parcelable不被混淆
  public static final android.os.Parcelable$Creator *;
}
-keepclassmembers class * implements java.io.Serializable {
    static final long serialVersionUID;
    private static final java.io.ObjectStreamField[] serialPersistentFields;
    private void writeObject(java.io.ObjectOutputStream);
    private void readObject(java.io.ObjectInputStream);
    java.lang.Object writeReplace();
    java.lang.Object readResolve();
}
-keep class **.R$* {
 *;
}
-keepclassmembers class * {
    void *(**On*Event);
}
#----------------------------------------------------------------------------

#---------------------------------webview------------------------------------
-keepclassmembers class fqcn.of.javascript.interface.for.Webview {
   public *;
}
-keepclassmembers class * extends android.webkit.WebViewClient {
    public void *(android.webkit.WebView, java.lang.String, android.graphics.Bitmap);
    public boolean *(android.webkit.WebView, java.lang.String);
}
-keepclassmembers class * extends android.webkit.WebViewClient {
    public void *(android.webkit.WebView, jav.lang.String);
}
#----------------------------------------------------------------------------


# --------- 忽略异常提示 -----------------------------------------------------
-dontwarn org.codehaus.**
#----------------------------------------------------------------------------




#---------------------------------2.实体类---------------------------------

#保持某个包下的所有类不混淆
#-keep class com.demo.login.bean.** { *; }

#保持某个类不被混淆
-keep class com.haxi.mh.utils.jsbridge.Message{*;}

#-------------------------------------------------------------------------





#---------------------------------3.第三方包-------------------------------

# XmlResourceParser异常
-keep class org.xmlpull.v1.** { *;}
-dontwarn org.xmlpull.v1.**

# butterknife混淆脚本 ----
-dontwarn butterknife.internal.**
-keep class **$$ViewInjector { *; }
#-keepnames class * { @butterknife.InjectView *;}

# Glide图片框架 ----
-keep public class * implements com.bumptech.glide.module.GlideModule
-keep public class * extends com.bumptech.glide.module.AppGlideModule
-keep public enum com.bumptech.glide.load.resource.bitmap.ImageHeaderParser$** {
  **[] $VALUES;
  public *;
}

# for DexGuard only
#-keepresourcexmlelements manifest/application/meta-data@value=GlideModule

# eventbus避免混淆 ----
-keepattributes *Annotation*
-keepclassmembers class ** {
    @org.greenrobot.eventbus.Subscribe <methods>;
}
-keep enum org.greenrobot.eventbus.ThreadMode { *; }

# Only required if you use AsyncExecutor
-keepclassmembers class * extends org.greenrobot.eventbus.util.ThrowableFailureEvent {
    <init>(java.lang.Throwable);
}

# greendao ----
-keepclassmembers class * extends org.greenrobot.greendao.AbstractDao {
public static java.lang.String TABLENAME;
}
-keep class **$Properties

# If you do not use SQLCipher:
-dontwarn org.greenrobot.greendao.database.**
# If you do not use Rx:
-dontwarn rx.**

# retrofit网络请求 ----
-dontwarn okio.**
-dontwarn javax.annotation.**
-dontwarn retrofit2.converter.gson.**
-dontwarn retrofit2.converter.scalars.**
-dontwarn retrofit2.adapter.rxjava2.**
-keep public class retrofit2.**{*;}

# 项目中报错所以要忽略掉
-dontwarn retrofit2.Platform$Java8

# OKHttp3拦截器
-dontwarn okhttp3.logging.**

# easypermissions ----
-keepclassmembers class * {
    @pub.devrel.easypermissions.AfterPermissionGranted <methods>;
}

# PermissionsDispatcher ----
-dontwarn permissions.dispatcher.**
-keep public class permissions.dispatcher.**{*;}

# Gson混淆脚本 ----
-keep class com.google.gson.stream.**{*;}


# Bugly ----
-dontwarn com.tencent.bugly.**
-dontwarn com.tencent.tinker.**
-keep public class com.tencent.bugly.**{*;}
-keep public class com.tencent.tinker.**{*;}

# Loggger ----
-dontwarn com.orhanobut.logger.**
-keep public class com.orhanobut.logger.**{*;}

# reactivex.rxjava2:rxandroid ----
-dontwarn io.reactivex.android.**
-dontwarn io.reactivex.**
-keep public class io.reactivex.android.**{*;}
-keep public class io.reactivex.**{*;}

# rxjava ----
-dontwarn io.reactivex.**
-keep public class io.reactivex.**{*;}

# rxlifecycle2 ----
-dontwarn com.trello.rxlifecycle2.**
-dontwarn com.trello.rxlifecycle2.android.**
-dontwarn com.trello.rxlifecycle2.components.**
-keep public class com.trello.rxlifecycle2.**{*;}
-keep public class com.trello.rxlifecycle2.android.**{*;}
-keep public class com.trello.rxlifecycle2.components.**{*;}

# facebook.stetho 查看数据库 拦截网络请求 ----
-dontwarn com.facebook.stetho.**
-keep public class com.facebook.stetho.**{*;}

# 格式化拼音 ----
-dontwarn net.sourceforge.pinyin4j.**
-dontwarn com.hp.hpl.sparta.**
-keep public class net.sourceforge.pinyin4j.**{*;}
-keep public class com.hp.hpl.sparta.**{*;}

# picasso
-dontwarn com.squareup.picasso.**

# matisse 第三方jar包屏蔽
-dontwarn com.zhihu.matisse.**
-keep class com.zhihu.matisse.** {*;}

# sqlcipher加密数据库 keep 不加public
-dontwarn net.sqlcipher.**
-keep class net.sqlcipher.** {*;}
-keep class net.sqlcipher.database.** {*;}

# webRtc混淆
-dontwarn org.webrtc.**
-keep class org.webrtc.** {*;}

# 状态栏控制
-keep class android.support.v8.renderscript.** { *; }


# UPPayAssistEx.jar
-dontwarn com.unionpay.**
-keep class com.unionpay.** {*;}

# UPPayPluginExPro.jar
-dontwarn cn.gov.pbc.tsm.client.mobile.android.bank.service.**
-keep class cn.gov.pbc.tsm.client.mobile.android.bank.service.** {*;}
-dontwarn com.**
-keep class com.** {*;}

# XXPermissions混淆
-dontwarn com.hjq.permissions.**

# dagger2
-dontwarn dagger.**

# bcprov-jdk15on-1.56.jar ----
-dontwarn org.bouncycastle.**
-keep public class org.bouncycastle.**{*;}


#------------------------------------------- openfire 即时通讯 start--------------------------------------

# jxmpp-core-0.4.1.jar ----
-dontwarn org.jxmpp.**
-keep public class org.jxmpp.** {*;}

# jxmpp-util-cache-0.4.1.jar ----
-dontwarn org.jxmpp.util.cache.**
-keep public class org.jxmpp.util.cache.** {*;}

# smack-android-4.1.3.jar ----
-dontwarn org.jivesoftware.**
-keep public class org.jivesoftware.** {*;}

# smack-android-extensions-4.1.3.jar ----
-dontwarn org.jivesoftware.smackx.ping.android.**
-keep public class org.jivesoftware.smackx.ping.android.** {*;}

# smack-core-4.1.3.jar ----
-dontwarn org.jivesoftware.smack.**
-keep public class org.jivesoftware.smack.** {*;}

# smack-extensions-4.1.3.jar ----
-dontwarn org.jivesoftware.**
-keep public class org.jivesoftware.** {*;}
-dontwarn org.jivesoftware.smack.extensions.**
-keep public class org.jivesoftware.smack.extensions.** {*;}

# smack-im-4.1.3.jar ----
-dontwarn org.jivesoftware.smack.**
-keep public class org.jivesoftware.smack.** {*;}
-dontwarn org.jivesoftware.smack.im.**
-keep public class org.jivesoftware.smack.im.** {*;}

# smack-tcp-4.1.3.jar ----
-dontwarn org.jivesoftware.smack.tcp.**
-keep public class org.jivesoftware.smack.tcp.** {*;}


#------------------------------------------- openfire 即时通讯 end-------------------------------

#---------------------------------------------- 第三方推送 start---------------------------------

# 友盟统计
-keep class com.umeng.analytics.** {*;}
-keepclassmembers class * {
   public <init> (org.json.JSONObject);
}
-keep public class com.haxi.mh.R$*{
    public static final int *;
}

# 友盟push
-dontwarn com.taobao.**
-dontwarn anet.channel.**
-dontwarn anetwork.channel.**
-dontwarn org.android.**
-dontwarn org.apache.thrift.**
-dontwarn com.xiaomi.**
-dontwarn com.huawei.**

-keepattributes *Annotation*

-keep class com.taobao.** {*;}
-keep class org.android.** {*;}
-keep class anet.channel.** {*;}
-keep class com.umeng.** {*;}
-keep class com.xiaomi.** {*;}
-keep class com.huawei.** {*;}
-keep class org.apache.thrift.** {*;}

-keep class com.alibaba.sdk.android.**{*;}
-keep class com.ut.**{*;}
-keep class com.ta.**{*;}

-keep public class **.R$*{
   public static final int *;
}

# 小米推送
#这里com.xiaomi.mipushdemo.DemoMessageRreceiver改成app中定义的完整类名
-keep class com.hr.deanoffice.service.MIMessageReceiver {*;}
#可以防止一个误报的 warning 导致无法成功编译，如果编译使用的 Android 版本是 23。
-dontwarn com.xiaomi.push.**

# 华为推送
-ignorewarning
-keepattributes *Annotation*
-keepattributes Exceptions
-keepattributes InnerClasses
-keepattributes Signature
-keepattributes SourceFile,LineNumberTable
-keep class com.hianalytics.android.**{*;}
-keep class com.huawei.updatesdk.**{*;}
-keep class com.huawei.hms.**{*;}

-keep class com.huawei.gamebox.plugin.gameservice.**{*;}

-keep public class com.huawei.android.hms.agent.** extends android.app.Activity { public *; protected *; }
-keep interface com.huawei.android.hms.agent.common.INoProguard {*;}
-keep class * extends com.huawei.android.hms.agent.common.INoProguard {*;}

#（可选）避免Log打印输出
-assumenosideeffects class android.util.Log {
   public static *** v(...);
   public static *** d(...);
   public static *** i(...);
   public static *** w(...);
 }

 # 魅族推送
 -keep class com.meizu.cloud.pushsdk.** { *; }
 -dontwarn  com.meizu.cloud.pushsdk.**

 -keep class com.meizu.nebula.** { *; }
 -dontwarn com.meizu.nebula.**

 -keep class com.meizu.push.** { *; }
 -dontwarn com.meizu.push.**

#---------------------------------------------- 第三方推送 end--------------------------------

#---------------------------------------------- 支付 start-----------------------------------

# aliPay 支付宝支付
-keep class com.alipay.android.app.IAlixPay{*;}
-keep class com.alipay.android.app.IAlixPay$Stub{*;}
-keep class com.alipay.android.app.IRemoteServiceCallback{*;}
-keep class com.alipay.android.app.IRemoteServiceCallback$Stub{*;}
-keep class com.alipay.sdk.app.PayTask{ public *;}
-keep class com.alipay.sdk.app.AuthTask{ public *;}
-keep class com.alipay.sdk.app.H5PayCallback {
    <fields>;
    <methods>;
}
-keep class com.alipay.android.phone.mrpc.core.** { *; }
-keep class com.alipay.apmobilesecuritysdk.** { *; }
-keep class com.alipay.mobile.framework.service.annotation.** { *; }
-keep class com.alipay.mobilesecuritysdk.face.** { *; }
-keep class com.alipay.tscenter.biz.rpc.** { *; }
-keep class org.json.alipay.** { *; }
-keep class com.alipay.tscenter.** { *; }
-keep class com.ta.utdid2.** { *;}
-keep class com.ut.device.** { *;}

-dontwarn com.alipay.**
-dontwarn com.ta.utdid2.**
-dontwarn com.ut.device.**
-dontwarn org.json.alipay.**

# 微信支付
-keep class com.tencent.mm.opensdk.** {*;}
-keep class com.tencent.wxop.** {*;}
-keep class com.tencent.mm.sdk.** {*;}
#---------------------------------------------- 支付 end-------------------------------------





