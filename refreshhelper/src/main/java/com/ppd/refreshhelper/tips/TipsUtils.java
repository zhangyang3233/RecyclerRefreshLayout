package com.ppd.refreshhelper.tips;

import android.view.View;
import android.view.ViewGroup;


public class TipsUtils {

    /**
     * Show tips view to the target view.
     *
     * @param targetView the target to show the tips view.
     * @param tipsType   {@link PPDTipsType}
     * @return the tips view.
     */
    public static View showTips(View targetView, PPDTipsType tipsType) {
        Tips tips = tipsType.createTips(targetView.getContext());
        return tips.applyTo(targetView, tipsType.getLayoutRes());
    }

    /**
     * Batch hide tips view from the target view,
     * eg: {@code hideTips(target, TipsType.LOADING, TipsType.FAILED)}.
     *
     * @param targetView the target to show the tips view.
     * @param tipsTypes  the tips type you want to hid, {@link PPDTipsType}.
     */
    public static void hideTips(View targetView, PPDTipsType... tipsTypes) {
        if (targetView == null || tipsTypes == null || tipsTypes.length == 0) {
            return;
        }
        for (PPDTipsType tipsType : tipsTypes) {
            hideTips(targetView, tipsType.getLayoutRes());
        }
    }

    private static void hideTips(View targetView, int tipsId) {
        ViewGroup tipsContainerView = (ViewGroup) targetView.getParent();
        if (!(tipsContainerView instanceof TipsContainer)) {
            return;
        }
        View tipsView = findChildViewById(tipsContainerView, tipsId);
        hideTipsInternal(targetView, tipsView);
    }

    private static void hideTipsInternal(View targetView, View tipsView) {
        ViewGroup tipsContainerView = (ViewGroup) targetView.getParent();
        if (!(tipsContainerView instanceof TipsContainer)) {
            return;
        }
        tipsContainerView.removeView(tipsView);
        boolean hideTarget = false;
        for (int i = 0; i < tipsContainerView.getChildCount(); ++i) {
            Tips tips = (Tips) tipsContainerView.getChildAt(i).getTag();
            if (tips == null) {
                continue;
            }
            hideTarget = tips.mHideTarget;
            if (hideTarget) {
                break;
            }
        }
        targetView.setVisibility(hideTarget ? View.INVISIBLE : View.VISIBLE);
        if (tipsContainerView.getChildCount() == 1) {
            removeContainerView(tipsContainerView, targetView);
        }
    }

    private static void removeContainerView(ViewGroup tipsContainerView, View targetView) {
        ViewGroup parent = (ViewGroup) tipsContainerView.getParent();
        ViewGroup.LayoutParams targetParams = tipsContainerView.getLayoutParams();
        int index = parent.indexOfChild(tipsContainerView);
        parent.removeViewAt(index);
        if (targetView.getParent() != null) {
            ((ViewGroup) targetView.getParent()).removeView(targetView);
        }
        parent.addView(targetView, index, targetParams);
    }

    static View findChildViewById(ViewGroup parent, int id) {
        final int count = parent.getChildCount();
        for (int i = 0; i < count; ++i) {
            View child = parent.getChildAt(i);
            if (child.getId() == id) {
                return child;
            }
        }
        return null;
    }
}
