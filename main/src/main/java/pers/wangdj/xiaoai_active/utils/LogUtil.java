package pers.wangdj.xiaoai_active.utils;

import android.util.Log;

/**
 * 项目名：  AndroidCommonProject
 * 包名：    pers.wangdj.xiaoai_active.utils
 * 文件名：  LogUtil
 * 创建者：  wangdja
 * 创建时间：2018-01-17  10:36 上午
 * 描述：    日志工具类
 */

public class LogUtil {

    private static final int VERBOSE = 1;
    private static final int DEBUG = 2;
    private static final int INFO = 3;
    private static final int WARN = 4;
    private static final int ERROR = 5;
    private static final int NOTHING = 6;

    /**
     * 日志打印等级控制，使用这个值控制日志打印的级别
     * wangdj @2018年1月17日10点44分
     */

    private static int level = VERBOSE;

    public static void v(String tag, String msg) {
        if (level <= VERBOSE) {
            Log.v(tag, msg);
        }
    }

    public static void d(String tag, String msg) {
        if (level <= DEBUG) {
            Log.d(tag, msg);
        }
    }

    public static void i(String tag, String msg) {
        if (level <= INFO) {
            Log.i(tag, msg);
        }
    }

    public static void w(String tag, String msg) {
        if (level <= WARN) {
            Log.w(tag, msg);
        }
    }

    public static void e(String tag, String msg) {
        if (level <= ERROR) {
            Log.e(tag, msg);
        }
    }

}
