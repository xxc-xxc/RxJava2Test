package com.xxc.rxjava2test.widget;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.OrientationHelper;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

/**
 * Create By xxc
 * Date: 2020/9/11 17:12
 * Desc:
 */
public class CustomLayoutManager extends RecyclerView.LayoutManager {

    private Context mContext;
    //RecyclerView从右往左滑动时，新出现的child添加在右边
    private static int ADD_RIGHT = 1;
    //RecyclerView从左往右滑动时，新出现的child添加在左边
    private static int ADD_LEFT = -1;
    //SDK中的方法，帮助我们计算一些layout childView 所需的值，详情看源码，解释的很明白
    private OrientationHelper helper;
    //动用 scrollToPosition 后保存去到childView的位置，然后重新布局即调用onLayoutChildren
    private int mPendingScrollPosition = 0;

    public CustomLayoutManager(Context context) {
        mContext = context;
    }

    @Override
    public RecyclerView.LayoutParams generateDefaultLayoutParams() {
        return new RecyclerView.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
    }

    @Override
    public void onLayoutChildren(RecyclerView.Recycler recycler, RecyclerView.State state) {
        if (getItemCount() == 0) {
            detachAndScrapAttachedViews(recycler);
            return;
        }
        if (getChildCount() == 0 && state.isPreLayout()) {
            return;
        }
        //初始化OrientationHelper
        ensureStatus();
        int offset = 0;
        int available = helper.getTotalSpace();
        if (getChildCount() != 0) {
            View firstView = getChildAt(0);
            if (firstView != null) {
                offset = helper.getDecoratedStart(firstView);
                mPendingScrollPosition = getPosition(firstView);
            }
            available += Math.abs(offset);
        }
        detachAndScrapAttachedViews(recycler);
        // 布局可见区域
        layoutScrap(recycler, state, offset, available);
    }

    private void layoutScrap(RecyclerView.Recycler recycler, RecyclerView.State state, int offset, int available) {
        for (int i = mPendingScrollPosition; i < state.getItemCount(); i++) {
            //可用布局宽度不足，跳出循环
            if (available <= 0) {
                break;
            }
            //在右边添加新的childView
            int childWidth = layoutScrapRight(recycler, i, offset);
            available -= childWidth;
            offset += childWidth;
        }
    }

    /**
     *  RecyclerView从右往左滑动时，新出现的child添加在右边
     */
    private int layoutScrapRight(RecyclerView.Recycler recycler, int position, int offset) {
        return layoutScrap(recycler, position, offset, ADD_RIGHT);
    }

    /**
     *  RecyclerView从右往左滑动时，新出现的child添加在右边
     */
    private int layoutScrapLeft(RecyclerView.Recycler recycler, int position, int offset) {
        return layoutScrap(recycler, position, offset, ADD_LEFT);
    }

    /**
     *  RecyclerView从右往左滑动时，添加新的child
     */
    private int layoutScrap(RecyclerView.Recycler recycler, int position, int offset, int direction) {
        //从 recycler 中取到将要出现的childView
        View childPosition = recycler.getViewForPosition(position);
        if (direction == ADD_RIGHT) {
            addView(childPosition);
        } else {
            addView(childPosition, 0);
        }
        //计算childView的大小
        measureChildWithMargins(childPosition, 0, 0);
        int childWidth = getDecoratedMeasuredWidth(childPosition);
        int childHeigth = getDecoratedMeasuredHeight(childPosition);
        if (direction == ADD_RIGHT) {
            //layout childView
            layoutDecorated(childPosition, offset, 0, offset + childWidth, childHeigth);
        } else {
            layoutDecorated(childPosition, offset - childWidth, 0, offset, childHeigth);
        }
        return childWidth;
    }

    private void ensureStatus() {
        if (helper == null) {
            helper = OrientationHelper.createHorizontalHelper(this);
        }
    }

//    detachAndScrapAttachedViews(recycler); // 分离所有的itemView
//    int offsetX = 0;
//    int offsetY = 0;
//
//    int temp7Width = 0;
//    int temp8Width = 0;
//    int temp9Width = 0;
//    int tempOffsetX = 0;
//        for (int i = 0; i < getItemCount(); i++) {
//        View itemView = recycler.getViewForPosition(i); // 获取一个item
//        addView(itemView);
//        measureChildWithMargins(itemView, 0, 0);
//
//        // 获取测量的宽高
//        int width = getDecoratedMeasuredWidth(itemView);
//        int height = getDecoratedMeasuredHeight(itemView);
//
//        // 摆放item
//        if (i < 3) {
//            layoutDecorated(itemView, offsetX, offsetY,
//                    offsetX + width, offsetY + height);
//            offsetY += height + 10;
//            if (i == 2) {
//                offsetX += width + 10;
//                offsetY = 0;
//            }
//        } else if (i == 3) {
//            layoutDecorated(itemView, offsetX, offsetY,
//                    offsetX + width, offsetY + height);
//            offsetY += height + 10;
//        } else if (i == 4) {
//            layoutDecorated(itemView, offsetX, offsetY,
//                    offsetX + width, offsetY + height);
//            offsetX += width + 10;
//        } else if (i == 5) {
//            layoutDecorated(itemView, offsetX, offsetY,
//                    offsetX + width, offsetY + height);
//            offsetX += width + 10;
//            offsetY = 0;
//        } else if (i == 6) {
//            layoutDecorated(itemView, offsetX, offsetY,
//                    offsetX + width, offsetY + height);
//            offsetX += width + 10;
//            offsetY = 0;
//        } else if (i == 7) {
//            layoutDecorated(itemView, offsetX, offsetY,
//                    offsetX + width, offsetY + height);
//            offsetY += height + 10;
//            temp7Width = width;
//        } else if (i == 8) {
//            layoutDecorated(itemView, offsetX, offsetY,
//                    offsetX + width, offsetY + height);
//            temp8Width = width;
//            tempOffsetX = offsetX;
//            offsetX += temp7Width;
//            offsetY = 0;
//        } else if (i == 9) {
//            layoutDecorated(itemView, offsetX, offsetY,
//                    offsetX + width, offsetY + height);
//            temp9Width = width;
//            offsetX = tempOffsetX + temp8Width;
//            offsetY += height + 10;
//        } else if (i == 10) {
//            layoutDecorated(itemView, offsetX, offsetY,
//                    offsetX + width, offsetY + height);
//            offsetX += temp9Width;
//            offsetY = 0;
//        } else if (i == 11) {
//            layoutDecorated(itemView, offsetX, offsetY,
//                    offsetX + width, offsetY + height);
//            offsetY += height + 10;
//        } else if (i == 12) {
//            layoutDecorated(itemView, offsetX, offsetY,
//                    offsetX + width, offsetY + height);
//            offsetX += width + 10;
//            offsetY = 0;
//        } else {
//            layoutDecorated(itemView, offsetX, offsetY,
//                    offsetX + width, offsetY + height);
//            offsetX += width + 10;
//            offsetY = 0;
//        }
//    }
}
