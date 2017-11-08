package com.ppd.refreshhelper.util;

import android.content.Context;

/**
 * Created by zhangyang131 on 2017/11/8.
 */

public class DensityUtil {

    public static float dip2px(Context context, float dpValue) {
        float scale = context.getResources().getDisplayMetrics().density;
        return dpValue * scale;
    }
}
