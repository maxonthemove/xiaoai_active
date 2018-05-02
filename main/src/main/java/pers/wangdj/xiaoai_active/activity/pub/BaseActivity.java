package pers.wangdj.xiaoai_active.activity.pub;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import pers.wangdj.xiaoai_active.utils.Constants;
import pers.wangdj.xiaoai_active.utils.LogUtil;

/**
 * 项目名：  AndroidCommonProject
 * 包名：    pers.wangdj.xiaoai_active.activity.pub
 * 文件名：  BaseActivity
 * 创建者：  wangdja
 * 创建时间：2018-01-17  10:22 上午
 * 描述：    Activity 基本类，初始化一些公共方法
 */

public abstract class BaseActivity extends AppCompatActivity {

    /**
     * 主要做的事情：
     * 1.统一的属性
     * 2.统一的接口
     * 3.统一的方法
     */

    private static final String TAG = "BaseActivity";
    private Toast mToast;



    @Override
    //activity创建调用的方法
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //此处写公共方法
        LogUtil.d(TAG,getClass().getSimpleName());//打印当前的activity的父类，以及当前的activity的子类名称
        ActivityCollector.addActivity(this);

    }

    //activity 销毁时调用的方法
    @Override
    protected void onDestroy() {
        super.onDestroy();
        ActivityCollector.removeActivity(this);
    }

    //关闭全部页面
    protected void finshAllActivity(){
        ActivityCollector.finishAll();
    }

    //初始化视图
    protected abstract void initView();
    //初始化Listener
    protected abstract void initListener();
    //初始化数据
    protected abstract void initData();

    // 初始化权限
    protected void initPermission() {

        if (isSurpass6Version()) {
            List<String> permissionList = new ArrayList<>();

            permissionList.add(Manifest.permission.READ_PHONE_STATE);  // 电话状态
            permissionList.add(Manifest.permission.WRITE_SETTINGS);  // 设置
            permissionList.add(Manifest.permission.ACCESS_WIFI_STATE);  // wifi
            permissionList.add(Manifest.permission.INTERNET);  // 网络
            permissionList.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);  // 存储
            permissionList.add(Manifest.permission.ACCESS_FINE_LOCATION);  // 地理位置
            permissionList.add(Manifest.permission.RECORD_AUDIO);  // 录音
            permissionList.add(Manifest.permission.CALL_PHONE);  // 打电话
            permissionList.add(Manifest.permission.CAMERA);  // 摄像机
            permissionList.add(Manifest.permission.SEND_SMS);  // 发信息
            permissionList.add(Manifest.permission.READ_CONTACTS);//读通讯录
//            permissionList.add(Manifest.permission.READ_EXTERNAL_STORAGE); // 读download
//            permissionList.add(Manifest.permission.WRITE_CALL_LOG);
//            permissionList.add(Manifest.permission.BODY_SENSORS); // 触感
            permissionList.add(Manifest.permission.INSTALL_PACKAGES); // install package

            // 地理位置
            //permissionList.add(Manifest.permission.ACCESS_FINE_LOCATION);
            //permissionList.add(Manifest.permission.ACCESS_COARSE_LOCATION);

            requestPermission(permissionList.toArray(new String[permissionList.size()]), Constants.REQUEST_CODE_PERMISSION);

        }
    }

    /**
     * 是否等于或者超过6.0
     *
     * @return   是否等于或者超过6.0
     */
    protected boolean isSurpass6Version() {
        //第一个23 是为了防止当前的 sdk版本低于23 ，第二个是谷歌标准写法。
        return Build.VERSION.SDK_INT >= 23||Build.VERSION.SDK_INT > Build.VERSION_CODES.M;
    }

    /**
     * 请求权限
     *
     * @param permissions 请求的权限
     * @param requestCode 请求权限的请求码
     */
    protected void requestPermission(String[] permissions, int requestCode) {
        if (checkPermissions(permissions)) {
            permissionSuccess(requestCode);
        } else {
            List<String> needPermissions = getDeniedPermissions(permissions);
            ActivityCompat.requestPermissions(this, needPermissions.toArray(new String[needPermissions.size
                    ()]), requestCode);
        }
    }

    /**
     * 检测所有的权限是否都已授权
     *
     * @param permissions   权限列表
     * @return              是否全部授权
     */
    protected boolean checkPermissions(String[] permissions) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            return true;
        }

        for (String permission : permissions) {
            if (ContextCompat.checkSelfPermission(this, permission) !=
                    PackageManager.PERMISSION_GRANTED) {
                return false;
            }
        }
        return true;
    }

    /**
     * 获取权限集中需要申请权限的列表
     *
     * @param permissions  权限列表
     * @return             需要申请的权限列表
     */
    protected List<String> getDeniedPermissions(String[] permissions) {
        List<String> needRequestPermissionList = new ArrayList<>();
        for (String permission : permissions) {
            if (ContextCompat.checkSelfPermission(this, permission) !=
                    PackageManager.PERMISSION_GRANTED ||
                    ActivityCompat.shouldShowRequestPermissionRationale(this, permission)) {
                needRequestPermissionList.add(permission);
            }
        }
        return needRequestPermissionList;
    }

    /**
     * 获取权限成功
     *
     * @param requestCode  请求code
     */
    protected void permissionSuccess(int requestCode) {
        LogUtil.v(TAG, "获取权限成功=" + requestCode);
    }

    /**
     * 消息提醒，后边的会干掉前边的
     *
     * @param obj  提示消息
     */
    protected void showToast(Object obj){
        if (mToast != null) {
            mToast.cancel();
        }
        mToast = Toast.makeText(this, obj + "", Toast.LENGTH_LONG);
        mToast.show();

    }

    /**
     * 可以在中心显示的提示内容
     *
     * @param obj          需要提示的内容
     * @param isCenterShow 是否需要在中心显示
     */
    public void showToast(Object obj, boolean isCenterShow) {

        if (isCenterShow) {
            if (mToast != null) {
                mToast.cancel();
            }
            mToast = Toast.makeText(this, obj + "", Toast.LENGTH_LONG);
            mToast.setGravity(Gravity.CENTER, 0, 0);
            mToast.show();
        } else {
            showToast(obj);
        }


    }


}
