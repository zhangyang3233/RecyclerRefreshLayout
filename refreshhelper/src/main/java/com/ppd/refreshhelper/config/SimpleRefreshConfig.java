package com.ppd.refreshhelper.config;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.animation.Interpolator;
import android.widget.ImageView;

import com.dinuscxj.refresh.IDragDistanceConverter;
import com.dinuscxj.refresh.RecyclerRefreshLayout;
import com.ppd.refreshhelper.R;
import com.ppd.refreshhelper.util.DensityUtil;


/**
 * Created by zhangyang131 on 2017/11/7.
 */

public class SimpleRefreshConfig extends BaseRefreshConfig {
    protected RecyclerRefreshLayout.RefreshStyle mStyle = RecyclerRefreshLayout.RefreshStyle.NORMAL;
    protected Float mRefreshTargetOffset;
    protected Float mRefreshInitialOffset;
    protected Integer mAnimateToStartDuration;
    protected Integer mAnimateToRefreshDuration;
    protected Interpolator mAnimateToStartInterpolator;
    protected Interpolator mAnimateToRefreshInterpolator;
    protected BaseRefreshConfig.RefreshViewInfo mRefreshViewInfo;
    protected IDragDistanceConverter mIDragDistanceConverter;
    protected Boolean mNestedScrollingEnabled;

    private SimpleRefreshConfig(RecyclerRefreshLayout.RefreshStyle style) {
        this.mStyle = style;
    }

    public static SimpleRefreshConfig newInstance(RecyclerRefreshLayout.RefreshStyle style) {
        return new SimpleRefreshConfig(style);
    }

    @Override
    public RecyclerRefreshLayout.RefreshStyle getRefreshStyle() {
        return mStyle;
    }

    @Override
    public Float getRefreshTargetOffset() {
        return mRefreshTargetOffset;
    }

    @Override
    public Float getRefreshInitialOffset() {
        return mRefreshInitialOffset;
    }

    @Override
    public Integer getAnimateToStartDuration() {
        return mAnimateToStartDuration;
    }

    @Override
    public Integer getAnimateToRefreshDuration() {
        return mAnimateToRefreshDuration;
    }

    @Override
    public Interpolator getAnimateToStartInterpolator() {
        return mAnimateToStartInterpolator;
    }

    @Override
    public Interpolator getAnimateToRefreshInterpolator() {
        return mAnimateToRefreshInterpolator;
    }

    @Override
    RefreshViewInfo getRefreshViewInfo() {
        return mRefreshViewInfo;
    }

    @Override
    public IDragDistanceConverter getDragDistanceConverter() {
        return mIDragDistanceConverter;
    }

    @Override
    public Boolean getNestedScrollingEnabled() {
        return mNestedScrollingEnabled;
    }

    @Override
    public LoadMoreViewInfo getLoadMoreViewInfo(Context context) {
        ImageView mLoadingView = new ImageView(context);
        mLoadingView.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
        mLoadingView.setImageResource(R.drawable.spinner);
        mLoadingView.setPadding(0, (int) DensityUtil.dip2px(context, 10),
                0, (int) DensityUtil.dip2px(context, 10));
        RecyclerView.LayoutParams params = new RecyclerView.LayoutParams(RecyclerRefreshLayout.LayoutParams
                .MATCH_PARENT,
                (int) DensityUtil.dip2px(context, 40));
        return new LoadMoreViewInfo(mLoadingView, params);
    }


    public SimpleRefreshConfig setStyle(RecyclerRefreshLayout.RefreshStyle style) {
        this.mStyle = style;
        return this;
    }

    public SimpleRefreshConfig setRefreshTargetOffset(Float refreshTargetOffset) {
        this.mRefreshTargetOffset = refreshTargetOffset;
        return this;
    }

    public SimpleRefreshConfig setRefreshInitialOffset(Float refreshInitialOffset) {
        this.mRefreshInitialOffset = refreshInitialOffset;
        return this;
    }

    public SimpleRefreshConfig setAnimateToStartDuration(Integer animateToStartDuration) {
        this.mAnimateToStartDuration = animateToStartDuration;
        return this;
    }

    public SimpleRefreshConfig setAnimateToRefreshDuration(Integer animateToRefreshDuration) {
        this.mAnimateToRefreshDuration = animateToRefreshDuration;
        return this;
    }

    public SimpleRefreshConfig setAnimateToStartInterpolator(Interpolator animateToStartInterpolator) {
        this.mAnimateToStartInterpolator = animateToStartInterpolator;
        return this;
    }

    public SimpleRefreshConfig setAnimateToRefreshInterpolator(Interpolator animateToRefreshInterpolator) {
        this.mAnimateToRefreshInterpolator = animateToRefreshInterpolator;
        return this;
    }

    public SimpleRefreshConfig setRefreshViewInfo(RefreshViewInfo refreshViewInfo) {
        this.mRefreshViewInfo = refreshViewInfo;
        return this;
    }

    public SimpleRefreshConfig setIDragDistanceConverter(IDragDistanceConverter dragDistanceConverter) {
        this.mIDragDistanceConverter = dragDistanceConverter;
        return this;
    }

    public SimpleRefreshConfig setNestedScrollingEnabled(Boolean nestedScrollingEnabled) {
        this.mNestedScrollingEnabled = nestedScrollingEnabled;
        return this;
    }

}
