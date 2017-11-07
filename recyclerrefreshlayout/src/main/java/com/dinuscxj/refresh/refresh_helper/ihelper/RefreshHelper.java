package com.dinuscxj.refresh.refresh_helper.ihelper;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.dinuscxj.recyclerrefreshlayout.R;
import com.dinuscxj.refresh.RecyclerRefreshLayout;
import com.dinuscxj.refresh.refresh_helper.adapter.HeaderViewRecyclerAdapter;
import com.dinuscxj.refresh.refresh_helper.adapter.RecyclerListAdapter;
import com.dinuscxj.refresh.refresh_helper.tips.TipsHelper;

/**
 * Created by zhangyang131 on 2017/11/7.
 */

public class RefreshHelper {
    IRefresher mIRefresher;
    View mParent;

    int mPage;
    private boolean mIsLoading;
    private RecyclerView mRecyclerView;
    private RecyclerRefreshLayout mRecyclerRefreshLayout;

    private TipsHelper mTipsHelper;
    private HeaderViewRecyclerAdapter mHeaderAdapter;
    private RecyclerListAdapter mOriginAdapter;

    private RefreshEventDetector mRefreshEventDetector;
    private final AutoLoadEventDetector mAutoLoadEventDetector;


    public RefreshHelper(IRefresher refresher, View parent) {
        mIRefresher = refresher;
        mParent = parent;
        mRefreshEventDetector = new RefreshEventDetector(mIRefresher);
        mAutoLoadEventDetector = new AutoLoadEventDetector();
        init();
    }

    public void init() {
        // init recyclerView and adapter
        mRecyclerView = (RecyclerView) mParent.findViewById(R.id.recycler_view);
        mRecyclerView.addOnScrollListener(mAutoLoadEventDetector);
        mOriginAdapter = mIRefresher.createAdapter();
        mHeaderAdapter = new HeaderViewRecyclerAdapter(mOriginAdapter);
        RecyclerView.LayoutManager layoutManager = mIRefresher.onCreateLayoutManager();
        if (layoutManager != null) {
            mRecyclerView.setLayoutManager(layoutManager);
        }
        mRecyclerView.setAdapter(mHeaderAdapter);
        mHeaderAdapter.adjustSpanSize(mRecyclerView);

        // init RecyclerRefreshLayout
        mRecyclerRefreshLayout = (RecyclerRefreshLayout) mParent.findViewById(R.id.refresh_layout);
        if (mRecyclerRefreshLayout == null) {
            return;
        }

        if (mIRefresher.allowPullToRefresh()) {
            mRecyclerRefreshLayout.setNestedScrollingEnabled(true);
            mRecyclerRefreshLayout.setOnRefreshListener(mRefreshEventDetector);
        } else {
            mRecyclerRefreshLayout.setEnabled(false);
        }

    }

    private void initTipsHelper() {
        mTipsHelper = mIRefresher.createTipsHelper();
    }

    private TipsHelper getTipsHelper(){
        if(mTipsHelper == null){
            initTipsHelper();
        }
        return mTipsHelper;
    }

    public void onDestory(){
        mRecyclerView.removeOnScrollListener(mAutoLoadEventDetector);
    }

    public HeaderViewRecyclerAdapter getHeaderAdapter() {
        return mHeaderAdapter;
    }

    public RecyclerListAdapter getOriginAdapter() {
        return mOriginAdapter;
    }

    public RecyclerRefreshLayout getRecyclerRefreshLayout() {
        return mRecyclerRefreshLayout;
    }

    public RecyclerView getRecyclerView() {
        return mRecyclerView;
    }

    public void refresh() {
        if (isFirstPage()) {
            getTipsHelper().showLoading(true);
        } else {
            mRecyclerRefreshLayout.setRefreshing(true);
        }
        requestRefresh();
    }


    public boolean isFirstPage() {
        return mOriginAdapter.getItemCount() <= 0;
    }

    private void requestRefresh() {
        if (!mIsLoading) {
            mIsLoading = true;
            mIRefresher.onRefresh();
        }
    }

    private void requestMore() {
        if (mIRefresher.hasMore() && !mIsLoading) {
            mIsLoading = true;
            mIRefresher.onLoadMore(mPage);
        }
    }

    public void requestFailure() {
        requestComplete();
        getTipsHelper().showError(isFirstPage(), new Exception("net error"));
    }

    public void requestComplete(){
        mIsLoading = false;
        if (mRecyclerRefreshLayout != null) {
            mRecyclerRefreshLayout.setRefreshing(false);
        }
        getTipsHelper().hideError();
        getTipsHelper().hideEmpty();
        getTipsHelper().hideLoading();
        if (mOriginAdapter.isEmpty()) {
            getTipsHelper().showEmpty();
        } else if (mIRefresher.hasMore()) {
            getTipsHelper().showHasMore();
        } else {
            getTipsHelper().hideHasMore();
        }
    }


    public class AutoLoadEventDetector extends RecyclerView.OnScrollListener {

        @Override
        public void onScrolled(RecyclerView view, int dx, int dy) {
            RecyclerView.LayoutManager manager = view.getLayoutManager();
            if (manager.getChildCount() > 0) {
                int count = manager.getItemCount();
                int last = ((RecyclerView.LayoutParams) manager
                        .getChildAt(manager.getChildCount() - 1).getLayoutParams()).getViewAdapterPosition();

                if (last == count - 1 && !mIsLoading) {
                    requestMore();
                }
            }
        }
    }

    public class RefreshEventDetector implements RecyclerRefreshLayout.OnRefreshListener {
        IRefresher refresher;

        public RefreshEventDetector(IRefresher refresher) {
            this.refresher = refresher;
        }

        @Override
        public void onRefresh() {
            refresher.onRefresh();
        }
    }
}
