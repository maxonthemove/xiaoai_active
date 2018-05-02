package pers.wangdj.xiaoai_active.activity.pub;

import android.app.Activity;

import java.util.ArrayList;
import java.util.List;

/**
 * 项目名：  AndroidCommonProject
 * 包名：    pers.wangdj.xiaoai_active.activity.pub
 * 文件名：  ActivityCollector
 * 创建者：  wangdja
 * 创建时间：2018-01-17  1:36 下午
 * 描述：    Activity 管理，实现 activity之间的快速跳转
 */

class ActivityCollector {

    private static List<Activity> activities = new ArrayList<>();

    //新增activity
    static void addActivity(Activity activity) {
        activities.add(activity);
    }

    //删除activity
    static void removeActivity(Activity activity) {
        activities.remove(activity);
    }

    //关闭全部activity
    static void finishAll(){
        for(Activity activity: activities){
            if(!activity.isFinishing()){
                activity.finish();
            }
        }
        activities.clear();
    }
}
