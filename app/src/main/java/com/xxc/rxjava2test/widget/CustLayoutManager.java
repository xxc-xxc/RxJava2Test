package com.xxc.rxjava2test.widget;

import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

/**
 * Create By xxc
 * Date: 2020/9/11 17:12
 * Desc:
 */
public class CustLayoutManager extends RecyclerView.LayoutManager {
    @Override
    public RecyclerView.LayoutParams generateDefaultLayoutParams() {
        return new RecyclerView.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
    }

    @Override
    public void onLayoutChildren(RecyclerView.Recycler recycler, RecyclerView.State state) {
        detachAndScrapAttachedViews(recycler); // 分离所有的itemView
        int offsetX = 0;
        int offsetY = 0;

        int temp7Width = 0;
        int temp8Width = 0;
        int temp9Width = 0;
        int tempOffsetX = 0;
        for (int i = 0; i < getItemCount(); i++) {
            View itemView = recycler.getViewForPosition(i); // 获取一个item
            addView(itemView);
            measureChildWithMargins(itemView, 0, 0);

            // 获取测量的宽高
            int width = getDecoratedMeasuredWidth(itemView);
            int height = getDecoratedMeasuredHeight(itemView);

            // 摆放item
            if (i < 3) {
                layoutDecorated(itemView, offsetX, offsetY,
                        offsetX + width, offsetY + height);
                offsetY += height + 10;
                if (i == 2) {
                    offsetX += width + 10;
                    offsetY = 0;
                }
            } else if (i == 3) {
                layoutDecorated(itemView, offsetX, offsetY,
                        offsetX + width, offsetY + height);
                offsetY += height + 10;
            } else if (i == 4) {
                layoutDecorated(itemView, offsetX, offsetY,
                        offsetX + width, offsetY + height);
                offsetX += width + 10;
            } else if (i == 5) {
                layoutDecorated(itemView, offsetX, offsetY,
                        offsetX + width, offsetY + height);
                offsetX += width + 10;
                offsetY = 0;
            } else if (i == 6) {
                layoutDecorated(itemView, offsetX, offsetY,
                        offsetX + width, offsetY + height);
                offsetX += width + 10;
                offsetY = 0;
            } else if (i == 7) {
                layoutDecorated(itemView, offsetX, offsetY,
                        offsetX + width, offsetY + height);
                offsetY += height + 10;
                temp7Width = width;
            } else if (i == 8) {
                layoutDecorated(itemView, offsetX, offsetY,
                        offsetX + width, offsetY + height);
                temp8Width = width;
                tempOffsetX = offsetX;
                offsetX += temp7Width;
                offsetY = 0;
            } else if (i == 9) {
                layoutDecorated(itemView, offsetX, offsetY,
                        offsetX + width, offsetY + height);
                temp9Width = width;
                offsetX = tempOffsetX + temp8Width;
                offsetY += height + 10;
            } else if (i == 10) {
                layoutDecorated(itemView, offsetX, offsetY,
                        offsetX + width, offsetY + height);
                offsetX += temp9Width;
                offsetY = 0;
            } else if (i == 11) {
                layoutDecorated(itemView, offsetX, offsetY,
                        offsetX + width, offsetY + height);
                offsetY += height + 10;
            } else if (i == 12) {
                layoutDecorated(itemView, offsetX, offsetY,
                        offsetX + width, offsetY + height);
                offsetX += width + 10;
                offsetY = 0;
            } else {
                layoutDecorated(itemView, offsetX, offsetY,
                        offsetX + width, offsetY + height);
                offsetX += width + 10;
                offsetY = 0;
            }
        }
    }
}
