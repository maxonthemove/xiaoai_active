package pers.wangdj.xiaoai_active.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * 项目名：  AndroidCommonProject
 * 包名：    pers.wangdj.xiaoai_active.adapter
 * 文件名：  GuideAdater
 * 创建者：  wangdja
 * 创建时间：2018-01-22  2:59 下午
 * 描述：    引导页适配器
 */

public class GuideAdater extends PagerAdapter {

    private List<View> mList = new ArrayList<>();
    private Context mContext;

    public GuideAdater(Context mContext, List<View> mList) {
        this.mList = mList;
        this.mContext = mContext;
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        container.addView(mList.get(position));
        return mList.get(position);

    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView(mList.get(position));
//        super.destroyItem(container, position, object);
    }
}
