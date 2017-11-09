package com.dinuscxj.example.ppd;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.dinuscxj.example.demo.ResistanceDragDistanceConvert;
import com.dinuscxj.refresh.RecyclerRefreshLayout;
import com.ppd.refreshhelper.config.BaseRefreshConfig;
import com.ppd.refreshhelper.config.SimpleRefreshConfig;
import com.ppd.refreshhelper.ihelper.IRefresher;
import com.ppd.refreshhelper.ihelper.RefreshHelper;
import com.ppd.refreshhelper.tips.TipsHelper;
import com.ppd.refreshhelper.util.DensityUtil;

/**
 * Created by zhangyang131 on 2017/11/9.
 */

public abstract class BaseListActivity extends AppCompatActivity implements IRefresher {
    RefreshHelper mRefreshHelper;
    View mContentView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContentView = getLayoutInflater().inflate(getLayoutResId(), null);
        setContentView(mContentView);
        mRefreshHelper = new RefreshHelper(this, mContentView);
        onInflate(mContentView);
        onStartLoad();
    }

    public void requestLoad(){
        mRefreshHelper.requestRefresh();
    }

    protected abstract void onStartLoad();

    protected abstract void onInflate(View mContentView);

    @Override
    public TipsHelper createTipsHelper() {
        return new PPDTipsHelper(mRefreshHelper.getRecyclerView());
    }

    @Override
    public boolean hasMore() {
        return false;
    }

    @NonNull
    @Override
    public RecyclerView.LayoutManager onCreateLayoutManager() {
        return new LinearLayoutManager(this);
    }

    @Override
    public boolean allowPullToRefresh() {
        return true;
    }

    @Override
    public BaseRefreshConfig getRefreshConfig() {
        return SimpleRefreshConfig.newInstance(RecyclerRefreshLayout.RefreshStyle.NORMAL)
                .setIDragDistanceConverter(new ResistanceDragDistanceConvert(DensityUtil
                        .getScreenHeight(this)));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mRefreshHelper.onDestroy();
    }

    protected RecyclerView.Adapter getAdapter(){
        return mRefreshHelper.getAdapter();

    }

    public abstract int getLayoutResId();

    public void requestComplete(){
        mRefreshHelper.requestComplete();
    }

    public void requestFailure(){
        mRefreshHelper.requestFailure();
    }


}
