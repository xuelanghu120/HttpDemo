# Add project specific ProGuard rules here.
# By default, the flags in this file are appended to flags specified
# in E:\progress\Sdk/tools/proguard/proguard-android.txt
# You can edit the include path and order by changing the proguardFiles
# directive in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# Add any project specific keep options here:

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}

#避免混淆泛型 如果混淆报错建议关掉
-keepattributes Signature
# Ensure annotations are kept for runtime use.#InnerClasses,
-keepattributes *Annotation*

-ignorewarnings                # 抑制警告

-keep public class * extends android.app.Activity      # 保持哪些类不被混淆
-keep public class * extends android.app.Application   # 保持哪些类不被混淆
-keep public class * extends android.app.Service       # 保持哪些类不被混淆
-keep public class * extends android.content.BroadcastReceiver  # 保持哪些类不被混淆
-keep public class * extends android.content.ContentProvider    # 保持哪些类不被混淆
-keep public class * extends android.app.Fragment
-keep public class * extends android.preference.Preference
-keep public class * extends android.support.v4.**
-keep public class * extends android.support.annotation.**
-keep public class * extends android.support.v7.**
-keep public class * extends android.support.v13.**
-keep public class * extends android.widget.AbsListView.**


-keep class android.support.v4.** { *; }
-keep class android.support.v7.** { *; }
-keep class android.support.v13.** { *; }
-keep class android.preference.Preference {*;}


-keep class android.databinding.** {*;}
-keep class me.tatarka.bindingcollectionadapter.** {*;}
-keep class com.huxin.common.db.** {*;}
-keep class * implements me.tatarka.bindingcollectionadapter.BindingCollectionAdapter {*;}
#cookie管理的
-keep class com.huxin.common.http.cookie.** {*;}
#保持 native 方法不被混淆
-keepclasseswithmembernames class * {
    native <methods>;
}

-keepclassmembers enum * {     # 保持枚举 enum 类不被混淆
    public static **[] values();
    public static ** valueOf(java.lang.String);
}

-keep class * implements android.os.Parcelable {*;}

###排除所有注解类
-keep class * extends java.lang.annotation.Annotation { *; }
-keep interface * extends java.lang.annotation.Annotation { *; }




#用于反射与Gson的类需继承
-keep class * implements com.huxin.common.entity.IEntity {*;}

-keep class * implements com.huxin.common.http.builder.ParamEntity {*;}

#mmlib网络库
#网络解析库
-keep class * implements com.huxin.common.http.builder.URLBuilder {*;}

#友盟统计
-keepclassmembers class * {
   public <init> (org.json.JSONObject);
}
-keep public class com.huxin.xinpiao.R$*{
    public static final int *;
}

-keep public class com.ygbx.faceLibrary.R$*{
    public static final int *;
}

-keepclassmembers enum * {*;}
-keep public class com.umeng.fb.ui.ThreadView {
}

#友盟推送
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






#
##微信
#-keep class com.tencent.mm.sdk.** {*;}

#Glide
-keep public class * implements com.bumptech.glide.module.GlideModule
-keep public enum com.bumptech.glide.load.resource.bitmap.ImageHeaderParser$** {
  **[] $VALUES;
  public *;
}


#gson
# Gson specific classes
-keep class sun.misc.Unsafe {*;}
# Application classes that will be serialized/deserialized over Gson
-keep class com.google.gson.examples.android.model.** {*;}

#OkHttp3
-dontwarn com.squareup.okhttp3.**
-keep class com.squareup.okhttp3.** {*;}
-dontwarn okio.**

#RxJava RxAndroid
-dontwarn sun.misc.**
-keepclassmembers class rx.internal.util.unsafe.*ArrayQueue*Field* {
   long producerIndex;
   long consumerIndex;
}
-keepclassmembers class rx.internal.util.unsafe.BaseLinkedQueueProducerNodeRef {
    rx.internal.util.atomic.LinkedQueueNode producerNode;
}
-keepclassmembers class rx.internal.util.unsafe.BaseLinkedQueueConsumerNodeRef {
    rx.internal.util.atomic.LinkedQueueNode consumerNode;
}

#ormlite
# OrmLite uses reflection
-keep class com.j256.**
-keepclassmembers class com.j256.** {*;}
-keep enum com.j256.**
-keepclassmembers enum com.j256.** {*;}
-keep interface com.j256.**
-keepclassmembers interface com.j256.** {*;}
#新加
-keepclassmembers class com.package.bo.** { *; }
#-keepclassmembers class * {@com.j256.ormlite.field.DatabaseField.*;}
-keepclassmembers class * {
@com.j256.ormlite.field.DatabaseField *;
}

#ormlite
-keep class com.j256.ormlite.** { *; }
-keep class com.j256.ormlite.android.** { *; }
-keep class com.j256.ormlite.field.** { *; }
-keep class com.j256.ormlite.stmt.** { *; }


#注解处理器
-dontwarn com.squareup.javapoet.**
# Don't remove any methods that have the @Subscribe annotation
-keepclassmembers class ** { @de.greenrobot.event.Subscribe <methods>; }

-dontwarn com.google.**

##阿里支付
#-libraryjars libs/alipaySDK-20161129.jar

-keep class com.alipay.android.app.IAlixPay{*;}
-keep class com.alipay.android.app.IAlixPay$Stub{*;}
-keep class com.alipay.android.app.IRemoteServiceCallback{*;}
-keep class com.alipay.android.app.IRemoteServiceCallback$Stub{*;}
-keep class com.alipay.sdk.app.PayTask{ public *;}
-keep class com.alipay.sdk.app.AuthTask{ public *;}

-keep class com.alipay.android.phone.** { *; }

#百度地图
-keep class com.baidu.** { *; }
-keep class vi.com.gdi.bgl.android.**{*;}

#高德地图


#注解警告
-dontwarn javax.**
-keep class javax.** { *; }
-dontwarn com.j256.**
-keep class com.j256.** { *; }
-keep class * extends javax.annotation.processing.AbstractProcessor { *; }
-keep class org.apache.** {*;}

#腾讯bugly热修复
-dontwarn com.tencent.bugly.**
-keep public class com.tencent.bugly.**{*;}
