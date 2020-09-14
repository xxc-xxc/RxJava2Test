package com.xxc.rxjava2test.widget;

import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

/**
 * Create By xxc
 * Date: 2020/9/14 15:38
 * Desc:
 */
public class TestLayoutManager extends RecyclerView.LayoutManager {
    private int mTotalHeight;

    @Override
    public RecyclerView.LayoutParams generateDefaultLayoutParams() {
        return new RecyclerView.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
    }

    @Override
    public void onLayoutChildren(RecyclerView.Recycler recycler, RecyclerView.State state) {
        super.onLayoutChildren(recycler, state);

        int offsetY = 0;
        for (int i = 0; i < getItemCount(); i++) {
            View scrap = recycler.getViewForPosition(i);
            addView(scrap);

            measureChildWithMargins(scrap, 0, 0);

            int perItemWidth = getDecoratedMeasuredWidth(scrap);
            int perItemHeight = getDecoratedMeasuredHeight(scrap);

            layoutDecorated(scrap, 0, offsetY, perItemWidth, offsetY + perItemHeight);
            offsetY += perItemHeight;
        }

        mTotalHeight = offsetY;
    }

    @Override
    public boolean canScrollVertically() {
        return true;
    }

    @Override
    public int scrollVerticallyBy(int dy, RecyclerView.Recycler recycler, RecyclerView.State state) {
        offsetChildrenVertical(dy);
        return super.scrollVerticallyBy(dy,recycler,state);
    }
}
