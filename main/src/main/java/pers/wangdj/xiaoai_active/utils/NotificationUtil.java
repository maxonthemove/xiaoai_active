package pers.wangdj.xiaoai_active.utils;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.app.NotificationCompat;

import pers.wangdj.xiaoai_active.R;
import pers.wangdj.xiaoai_active.activity.NotificationReceiver;

/**
 * 项目名：  Active
 * 包名：    pers.wangdj.xiaoai_active.utils
 * 文件名：  NotificationUtil
 * 创建者：  wangdja
 * 创建时间：2018/4/30  16:42
 * 描述：    通知 工具类
 */
public class NotificationUtil extends ContextWrapper {
    private NotificationManager manager;
    public static final String id = "xiaoai_active";
    public static final String name = "active_status";
    private Context mContext;
    private final int NotificationId = 998;

    public NotificationUtil(Context context) {
        super(context);
        this.mContext = context;
    }

    private NotificationManager getManager() {
        if (manager == null) {
            manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        }
        return manager;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public Notification.Builder getChannelNotification(String title, String content) {

        Intent intentClick = new Intent(this, NotificationReceiver.class);
        intentClick.setAction("notification_clicked");
        intentClick.putExtra(NotificationReceiver.TYPE, -1);
        PendingIntent pendingIntentClick = PendingIntent.getBroadcast(mContext, 0, intentClick, PendingIntent.FLAG_ONE_SHOT);

        Intent intentCancel = new Intent(this, NotificationReceiver.class);
        intentCancel.setAction("notification_cancelled");
        intentCancel.putExtra(NotificationReceiver.TYPE, -1);
        PendingIntent pendingIntentCancel = PendingIntent.getBroadcast(mContext, 0, intentCancel, PendingIntent.FLAG_ONE_SHOT);

        return new Notification.Builder(getApplicationContext(), id)
                .setContentTitle(title)
                .setContentText(content)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setLargeIcon(BitmapFactory.decodeResource(getResources(),R.mipmap.ic_launcher))
                .setAutoCancel(false)//设置这个标志当用户单击面板就可以让通知将自动取消
                .setContentIntent(pendingIntentClick)
                .setDeleteIntent(pendingIntentCancel)
                .setOngoing(false)//ture，设置他为一个正在进行的通知。他们通常是用来表示一个后台任务,用户积极参与(如播放音乐)或以某种方式正在等待,因此占用设备(如一个文件下载,同步操作,主动网络连接)
                .setDefaults(Notification.DEFAULT_VIBRATE)//向通知添加声音、闪灯和振动效果的最简单、最一致的方式是使用当前的用户默认设置，使用defaults属性，可以组合
                //Notification.DEFAULT_ALL  Notification.DEFAULT_SOUND 添加声音 // requires VIBRATE permission
                ;
    }

    public NotificationCompat.Builder getNotification_25(String title, String content) {
        Intent intentClick = new Intent(this, NotificationReceiver.class);
        intentClick.setAction("notification_clicked");
        intentClick.putExtra(NotificationReceiver.TYPE, -1);
        PendingIntent pendingIntentClick = PendingIntent.getBroadcast(mContext, 0, intentClick, PendingIntent.FLAG_ONE_SHOT);

        Intent intentCancel = new Intent(this, NotificationReceiver.class);
        intentCancel.setAction("notification_cancelled");
        intentCancel.putExtra(NotificationReceiver.TYPE, -1);
        PendingIntent pendingIntentCancel = PendingIntent.getBroadcast(mContext, 0, intentCancel, PendingIntent.FLAG_ONE_SHOT);

        return new NotificationCompat.Builder(getApplicationContext())
                .setContentTitle(title)
                .setContentText(content)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setLargeIcon(BitmapFactory.decodeResource(getResources(),R.mipmap.ic_launcher))
                .setAutoCancel(false)
                .setContentIntent(pendingIntentClick)
                .setDeleteIntent(pendingIntentCancel)
                .setOngoing(false)
                .setDefaults(Notification.DEFAULT_VIBRATE);
    }

    public void sendNotification(String title, String content) {
        if (Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(id, name, NotificationManager.IMPORTANCE_HIGH);
            getManager().createNotificationChannel(channel);
            Notification notification = getChannelNotification
                    (title, content).build();
            notification.flags = Notification.FLAG_NO_CLEAR;  //只有全部清除时，Notification才会清除
            getManager().notify(NotificationId, notification);
        } else {
            Notification notification = getNotification_25(title, content).build();
            notification.flags = Notification.FLAG_NO_CLEAR;  //只有全部清除时，Notification才会清除
            getManager().notify(NotificationId, notification);
        }
    }

}
