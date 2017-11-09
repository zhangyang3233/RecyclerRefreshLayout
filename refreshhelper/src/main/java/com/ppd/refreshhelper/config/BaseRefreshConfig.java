package com.ppd.refreshhelper.config;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Interpolator;

import com.dinuscxj.refresh.IDragDistanceConverter;
import com.dinuscxj.refresh.RecyclerRefreshLayout;
import com.ppd.refreshhelper.ihelper.ILoadMoreStatus;

/**
 * Created by zhangyang131 on 2017/11/7.
 */

public abstract class BaseRefreshConfig {

    public static void set(RecyclerRefreshLayout layout, BaseRefreshConfig config) {
        if (config.getRefreshStyle() != null) {
            layout.setRefreshStyle(config.getRefreshStyle());
        }
        if (config.getAnimateToStartInterpolator() != null) {
            layout.setAnimateToStartInterpolator(config.getAnimateToStartInterpolator());
        }
        if (config.getAnimateToStartDuration() != null) {
            layout.setAnimateToStartDuration(config.getAnimateToStartDuration());
        }

        if (config.getAnimateToRefreshDuration() != null) {
            layout.setAnimateToRefreshDuration(config.getAnimateToRefreshDuration());
        }

        if (config.getAnimateToRefreshInterpolator() != null) {
            layout.setAnimateToRefreshInterpolator(config.getAnimateToRefreshInterpolator());
        }

        if (config.getDragDistanceConverter() != null) {
            layout.setDragDistanceConverter(config.getDragDistanceConverter());
        }

        if (config.getNestedScrollingEnabled() != null) {
            layout.setNestedScrollingEnabled(config.getNestedScrollingEnabled());
        }

        if (config.getRefreshInitialOffset() != null) {
            layout.setRefreshInitialOffset(config.getRefreshInitialOffset());
        }

        if (config.getRefreshTargetOffset() != null) {
            layout.setRefreshTargetOffset(config.getRefreshTargetOffset());
        }

        if (config.getRefreshViewInfo() != null) {
            layout.setRefreshView(config.getRefreshViewInfo().mRefreshView, config.getRefreshViewInfo().mLayoutParams);
        }
    }


    /**
     * 设置RefreshView的样式
     *
     * @return
     */
    abstract RecyclerRefreshLayout.RefreshStyle getRefreshStyle();

    /**
     * 设置触发刷新需要滑动的最小距离
     *
     * @return
     */
    abstract Float getRefreshTargetOffset();

    /**
     * 设置RefreshView相对父组件的初始位置
     *
     * @return
     */
    abstract Float getRefreshInitialOffset();

    /**
     * 设置将刷新动画从手势释放的位置（或刷新位置）移动到起始位置的动画所需要的时间
     *
     * @return
     */
    abstract Integer getAnimateToStartDuration();

    /**
     * 设置将刷新动画从手势释放的位置移动到刷新位置的动画所需要的时间
     *
     * @return
     */
    abstract Integer getAnimateToRefreshDuration();


    /**
     * 设置将刷新动画从手势释放的位置（或刷新位置）移动到起始位置的动画所需要的Interpolator
     *
     * @return
     */
    abstract Interpolator getAnimateToStartInterpolator();

    /**
     * 设置将刷新动画从手势释放的位置移动到刷新位置的动画所需要的Interpolator
     *
     * @return
     */
    abstract Interpolator getAnimateToRefreshInterpolator();


    /**
     * 自定义刷新动画
     *
     * @return
     */
    abstract RefreshViewInfo getRefreshViewInfo();

    /**
     * 定义拖动距离的转换器
     *
     * @return
     */
    abstract IDragDistanceConverter getDragDistanceConverter();

    /**
     * 是否支持NestedScrolling
     *
     * @return
     */
    abstract Boolean getNestedScrollingEnabled();

    public abstract LoadMoreViewInfo getLoadMoreViewInfo(Context context);

    public static class LoadMoreViewInfo {
        public View mLoadMoreView;

        public LoadMoreViewInfo(View loadMoreView, RecyclerView.LayoutParams mLayoutParams) {
            if(!(loadMoreView instanceof ILoadMoreStatus)){
                throw new IllegalArgumentException("loadMoreView must be implements ILoadMoreStatus");
            }
            this.mLoadMoreView = loadMoreView;
            this.mLoadMoreView.setLayoutParams(mLayoutParams);
        }
    }

    public static class RefreshViewInfo {

        public RefreshViewInfo(View mRefreshView, ViewGroup.LayoutParams mLayoutParams) {
            this.mRefreshView = mRefreshView;
            this.mLayoutParams = mLayoutParams;
        }

        public View mRefreshView;
        public ViewGroup.LayoutParams mLayoutParams;
    }
}
