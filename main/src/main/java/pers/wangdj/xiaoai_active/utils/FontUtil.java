package pers.wangdj.xiaoai_active.utils;

import android.content.Context;
import android.graphics.Typeface;
import android.widget.TextView;

/**
 * 项目名：  AndroidCommonProject
 * 包名：    pers.wangdj.xiaoai_active.utils
 * 文件名：  FontUtil
 * 创建者：  wangdja
 * 创建时间：2018-01-22  2:34 下午
 * 描述：    字体工具类
 */

public class FontUtil {

    /**
     * 设置字体
     *
     * @param mContext  上下文
     * @param textView  文字控件
     */
    public static void setFont(Context mContext, TextView textView) {
        Typeface fontType = Typeface.createFromAsset(mContext.getAssets(),"fonts/FONT.TTF");
        textView.setTypeface(fontType);
    }
}
