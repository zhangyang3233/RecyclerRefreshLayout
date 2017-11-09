package com.dinuscxj.example.ppd;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dinuscxj.example.R;
import com.dinuscxj.example.demo.ResistanceDragDistanceConvert;
import com.dinuscxj.example.model.MJRefreshView;
import com.dinuscxj.refresh.RecyclerRefreshLayout;
import com.ppd.refreshhelper.config.BaseRefreshConfig;
import com.ppd.refreshhelper.config.SimpleRefreshConfig;
import com.ppd.refreshhelper.ihelper.IRefresher;
import com.ppd.refreshhelper.ihelper.RefreshHelper;
import com.ppd.refreshhelper.tips.TipsHelper;
import com.ppd.refreshhelper.util.DensityUtil;


public abstract class BaseListFragment extends Fragment implements IRefresher {

    RefreshHelper mRefreshHelper;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle
            savedInstanceState) {
        return inflater.inflate(R.layout.base_refresh_recycler_list_layout, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mRefreshHelper = new RefreshHelper(this, view);
        onStartLoad();
    }

    public abstract void onStartLoad();

    @Override
    public void onDestroyView() {
        mRefreshHelper.onDestroy();
        super.onDestroyView();
    }


    @Override
    public TipsHelper createTipsHelper() {
        PPDTipsHelper tipsHelper = new PPDTipsHelper(mRefreshHelper.getRecyclerView());
        tipsHelper.setOnEmpty(R.layout.tips_empty, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requestLoad();
            }
        });
        tipsHelper.setOnError(R.layout.tips_loading_failed, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requestLoad();
            }
        });
        return tipsHelper;
    }

    public void requestLoad() {
        mRefreshHelper.requestLoad();
    }

    @NonNull
    @Override
    public RecyclerView.LayoutManager onCreateLayoutManager() {
        return new LinearLayoutManager(getActivity());
    }

    @Override
    public boolean allowPullToRefresh() {
        return true;
    }

    @Override
    public BaseRefreshConfig getRefreshConfig() {
        MJRefreshView refreshView = new MJRefreshView(getActivity());
        BaseRefreshConfig.RefreshViewInfo refreshViewInfo = new BaseRefreshConfig.RefreshViewInfo
                (refreshView, new ViewGroup.LayoutParams(refreshView.getRefreshTargetOffset(),
                        refreshView.getRefreshTargetOffset()));
        return SimpleRefreshConfig.newInstance(RecyclerRefreshLayout.RefreshStyle.NORMAL)
                .setIDragDistanceConverter(new ResistanceDragDistanceConvert(DensityUtil
                        .getScreenHeight(getActivity()))).setRefreshViewInfo(refreshViewInfo);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mRefreshHelper.onDestroy();
    }

    public void requestComplete() {
        mRefreshHelper.requestComplete();
    }

    public void requestFailure() {
        mRefreshHelper.requestFailure();
    }

    protected RecyclerView.Adapter getAdapter() {
        return mRefreshHelper.getAdapter();
    }

    @Override
    public boolean hasMore() {
        return false;
    }
}
