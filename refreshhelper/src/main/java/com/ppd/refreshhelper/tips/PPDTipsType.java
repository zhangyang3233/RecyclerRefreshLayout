package com.ppd.refreshhelper.tips;

import android.content.Context;

import com.ppd.refreshhelper.R;


public class PPDTipsType {
     public static final PPDTipsType LOADING = new PPDTipsType(R.layout.tips_loading);
     public static final PPDTipsType LOADING_ERROR = new PPDTipsType(R.layout.tips_loading_failed);
     public static final PPDTipsType EMPTY = new PPDTipsType(R.layout.tips_empty);

     protected int mLayoutRes;

     public PPDTipsType(int layoutRes) {
          this.mLayoutRes = layoutRes;
     }

     public Tips createTips(Context context) {
          return new Tips(context, mLayoutRes);
     }

     public int getLayoutRes(){
          return mLayoutRes;
     }
}
