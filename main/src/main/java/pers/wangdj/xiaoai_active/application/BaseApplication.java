package pers.wangdj.xiaoai_active.application;

import android.app.Application;
import android.content.Context;
import android.support.multidex.MultiDex;

import pers.wangdj.xiaoai_active.R;

/**
 * 项目名：  AndroidCommonProject
 * 包名：    pers.wangdj.xiaoai_active.application
 * 文件名：  BaseApplication
 * 创建者：  wangdja
 * 创建时间：2018-01-17  10:58 上午
 * 描述：    application 基类
 */

public class BaseApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        //初始化Bugly
//        CrashReport.initCrashReport(getApplicationContext(), Constants.BUGLY_APP_ID, true);


    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);//对于 android 5.0 之前的版本，突破 65535(即2^16)方法限制
    }
}
