package com.example.mybox.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.mybox.R;
import com.example.mybox.activity.DesignSubActivity;


/**
 */
public class DesignRecyclerViewAdapter extends RecyclerView.Adapter<DesignRecyclerViewAdapter.ViewHolder> {

    private int[] colors = {R.color.c_ff6666, R.color.c_ccccff, R.color.c_9999cc, R.color.c_ffffcc,
            R.color.c_cccc33, R.color.c_cc9966, R.color.c_cccccc, R.color.c_cccc99,
            R.color.c_cc9966, R.color.c_ff99cc,};

    private Context mContext;

    public DesignRecyclerViewAdapter(Context mContext) {
        DesignRecyclerViewAdapter.this.mContext = mContext;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        TextView view = (TextView) LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.mTextView.setBackgroundColor(mContext.getResources().getColor(colors[position % (colors.length)]));
        holder.mTextView.setText(position + "");

        holder.mTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mContext.startActivity(new Intent(mContext, DesignSubActivity.class));
            }
        });
    }

    @Override
    public int getItemCount() {
        return colors.length * 2;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public final TextView mTextView;

        public ViewHolder(TextView view) {
            super(view);
            mTextView = view;
        }
    }
}
