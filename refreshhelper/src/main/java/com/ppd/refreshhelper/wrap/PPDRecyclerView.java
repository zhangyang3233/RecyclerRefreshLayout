package com.ppd.refreshhelper.wrap;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.View;

import com.ppd.refreshhelper.adapter.HeaderViewRecyclerAdapter;

public class PPDRecyclerView extends RecyclerView {
    private HeaderViewRecyclerAdapter mHeaderAdapter;
    private Adapter mOriginAdapter;

    public PPDRecyclerView(Context context) {
        super(context);
    }

    public PPDRecyclerView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public PPDRecyclerView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    public void setAdapter(Adapter adapter) {
        if(adapter instanceof HeaderViewRecyclerAdapter) {
            mHeaderAdapter = (HeaderViewRecyclerAdapter) adapter;
            mOriginAdapter = ((HeaderViewRecyclerAdapter) adapter).getAdapter();
            super.setAdapter(mHeaderAdapter);
        } else {
            mHeaderAdapter = new HeaderViewRecyclerAdapter(adapter);
            mOriginAdapter = adapter;
            mHeaderAdapter.adjustSpanSize(this);
            super.setAdapter(mHeaderAdapter);
        }
    }

    public Adapter getOriginAdapter() {
        return mOriginAdapter;
    }

    public HeaderViewRecyclerAdapter getHeaderAdapter() {
        return mHeaderAdapter;
    }

    public void addHeaderView(View view){
        mHeaderAdapter.addHeaderView(view);
    }

    public void addFooterView(View view){
        mHeaderAdapter.addFooterView(view);
    }

    public void removeHeaderView(View view){
        mHeaderAdapter.removeHeaderView(view);
    }

    public void removeFooterView(View view){
        mHeaderAdapter.removeFooterView(view);
    }

    public void removeAllHeader(){
        mHeaderAdapter.removeAllHeaderView();
    }

    public void removeAllFooter(){
        mHeaderAdapter.removeAllFooterView();
    }

    public boolean containsFooterView(View v) {
        return mHeaderAdapter.containsFooterView(v);
    }

    public boolean containsHeaderView(View v) {
        return mHeaderAdapter.containsHeaderView(v);
    }

    public void setHeaderVisibility(boolean shouldShow) {
        mHeaderAdapter.setHeaderVisibility(shouldShow);
    }

    public void setFooterVisibility(boolean shouldShow) {
        mHeaderAdapter.setFooterVisibility(shouldShow);
    }

    public int getHeadersCount() {
        return mHeaderAdapter.getHeadersCount();
    }

    public int getFootersCount() {
        return mHeaderAdapter.getFootersCount();
    }


}
