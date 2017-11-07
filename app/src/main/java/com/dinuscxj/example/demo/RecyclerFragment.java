package com.dinuscxj.example.demo;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dinuscxj.example.R;
import com.dinuscxj.refresh.RecyclerRefreshLayout;
import com.dinuscxj.refresh.refresh_helper.adapter.HeaderViewRecyclerAdapter;
import com.dinuscxj.refresh.refresh_helper.adapter.RecyclerListAdapter;
import com.dinuscxj.refresh.refresh_helper.ihelper.IRefresher;
import com.dinuscxj.refresh.refresh_helper.ihelper.RefreshHelper;

public abstract class RecyclerFragment extends Fragment {

    RefreshHelper mRefreshHelper;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.base_refresh_recycler_list_layout, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mRefreshHelper = new RefreshHelper(createRefresher(), view);
        mRefreshHelper.init();
        mRefreshHelper.refresh();
    }

    public void refresh(){
        mRefreshHelper.refresh();
    }

    protected abstract IRefresher createRefresher();

    @Override
    public void onDestroyView() {
        mRefreshHelper.onDestory();
        super.onDestroyView();
    }

    public HeaderViewRecyclerAdapter getHeaderAdapter() {
        return mRefreshHelper.getHeaderAdapter();
    }

    public RecyclerListAdapter getOriginAdapter() {
        return mRefreshHelper.getOriginAdapter();
    }

    public RecyclerRefreshLayout getRecyclerRefreshLayout() {
        return mRefreshHelper.getRecyclerRefreshLayout();
    }

    public RecyclerView getRecyclerView() {
        return mRefreshHelper.getRecyclerView();
    }


}
