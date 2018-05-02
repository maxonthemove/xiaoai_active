package pers.wangdj.xiaoai_active.activity.demo;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.media.AudioFormat;
import android.media.AudioRecord;
import android.media.MediaRecorder;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.baidu.speech.EventManager;
import com.baidu.speech.EventManagerFactory;

import com.baidu.speech.EventListener;
import com.baidu.speech.asr.SpeechConstant;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;

import pers.wangdj.xiaoai_active.R;
import pers.wangdj.xiaoai_active.activity.pub.BaseActivity;
import pers.wangdj.xiaoai_active.utils.Constants;
import pers.wangdj.xiaoai_active.utils.NotificationUtil;

/**
 * 项目名：  AndroidCommonProject
 * 包名：    pers.wangdj.xiaoai_active.activity
 * 文件名：  DemoActivity
 * 创建者：  wangdja
 * 创建时间：2018-01-17  10:18 上午
 * 描述：    演示样例
 */

public class DemoActivity extends BaseActivity implements View.OnClickListener, EventListener {


    protected Button btnDemoButton;
    protected Button btnDemoButtonForResult;
    private String ivwNetMode = "0";
    // 语音唤醒对象
    private String keep_alive = "1";
    private int curThresh = 20;
    private int num = 0;
    private NotificationUtil notificationUtil;//通知 工具类
    private boolean isActive = false;
    private LocalBroadcastManager localBroadcastManager;
    private LocalReceiver localReceiver;
    private IntentFilter intentFilter;
    private EventManager wakeup;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.demo_layout);
        initView();
        initListener();
        initData();
        initBroadcast();
        // 初始化唤醒对象
        requestPermissions();
        wakeup = EventManagerFactory.create(this, "wp");
        wakeup.registerListener(this); //  EventListener 中 onEvent方法
    }

    private void initBroadcast() {
        localBroadcastManager = LocalBroadcastManager.getInstance(this);
        intentFilter = new IntentFilter();
        intentFilter.addAction("notification_clicked_local");
        localReceiver = new LocalReceiver();
        localBroadcastManager.registerReceiver(localReceiver, intentFilter);
    }


    private void requestPermissions() {
        try {
            String[] permissions = {
                    Manifest.permission.RECORD_AUDIO,
                    Manifest.permission.ACCESS_NETWORK_STATE,
                    Manifest.permission.INTERNET,
                    Manifest.permission.READ_PHONE_STATE,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE
            };

            ArrayList<String> toApplyList = new ArrayList<String>();

            for (String perm : permissions) {
                if (PackageManager.PERMISSION_GRANTED != ContextCompat.checkSelfPermission(this, perm)) {
                    toApplyList.add(perm);
                    // 进入到这里代表没有权限.

                }
            }
            String[] tmpList = new String[toApplyList.size()];
            if (!toApplyList.isEmpty()) {
                ActivityCompat.requestPermissions(this, toApplyList.toArray(tmpList), 123);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void initView() {
        btnDemoButton = findViewById(R.id.btn_demo_button);
        btnDemoButton.setOnClickListener(DemoActivity.this);
        btnDemoButtonForResult = findViewById(R.id.btn_demo_button_for_result);
        btnDemoButtonForResult.setOnClickListener(DemoActivity.this);


    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void initData() {
        btnDemoButton.setText("开启语音唤醒");
        btnDemoButtonForResult.setText("关闭语音唤醒");
    }


    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.btn_demo_button) {

//            startActivity(actionStart("464655465555", "2641"));
            startWakeUper(true);

        } else if (view.getId() == R.id.btn_demo_button_for_result) {
//            startActivityForResult(actionStartForResult("464655465555", "2641"), Constants.requestCode350);
            shutDownWakeUper();

        }
    }

    Intent actionStart(String billno, String transtype) {
        Intent intent = new Intent(this, SecondDemoActivity.class);
        intent.putExtra("billno", billno);
        intent.putExtra("transtype", transtype);
        return intent;
    }

    Intent actionStartForResult(String billno, String transtype) {
        Intent intent = new Intent(this, SecondDemoActivity.class);
        intent.putExtra("billno", billno);
        intent.putExtra("transtype", transtype);
        return intent;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Constants.requestCode350 && resultCode == Constants.resultCode340) {
            btnDemoButton.setText(data.getExtras().getString("billtype"));
        }
    }

    public void startWakeUper(boolean sendNotification) {
        //非空判断，防止因空指针使程序崩溃
        Map<String, Object> params = new TreeMap<String, Object>();

        params.put(SpeechConstant.ACCEPT_AUDIO_VOLUME, false);
        params.put(SpeechConstant.WP_WORDS_FILE, "assets:///WakeUp.bin");
        // "assets:///WakeUp.bin" 表示WakeUp.bin文件定义在assets目录下

        String json = null; // 这里可以替换成你需要测试的json
        json = new JSONObject(params).toString();
        //启动
        wakeup.send(SpeechConstant.WAKEUP_START, json, null, 0, 0);
        isActive = true;
        if (sendNotification) {
            notificationUtil = new NotificationUtil(this);
            notificationUtil.sendNotification("语音唤醒", "语音唤醒已开启，点击关闭");
        }

    }

    private static final String TAG = "DemoActivity";

    //暂时关闭唤醒
    public void shutDownWakeUperForSomeTime() {
        wakeup.send(SpeechConstant.WAKEUP_STOP, null, null, 0, 0); //关闭
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    do {
                        Thread.sleep(3000);
                        Log.d(TAG, "run:休眠 3s ");
                    } while (!validateMicAvailability());
                    startWakeUper(false);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    public void shutDownWakeUper() {
        wakeup.send(SpeechConstant.WAKEUP_STOP, null, null, 0, 0); //关闭
        isActive = false;
        notificationUtil = new NotificationUtil(this);
        notificationUtil.sendNotification("语音唤醒", "语音唤醒已关闭，点击开启");
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        localBroadcastManager.unregisterReceiver(localReceiver);

        wakeup.send(SpeechConstant.WAKEUP_STOP, "{}", null, 0, 0);

    }


    /**
     * 判断 麦克风是否被占用
     *
     * @return
     */
    private boolean validateMicAvailability() {
        Boolean available = true;
        AudioRecord recorder =
                new AudioRecord(MediaRecorder.AudioSource.MIC, 44100,
                        AudioFormat.CHANNEL_IN_MONO,
                        AudioFormat.ENCODING_DEFAULT, 44100);
        try {
            if (recorder.getRecordingState() != AudioRecord.RECORDSTATE_STOPPED) {
                available = false;
            }
            recorder.startRecording();
            if (recorder.getRecordingState() != AudioRecord.RECORDSTATE_RECORDING) {
                recorder.stop();
                available = false;
            }
            recorder.stop();
        } catch (Exception e) {

        } finally {
            recorder.release();
            recorder = null;
        }
        Log.d("maikefeng", "麦克风可用？ : " + available);
        return available;
    }

    @Override
    public void onEvent(String name, String params, byte[] data, int offset, int length) {
        String logTxt = "name: " + name;
        if (params != null && !params.isEmpty()) {
            logTxt += " ;params :" + params;
            try {
                JSONObject jsonObject = new JSONObject(params);
                if (jsonObject.getString("errorDesc").equals("wakup success")) {
                    //唤醒 已经打开 并且说出了唤醒词 暂时关闭唤醒 等待小爱占用麦克风
                    shutDownWakeUperForSomeTime();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        } else if (data != null) {
            logTxt += " ;data length=" + data.length;
        }
        Log.d(TAG, "onEvent: " + logTxt);

    }

    class LocalReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
//            showToast("收到了通知");
            if (isActive) {
                //开启状态，关闭
                shutDownWakeUper();
            } else {
                startWakeUper(true);
            }
        }
    }

}
