package com.dinuscxj.example.ppdtestdemo;

import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import com.dinuscxj.example.R;
import com.dinuscxj.example.model.OpenProjectModel;
import com.dinuscxj.example.ppd.BaseListActivity;
import com.dinuscxj.example.ppd.NetAsyncTask;
import com.ppd.refreshhelper.adapter.RecyclerListAdapter;

import java.util.ArrayList;

public class PPDMainActivity extends BaseListActivity {
    ArrayList<OpenProjectModel> mItemList;

    @Override
    protected void onStartLoad() {
        requestLoad();
    }

    @Override
    protected void onInflate(View mContentView) {

    }

    @Override
    public int getLayoutResId() {
        return R.layout.activity_ppdmain;
    }

    int count;

    @Override
    public void onRefresh() {
        count++;
        if (count % 3 == 0) {
            mItemList.clear();
            requestFailure();
            return;
        }else if(count % 4 == 0){
            mItemList.clear();
            requestComplete();
            return;
        }
        NetAsyncTask task = new NetAsyncTask() {
            @Override
            public void onResult(ArrayList<OpenProjectModel> result) {
                mItemList = result;
                ((RecyclerListAdapter) getAdapter()).setItemList(mItemList);
                getAdapter().notifyDataSetChanged();
                requestComplete();

            }
        };
        task.execute(NetAsyncTask.SIMULATE_FRESH_FIRST);
    }

    @Override
    public void onLoadMore(final int page) {
        NetAsyncTask task = new NetAsyncTask() {
            @Override
            public void onResult(ArrayList<OpenProjectModel> result) {
                Log.e("page", "" + page);
                if (page > 3) {
                    requestFailure();
                    return;
                }
                mItemList.addAll(result);
//                getAdapter().setItemList(mItemList);
                getAdapter().notifyDataSetChanged();
                requestComplete();
            }
        };
        task.execute(NetAsyncTask.SIMULATE_FRESH_FIRST);
    }

    @Override
    public boolean hasMore() {
        return true;
    }

    @NonNull
    @Override
    public RecyclerListAdapter createAdapter() {
        return new RecyclerListAdapter() {
            {
                addViewType(OpenProjectModel.class, new ViewHolderFactory<ViewHolder>() {
                    @Override
                    public ViewHolder onCreateViewHolder(ViewGroup parent) {
                        return new ItemViewHolder(parent);
                    }
                });
            }
        };
    }
}
