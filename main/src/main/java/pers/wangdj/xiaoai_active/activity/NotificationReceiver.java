package pers.wangdj.xiaoai_active.activity;

import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;

/**
 * 项目名：  Active
 * 包名：    pers.wangdj.xiaoai_active.activity
 * 文件名：  NotificationReceiver
 * 创建者：  wangdja
 * 创建时间：2018/5/1  8:08
 * 描述：    通知点击事件 接收器
 */
public class NotificationReceiver  extends BroadcastReceiver {


    public static final String TYPE = "type"; //这个type是为了Notification更新信息的，这个不明白的朋友可以去搜搜，很多
    private LocalBroadcastManager localBroadcastManager;//本地广播。想不到别的好办法了

    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        int type = intent.getIntExtra(TYPE, -1);

        if (type != -1) {
            NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
            notificationManager.cancel(type);
        }

        if (action.equals("notification_clicked")) {
            //处理点击事件
//            Toast.makeText(context, "点击通知", Toast.LENGTH_SHORT).show();
            localBroadcastManager = localBroadcastManager.getInstance(context);//获取实例
            Intent intent1=new Intent("notification_clicked_local");
            localBroadcastManager.sendBroadcast(intent1);//发送本地广播
        }

        if (action.equals("notification_cancelled")) {
            //处理滑动清除和点击删除事件
//            Toast.makeText(context, "取消通知", Toast.LENGTH_SHORT).show();
        }
    }
}
