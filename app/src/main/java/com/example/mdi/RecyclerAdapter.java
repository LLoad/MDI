package com.example.mdi;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ItemViewHolder> {
    private ArrayList<Integer> mItems;
    private LayoutInflater mInflater;
    private ItemClickListener mClickListener;

    public RecyclerAdapter(Context context, ArrayList<Integer> items) {
        this.mInflater = LayoutInflater.from(context);
        mItems = items;
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view = mInflater.inflate(R.layout.drug_count, viewGroup, false);
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder itemViewHolder, int position) {
        int count = mItems.get(position);
        itemViewHolder.mTextView.setText(String.valueOf(count + 1));
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }

    class ItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView mTextView;

        ItemViewHolder(@NonNull View itemView) {
            super(itemView);
            mTextView = (TextView)itemView.findViewById(R.id.counting);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if(mClickListener != null) mClickListener.onItemClick(v, getAdapterPosition());
        }
    }

    public int getItem(int id) { return mItems.get(id); }

    public void setClickListener(ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }

    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }

}
