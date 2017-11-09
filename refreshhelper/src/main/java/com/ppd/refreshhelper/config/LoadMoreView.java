package com.ppd.refreshhelper.config;

import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.ppd.refreshhelper.R;
import com.ppd.refreshhelper.ihelper.ILoadMoreStatus;

/**
 * Created by zhangyang131 on 2017/11/9.
 */

public class LoadMoreView extends FrameLayout implements ILoadMoreStatus {
    TextView msg;
    ImageView loading;
    OnClickListener mOnFailedOnclick;

    public OnClickListener getOnFailedOnclick() {
        return mOnFailedOnclick;
    }

    public void setOnFailedOnclick(OnClickListener onFailedOnclick) {
        this.mOnFailedOnclick = onFailedOnclick;
        this.msg.setOnClickListener(mOnFailedOnclick);
    }

    public LoadMoreView(@NonNull Context context) {
        super(context);
    }

    public LoadMoreView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public LoadMoreView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    static LoadMoreView newInstance(Context context) {
        LoadMoreView loadMoreView = (LoadMoreView) LayoutInflater.from(context).inflate(R.layout
                .load_more_layout, null);
        return loadMoreView;
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        initView();
        initText();
        initListener();
    }

    private void initListener() {
        msg.setOnClickListener(mOnFailedOnclick);
    }

    private void initText() {
        msg.setText(R.string.load_more_failed);
    }

    private void initView() {
        loading = (ImageView) findViewById(R.id.loading);
        ((AnimationDrawable) loading.getDrawable()).start();
        msg = (TextView) findViewById(R.id.msg);
    }

    @Override
    public void onError() {
        loading.setVisibility(View.INVISIBLE);
        msg.setVisibility(View.VISIBLE);
    }

    @Override
    public void onNormal() {
        loading.setVisibility(View.VISIBLE);
        msg.setVisibility(View.INVISIBLE);
    }
}
