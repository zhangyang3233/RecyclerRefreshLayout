package com.dinuscxj.refresh.refresh_helper.ihelper;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.dinuscxj.refresh.refresh_helper.adapter.RecyclerListAdapter;
import com.dinuscxj.refresh.refresh_helper.tips.TipsHelper;

/**
 * Created by zhangyang131 on 2017/11/7.
 */

public interface IRefresher {
    void initRecylerView(View parent);

    TipsHelper createTipsHelper();

    void onRefresh();

    void onLoadMore(int page);

    boolean hasMore();

    @NonNull
    RecyclerListAdapter createAdapter();

    RecyclerView.LayoutManager onCreateLayoutManager();

    boolean allowPullToRefresh();


}
