package com.example.mdi;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ItemViewHolder> {
    ArrayList<RecyclerItem> mItems;

    public RecyclerAdapter(ArrayList<RecyclerItem>items) {
        mItems = items;
    }

    @NonNull @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.drug_count, viewGroup, false);
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder itemViewHolder, int i) {
        itemViewHolder.drugCounting.setText(mItems.get(i).getCounting());
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }

    class ItemViewHolder extends RecyclerView.ViewHolder{
        private TextView drugCounting;
        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);
            drugCounting = (TextView)itemView.findViewById(R.id.counting);
        }
    }
}
