package com.ppd.refreshhelper.ihelper;

import android.view.View;

/**
 * Created by zhangyang131 on 2017/11/9.
 */

public interface ILoadMoreStatus {
    void onError();
    void onNormal();
    void setOnFailedOnclick(View.OnClickListener onFailedOnclick);

}
