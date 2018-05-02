package pers.wangdj.xiaoai_active.activity.demo;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.QuickContactBadge;
import android.widget.RatingBar;
import android.widget.TextView;

import pers.wangdj.xiaoai_active.R;
import pers.wangdj.xiaoai_active.activity.pub.BaseActivity;
import pers.wangdj.xiaoai_active.utils.Constants;

/**
 * 项目名：  AndroidCommonProject
 * 包名：    pers.wangdj.xiaoai_active.activity.demo
 * 文件名：  SecondDemoActivity
 * 创建者：  wangdja
 * 创建时间：2018-01-17  3:02 下午
 * 描述：    演示activity 2
 */

public class SecondDemoActivity extends BaseActivity implements View.OnClickListener {


    protected QuickContactBadge quickContactBadge;
    protected RatingBar ratingBar;
    protected TextView textView;
    protected TextView textView2;
    protected Button button;

    private  String  tv_text_1 ;
    private  String  tv_text_2 ;




    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.demo_second_layout);
        initView();
        initListener();
        initData();
    }

    @Override
    protected void initView() {
        quickContactBadge = findViewById(R.id.quickContactBadge);
        ratingBar = findViewById(R.id.ratingBar);
        textView =  findViewById(R.id.textView);
        textView2 =  findViewById(R.id.textView2);
        button =  findViewById(R.id.button);
        button.setOnClickListener(SecondDemoActivity.this);

    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void initData() {

        tv_text_1 = getIntent().getExtras().getString("billno","");
        tv_text_2 = getIntent().getExtras().getString("transtype","");

        textView.setText(tv_text_1);
        textView2.setText(tv_text_2);

    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.button) {
            setResult(Constants.resultCode340,actionFinish("asdfasss"));
            finish();
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        setResult(Constants.resultCode340,actionFinish("asdfasss"));
        finish();
    }

    Intent actionFinish(String billtype){
        Intent intent = new Intent();
        intent.putExtra("billtype",billtype);
        return intent;
    }
}
