package com.bw.movie.core;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.tencent.android.tpush.XGIOperateCallback;
import com.tencent.android.tpush.XGPushConfig;
import com.tencent.android.tpush.XGPushManager;
import com.tencent.bugly.crashreport.CrashReport;
import com.umeng.commonsdk.UMConfigure;

/**
 * @name: MyApplication
 * @describe: 程序入口
 * @author: 康海涛 QQ2541849981
 * @time: 2018/8/14 12:45
 * @remark:
 */
public class WDApplication extends Application {

    /** 主线程ID */
    private static int mMainThreadId = -1;
    /** 主线程ID */
    private static Thread mMainThread;
    /** 主线程Handler */
    private static Handler mMainThreadHandler;
    /** 主线程Looper */
    private static Looper mMainLooper;

    /**
     * context 全局唯一的上下文
     */
    private static Context context;

    private static SharedPreferences sharedPreferences;

    @Override
    public void onCreate() {
        super.onCreate();
        context=this;
        mMainThreadId = android.os.Process.myTid();
        mMainThread = Thread.currentThread();
        mMainThreadHandler = new Handler();
        mMainLooper = getMainLooper();
        sharedPreferences = getSharedPreferences("share.xml",MODE_PRIVATE);
        Fresco.initialize(this);
        UMConfigure.init(this,  UMConfigure.DEVICE_TYPE_PHONE, null);
        //腾讯bugly
        CrashReport.initCrashReport(getApplicationContext(), "b7b8d362e0", false);

        XGPushConfig.enableOtherPush(getApplicationContext(), true);
        XGPushConfig.setMiPushAppId(getApplicationContext(), "APPID");
        XGPushConfig.setMiPushAppKey(getApplicationContext(), "APPKEY");
        XGPushConfig.setMzPushAppId(this, "APPID");
        XGPushConfig.setMzPushAppKey(this, "APPKEY");
        XGPushManager.registerPush(this, new XGIOperateCallback() {
            @Override
            public void onSuccess(Object data, int flag) {
                //token在设备卸载重装的时候有可能会变
                Log.d("TPush", "注册成功，设备token为：" + data);
            }
            @Override
            public void onFail(Object data, int errCode, String msg) {
                Log.d("TPush", "注册失败，错误码：" + errCode + ",错误信息：" + msg);
            }
        });
    }

    public static SharedPreferences getShare(){
        return sharedPreferences;
    }

    /**
     * @author: 康海涛 QQ2541849981
     * @describe: 获取全局Application的上下文
     * @return 全局唯一的上下文
     */
    public static Context getContext() {
        return context;
    }

    /** 获取主线程ID */
    public static int getMainThreadId() {
        return mMainThreadId;
    }

    /** 获取主线程 */
    public static Thread getMainThread() {
        return mMainThread;
    }

    /** 获取主线程的handler */
    public static Handler getMainThreadHandler() {
        return mMainThreadHandler;
    }

    /** 获取主线程的looper */
    public static Looper getMainThreadLooper() {
        return mMainLooper;
    }
}