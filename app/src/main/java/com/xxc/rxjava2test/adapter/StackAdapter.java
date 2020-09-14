package com.xxc.rxjava2test.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.xxc.rxjava2test.R;

import java.util.List;

/**
 * Create By xxc
 * Date: 2020/9/14 15:58
 * Desc:
 */
public class StackAdapter extends RecyclerView.Adapter<StackAdapter.StackViewHolder> {
    private Context mContext;
    private List<String> dataList;

    public StackAdapter(Context context, List<String> dataList) {
        mContext = context;
        this.dataList = dataList;
    }

    @NonNull
    @Override
    public StackViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_stack, parent, false);
        return new StackViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull StackViewHolder holder, int position) {
        holder.itemTxt.setText("position ==> " + position);
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public static class StackViewHolder extends RecyclerView.ViewHolder {

        private TextView itemTxt;
        public StackViewHolder(@NonNull View itemView) {
            super(itemView);
            itemTxt = itemView.findViewById(R.id.item_txt);
        }
    }

}
