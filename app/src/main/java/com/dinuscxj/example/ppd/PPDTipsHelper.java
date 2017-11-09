package com.dinuscxj.example.ppd;

import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.ppd.refreshhelper.tips.PPDTipsType;
import com.ppd.refreshhelper.tips.TipsHelper;
import com.ppd.refreshhelper.tips.TipsUtils;


public class PPDTipsHelper implements TipsHelper {
    protected final View mTagView;
    protected View.OnClickListener mEmptyOnclickListener;
    protected View.OnClickListener mErrorOnclickListener;
    protected View.OnClickListener mLoadingOnclickListener;
    PPDTipsType mEmptyType;
    PPDTipsType mLoadingType;
    PPDTipsType mErrorType;

    public void setOnEmpty(int emptyViewRes, View.OnClickListener onClickListener) {
        mEmptyType = new PPDTipsType(emptyViewRes);
        mEmptyOnclickListener = onClickListener;
    }

    public void setOnLoading(int loadingViewRes, View.OnClickListener onClickListener) {
        mLoadingType = new PPDTipsType(loadingViewRes);
        mLoadingOnclickListener = onClickListener;
    }

    public void setOnError(int errorViewRes, View.OnClickListener onClickListener) {
        mErrorType = new PPDTipsType(errorViewRes);
        mErrorOnclickListener = onClickListener;
    }


    public PPDTipsHelper(View tagView) {
        this.mTagView = tagView;
        mEmptyType = PPDTipsType.EMPTY;
        mErrorType = PPDTipsType.LOADING_ERROR;
        mLoadingType = PPDTipsType.LOADING;
    }

    @Override
    public void showEmpty() {
        hideLoading();
        View tipsView = TipsUtils.showTips(mTagView, mEmptyType);
        tipsView.setOnClickListener(mEmptyOnclickListener);
    }

    @Override
    public void hideEmpty() {
        TipsUtils.hideTips(mTagView, mEmptyType);
    }

    @Override
    public void showLoading(boolean firstPage) {
        hideEmpty();
        hideError();
        if (firstPage) {
            View tipsView = TipsUtils.showTips(mTagView, mLoadingType);
            if (tipsView instanceof ImageView) {
                Drawable d = ((ImageView) tipsView).getDrawable();
                if (d instanceof AnimationDrawable) {
                    ((AnimationDrawable) d).start();
                }
            }
            return;
        }
    }

    @Override
    public void hideLoading() {
        TipsUtils.hideTips(mTagView, mLoadingType);
    }

    @Override
    public void showError(boolean firstPage, Throwable error) {
        String errorMessage = error.getMessage();
        if (firstPage) {
            View tipsView = TipsUtils.showTips(mTagView, mErrorType);
            tipsView.setOnClickListener(mErrorOnclickListener);
            return;
        }

        Toast.makeText(mTagView.getContext(), errorMessage, Toast.LENGTH_LONG).show();
    }

    @Override
    public void hideError() {
        TipsUtils.hideTips(mTagView, mErrorType);
    }


}
