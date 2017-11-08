package com.ppd.refreshhelper.ihelper;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;

import com.ppd.refreshhelper.adapter.RecyclerListAdapter;
import com.ppd.refreshhelper.config.BaseRefreshConfig;
import com.ppd.refreshhelper.tips.TipsHelper;

/**
 * Created by zhangyang131 on 2017/11/7.
 */

public interface IRefresher {

    TipsHelper createTipsHelper();

    void onRefresh();

    void onLoadMore(int page);

    boolean hasMore();

    @NonNull
    RecyclerListAdapter createAdapter();

    @NonNull
    RecyclerView.LayoutManager onCreateLayoutManager();

    boolean allowPullToRefresh();

    BaseRefreshConfig getRefreshConfig();

}
