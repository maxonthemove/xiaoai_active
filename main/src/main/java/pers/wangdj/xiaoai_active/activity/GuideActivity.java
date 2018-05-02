package pers.wangdj.xiaoai_active.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

import pers.wangdj.xiaoai_active.R;
import pers.wangdj.xiaoai_active.activity.demo.DemoActivity;
import pers.wangdj.xiaoai_active.activity.pub.BaseActivity;
import pers.wangdj.xiaoai_active.adapter.GuideAdater;
import pers.wangdj.xiaoai_active.utils.LogUtil;

/**
 * 项目名：  AndroidCommonProject
 * 包名：    pers.wangdj.xiaoai_active.activity
 * 文件名：  GuideActivity
 * 创建者：  wangdja
 * 创建时间：2018-01-22  1:21 下午
 * 描述：    引导页
 */

public class GuideActivity extends BaseActivity implements View.OnClickListener {


    private ViewPager mViewPager;

    //容器
    private List<View> mList = new ArrayList<>();
    private View view1, view2, view3;

    //小圆点
    private List<ImageView> mPointList = new ArrayList<>();
    private ImageView point1, point2, point3;

    //尾页 进入主页按钮
    private Button btnGoToMain;

    //跳过
    private ImageView ivSkip;

    private static final String TAG = "GuideActivity";


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide_layout);

        initView();
        initListener();
        initData();

    }

    @Override
    protected void initView() {
        mViewPager = (ViewPager) findViewById(R.id.mViewPager);

        view1 = View.inflate(this, R.layout.page_item_one, null);
        view2 = View.inflate(this, R.layout.page_item_two, null);
        view3 = View.inflate(this, R.layout.page_item_three, null);

        btnGoToMain = view3.findViewById(R.id.btn_go_to_main);

        mList.add(view1);
        mList.add(view2);
        mList.add(view3);

        //设置适配器
        mViewPager.setAdapter(new GuideAdater(this, mList));

        //跳过按钮
        ivSkip = (ImageView) findViewById(R.id.iv_skip);

        // 3 个小圆点
        point1 = (ImageView) findViewById(R.id.point1);
        point2 = (ImageView) findViewById(R.id.point2);
        point3 = (ImageView) findViewById(R.id.point3);

        mPointList.add(point1);
        mPointList.add(point2);
        mPointList.add(point3);

        //设置默认的图片
        setPointImg(0);

    }

    /**
     * 设置原点的图片资源，以达到 切换的效果
     * @param selectPage  当前页面的index
     */
    private void setPointImg(int selectPage) {
        for (int i = 0; i < mPointList.size(); i++) {
            if (selectPage == i) {
                mPointList.get(i).setImageResource(R.mipmap.point_on);
            } else {
                mPointList.get(i).setImageResource(R.mipmap.point_off);
            }
        }
    }

    @Override
    protected void initListener() {
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                LogUtil.d(TAG, "position:" + position);
                switch (position) {
                    case 0:
                        ivSkip.setVisibility(View.VISIBLE);
                        setPointImg(0);
                        break;
                    case 1:
                        ivSkip.setVisibility(View.VISIBLE);
                        setPointImg(1);
                        break;
                    case 2:
                        ivSkip.setVisibility(View.INVISIBLE);
                        setPointImg(2);
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        btnGoToMain.setOnClickListener(this);
        ivSkip.setOnClickListener(this);


    }

    @Override
    protected void initData() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_go_to_main:
            case R.id.iv_skip:
                startActivity(new Intent(GuideActivity.this, DemoActivity.class));
                finish();
                break;
            default:
                break;
        }
    }
}
