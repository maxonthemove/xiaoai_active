package pers.wangdj.xiaoai_active.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.widget.TextView;

import pers.wangdj.xiaoai_active.R;
import pers.wangdj.xiaoai_active.activity.demo.DemoActivity;
import pers.wangdj.xiaoai_active.activity.pub.BaseActivity;
import pers.wangdj.xiaoai_active.utils.Constants;
import pers.wangdj.xiaoai_active.utils.FontUtil;
import pers.wangdj.xiaoai_active.utils.ShareUtil;

/**
 * 项目名：  AndroidCommonProject
 * 包名：    pers.wangdj.xiaoai_active.activity
 * 文件名：  SplashActivity
 * 创建者：  wangdja
 * 创建时间：2018-01-22  1:23 下午
 * 描述：    闪屏页
 */

public class SplashActivity extends BaseActivity {

    protected TextView tvSplash;

    private Handler mHandler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            switch (msg.what) {
                case Constants.HANDLER_SPLASH_TIME:
                    //判断程序是否是第一次运行
                    if (FirstRun()) {
                        startActivity(new Intent(SplashActivity.this, DemoActivity.class));
                        ShareUtil.putBoolean(SplashActivity.this, Constants.SHARE_FIRST_RUN, false);
                    } else {
                        startActivity(new Intent(SplashActivity.this, DemoActivity.class));
                    }
                    finish();
                    break;
                default:
                    break;
            }
            return false;
        }
    });


    /**
     * 1.延时 2000ms
     * 2.判断程序是否第一次运行
     * 3.自定义字体
     * 4.Activity 全屏主题
     */


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_layout);
        initView();
    }

    //判断程序是否是第一次运行
    private boolean FirstRun() {
        return ShareUtil.getBoolean(this, Constants.SHARE_FIRST_RUN, true);
    }

    @Override
    protected void initView() {
        mHandler.sendEmptyMessageDelayed(Constants.HANDLER_SPLASH_TIME, 2000);
        tvSplash = (TextView) findViewById(R.id.tv_splash);

        //设置字体
        FontUtil.setFont(this, tvSplash);

    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void initData() {

    }

    @Override
    //禁用返回键
    public void onBackPressed() {
//        super.onBackPressed();
    }


}
