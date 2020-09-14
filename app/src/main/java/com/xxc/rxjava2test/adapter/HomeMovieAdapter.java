package com.xxc.rxjava2test.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.xxc.rxjava2test.R;
import com.xxc.rxjava2test.utils.LogUtils;

import java.util.List;

public class HomeMovieAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int ITEM_VIEW_TYPE_CATEGORY = 0;
    private static final int ITEM_VIEW_TYPE_RECOMMEND1 = 1;
    private static final int ITEM_VIEW_TYPE_HIS_OR_FAV = 2;
    private static final int ITEM_VIEW_TYPE_RECOMMEND2 = 3;
    private static final int ITEM_VIEW_TYPE_RECOMMEND3 = 4;
    private static final int ITEM_VIEW_TYPE_RECOMMEND4 = 5;
    private static final int ITEM_VIEW_TYPE_RECOMMEND5 = 6;
    private static final int ITEM_VIEW_TYPE_RECOMMEND6 = 7;
    private static final String TAG = HomeMovieAdapter.class.getSimpleName();
    // 数据集
    private List<String> mDataSet;
    private Context mContext;

    public HomeMovieAdapter(Context context, List<String> data) {
        super();
        mDataSet = data;
        mContext = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View view;
        if (viewType == ITEM_VIEW_TYPE_CATEGORY) {
            view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_home_category_layout, parent, false);
            return new CategoryViewHolder(view);
        } else if (viewType == ITEM_VIEW_TYPE_RECOMMEND1) {
            view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_home_recommend1_layout, parent, false);
            return new Recommend1ViewHolder(view);
        } else if (viewType == ITEM_VIEW_TYPE_HIS_OR_FAV) {
            view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_home_his_or_fav_layout, parent, false);
            return new HisOrFavViewHolder(view);
        } else if (viewType == ITEM_VIEW_TYPE_RECOMMEND3) {
            view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_home_recommend3_layout, parent, false);
            return new Recommend3ViewHolder(view);
        } else if (viewType == ITEM_VIEW_TYPE_RECOMMEND4) {
            view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_home_recommend4_layout, parent, false);
            return new Recommend4ViewHolder(view);
        } else if (viewType == ITEM_VIEW_TYPE_RECOMMEND5) {
            view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_home_recommend5_layout, parent, false);
            return new Recommend5ViewHolder(view);
        } else if (viewType == ITEM_VIEW_TYPE_RECOMMEND6) {
            view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_home_recommend6_layout, parent, false);
            return new Recommend6ViewHolder(view);
        } else {
            view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_home_recommend2_layout, parent, false);
            return new Recommend2ViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int position) {
        int viewType = getItemViewType(position);
        if (viewType == ITEM_VIEW_TYPE_CATEGORY) {
            CategoryViewHolder holder = (CategoryViewHolder) viewHolder;
            holder.itemView.setTag(position);
            if (position == 0) {
                holder.categoryImg.setImageResource(R.drawable.iv_movie_poster);
            } else if (position == 1) {
                holder.categoryImg.setImageResource(R.drawable.iv_tv_poster);
            } else if (position == 2) {
                holder.categoryImg.setImageResource(R.drawable.iv_variety_poster);
            }
        } else if (viewType == ITEM_VIEW_TYPE_RECOMMEND1) {
            Recommend1ViewHolder holder = (Recommend1ViewHolder) viewHolder;
            holder.recommend1Img.setImageResource(R.drawable.iv_recommend1_poster);
        } else if (viewType == ITEM_VIEW_TYPE_HIS_OR_FAV) {
            HisOrFavViewHolder holder = (HisOrFavViewHolder) viewHolder;
            if (position == 4) {
                holder.hisImg.setImageResource(R.drawable.history_play);
            } else if (position == 5){
                holder.hisImg.setImageResource(R.drawable.personal_collection);
            }
        } else if (viewType == ITEM_VIEW_TYPE_RECOMMEND3) {
            Recommend3ViewHolder holder = (Recommend3ViewHolder) viewHolder;
            holder.recommend3Img.setImageResource(R.drawable.iv_recommend3_poster);
        } else if (viewType == ITEM_VIEW_TYPE_RECOMMEND4) {
            Recommend4ViewHolder holder = (Recommend4ViewHolder) viewHolder;
            holder.recommend4Img.setImageResource(R.drawable.iv_recommend4_poster);
        } else if (viewType == ITEM_VIEW_TYPE_RECOMMEND5) {
            Recommend5ViewHolder holder = (Recommend5ViewHolder) viewHolder;
            holder.recommend5Img.setImageResource(R.drawable.iv_recommend5_poster);
        } else if (viewType == ITEM_VIEW_TYPE_RECOMMEND6) {
            Recommend6ViewHolder holder = (Recommend6ViewHolder) viewHolder;
            holder.recommend6Img.setImageResource(R.drawable.iv_recommend6_poster);
        } else {
            Recommend2ViewHolder holder = (Recommend2ViewHolder) viewHolder;
            holder.recommend2Img.setImageResource(R.drawable.iv_recommend2_poster);
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (position < 3) {
            return ITEM_VIEW_TYPE_CATEGORY;
        } else if (position == 3) {
            return ITEM_VIEW_TYPE_RECOMMEND1;
        } else if (position == 4 || position == 5) {
            return ITEM_VIEW_TYPE_HIS_OR_FAV;
        } else if (position == 7) {
            return ITEM_VIEW_TYPE_RECOMMEND3;
        } else if (position == 9 || position == 11) {
            return ITEM_VIEW_TYPE_RECOMMEND4;
        } else if (position == 8 || position == 12) {
            return ITEM_VIEW_TYPE_RECOMMEND5;
        } else if (position == 10) {
            return ITEM_VIEW_TYPE_RECOMMEND6;
        } else {
            return ITEM_VIEW_TYPE_RECOMMEND2;
        }
    }

    @Override
    public int getItemCount() {
        return mDataSet.size();
    }

    public static class CategoryViewHolder extends RecyclerView.ViewHolder {

        private final ImageView categoryImg;

        public CategoryViewHolder(@NonNull View itemView) {
            super(itemView);
            categoryImg = itemView.findViewById(R.id.iv_category_img);
        }
    }

    public static class Recommend1ViewHolder extends RecyclerView.ViewHolder {

        private final ImageView recommend1Img;

        public Recommend1ViewHolder(@NonNull View itemView) {
            super(itemView);
            recommend1Img = itemView.findViewById(R.id.iv_recommend1_img);
        }
    }

    public static class HisOrFavViewHolder extends RecyclerView.ViewHolder {

        private final ImageView hisImg;

        public HisOrFavViewHolder(@NonNull View itemView) {
            super(itemView);
            hisImg = itemView.findViewById(R.id.iv_his_img);
        }
    }

    public static class Recommend2ViewHolder extends RecyclerView.ViewHolder {

        private final ImageView recommend2Img;

        public Recommend2ViewHolder(@NonNull View itemView) {
            super(itemView);
            recommend2Img = itemView.findViewById(R.id.iv_recommend2_img);
        }
    }

    public static class Recommend3ViewHolder extends RecyclerView.ViewHolder {

        private final ImageView recommend3Img;

        public Recommend3ViewHolder(@NonNull View itemView) {
            super(itemView);
            recommend3Img = itemView.findViewById(R.id.iv_recommend3_img);
        }
    }

    public static class Recommend4ViewHolder extends RecyclerView.ViewHolder {

        private final ImageView recommend4Img;

        public Recommend4ViewHolder(@NonNull View itemView) {
            super(itemView);
            recommend4Img = itemView.findViewById(R.id.iv_recommend4_img);
        }
    }

    public static class Recommend5ViewHolder extends RecyclerView.ViewHolder {

        private final ImageView recommend5Img;

        public Recommend5ViewHolder(@NonNull View itemView) {
            super(itemView);
            recommend5Img = itemView.findViewById(R.id.iv_recommend5_img);
        }
    }

    public static class Recommend6ViewHolder extends RecyclerView.ViewHolder {

        private final ImageView recommend6Img;

        public Recommend6ViewHolder(@NonNull View itemView) {
            super(itemView);
            recommend6Img = itemView.findViewById(R.id.iv_recommend6_img);
        }
    }

}
