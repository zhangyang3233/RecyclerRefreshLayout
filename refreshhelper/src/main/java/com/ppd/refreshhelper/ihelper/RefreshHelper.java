package com.ppd.refreshhelper.ihelper;

import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.dinuscxj.refresh.RecyclerRefreshLayout;
import com.ppd.refreshhelper.R;
import com.ppd.refreshhelper.config.BaseRefreshConfig;
import com.ppd.refreshhelper.tips.TipsHelper;
import com.ppd.refreshhelper.wrap.PPDRecyclerView;

/**
 * 使用方法，
 * 1.布局请使用 RecyclerRefreshLayout + PPDRecyclerView, id需为： refresh_layout 和 recycler_view
 * 2.在 Activity 或 Fragment 的 OnCreate方法中实例化即可
 *
 * @see PPDRecyclerView
 * @see RecyclerRefreshLayout
 * Created by zhangyang131 on 2017/11/7.
 */
public class RefreshHelper {
    public static final int INIT_PAGE_START = 1;
    IRefresher mIRefresher;
    View mParent;

    int mPage = INIT_PAGE_START;
    private boolean mIsLoading;
    private PPDRecyclerView mRecyclerView;
    private RecyclerRefreshLayout mRecyclerRefreshLayout;

    private TipsHelper mTipsHelper;
    private RecyclerView.Adapter mAdapter;

    private RefreshEventDetector mRefreshEventDetector;
    private final AutoLoadEventDetector mAutoLoadEventDetector;
    private View mLoadingMoreView;

    public RefreshHelper(IRefresher refresher, View parent) {
        mIRefresher = refresher;
        mParent = parent;
        mRefreshEventDetector = new RefreshEventDetector();
        mAutoLoadEventDetector = new AutoLoadEventDetector();
        init();
    }

    public void init() {
        // init recyclerView and adapter
        mRecyclerView = (PPDRecyclerView) mParent.findViewById(R.id.recycler_view);
        mRecyclerView.addOnScrollListener(mAutoLoadEventDetector);
        mAdapter = mIRefresher.createAdapter();
        RecyclerView.LayoutManager layoutManager = mIRefresher.onCreateLayoutManager();
        if (layoutManager != null) {
            mRecyclerView.setLayoutManager(layoutManager);
        }
        mRecyclerView.setAdapter(mAdapter);

        // init RecyclerRefreshLayout
        mRecyclerRefreshLayout = (RecyclerRefreshLayout) mParent.findViewById(R.id.refresh_layout);
        BaseRefreshConfig.set(mRecyclerRefreshLayout, mIRefresher.getRefreshConfig());
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

    private TipsHelper getTipsHelper() {
        if (mTipsHelper == null) {
            initTipsHelper();
        }
        return mTipsHelper;
    }

    public void onDestroy() {
        mRecyclerView.removeOnScrollListener(mAutoLoadEventDetector);
    }

    public RecyclerView.Adapter getAdapter() {
        return mAdapter;
    }

    public RecyclerRefreshLayout getRecyclerRefreshLayout() {
        return mRecyclerRefreshLayout;
    }

    public RecyclerView getRecyclerView() {
        return mRecyclerView;
    }

    public void requestLoad() {
        if (isFirstPage()) {
            getTipsHelper().showLoading(true);
        } else {
            mRecyclerRefreshLayout.setRefreshing(true);
        }
        onRefresh();
    }


    public boolean isFirstPage() {
        return mAdapter.getItemCount() <= 0;
    }

    private void onRefresh() {
        if (!mIsLoading) {
            mIsLoading = true;
            mPage = INIT_PAGE_START;
            mIRefresher.onRefresh();
        }
    }

    private void onLoadMore() {
        if (mIRefresher.hasMore() && !mIsLoading) {
            mIsLoading = true;
            ((ILoadMoreStatus) mLoadingMoreView).onNormal();
            mIRefresher.onLoadMore(mPage);
        }
    }

    public void requestFailure() {
        refreshStop();
        if (mPage == INIT_PAGE_START) {
            getTipsHelper().showError(isFirstPage(), new Exception("net error"));
        } else {
            ((ILoadMoreStatus) mLoadingMoreView).onError();
        }
    }

    private void refreshStop() {
        mIsLoading = false;
        if (mRecyclerRefreshLayout != null) {
            mRecyclerRefreshLayout.setRefreshing(false);
        }
        getTipsHelper().hideError();
        getTipsHelper().hideEmpty();
        getTipsHelper().hideLoading();
    }

    public void requestComplete() {
        mPage++;
        if (mLoadingMoreView != null) {
            ((ILoadMoreStatus) mLoadingMoreView).onNormal();
        }
        refreshStop();
        if (mAdapter.getItemCount() == 0) {
            getTipsHelper().showEmpty();
        } else if (mIRefresher.hasMore()) {
            showHasMore();
        } else {
            hideHasMore();
        }
    }


    public class AutoLoadEventDetector extends RecyclerView.OnScrollListener {

        @Override
        public void onScrolled(RecyclerView view, int dx, int dy) {
            RecyclerView.LayoutManager manager = view.getLayoutManager();
            if (manager.getChildCount() - mRecyclerView.getFootersCount() - mRecyclerView
                    .getHeadersCount() > 0) {
                int count = manager.getItemCount();
                int last = ((RecyclerView.LayoutParams) manager
                        .getChildAt(manager.getChildCount() - 1).getLayoutParams())
                        .getViewAdapterPosition();

                if (last == count - 1 && !mIsLoading) {
                    onLoadMore();
                }
            }
        }
    }

    public class RefreshEventDetector implements RecyclerRefreshLayout.OnRefreshListener {

        @Override
        public void onRefresh() {
            mIsLoading = true;
            mPage = INIT_PAGE_START;
            mIRefresher.onRefresh();
        }
    }

    void showHasMore() {
        if (!mRecyclerView.containsFooterView(getLoadingMoreView())) {
            if (mLoadingMoreView instanceof ImageView) {
                Drawable drawable = ((ImageView) mLoadingMoreView).getDrawable();
                if (drawable instanceof AnimationDrawable) {
                    ((AnimationDrawable) drawable).start();
                }
            }
            mRecyclerView.addFooterView(mLoadingMoreView);
        }
    }

    void hideHasMore() {
        mRecyclerView.removeFooterView(mLoadingMoreView);
    }


    View getLoadingMoreView() {
        if (mLoadingMoreView == null) {
            mLoadingMoreView = mIRefresher.getRefreshConfig().getLoadMoreViewInfo(mParent
                    .getContext())
                    .mLoadMoreView;
            ((ILoadMoreStatus) mLoadingMoreView).setOnFailedOnclick(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onLoadMore();
                }
            });
        }
        return mLoadingMoreView;
    }

}
