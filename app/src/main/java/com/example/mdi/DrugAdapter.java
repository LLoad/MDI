package com.example.mdi;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

public class DrugAdapter extends ArrayAdapter<Drug> {

    private Context context;
    private List mList;
    private ListView mListView;

    class UserViewHolder {
        public ImageView image;
        public TextView name;
    }

    public DrugAdapter(Context context, List<Drug> list, ListView listview) {
        super(context, 0, list);
        this.context = context;
        this.mList = list;
        this.mListView = listview;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parentViewGroup) {
        View rowView = convertView;
        UserViewHolder viewHolder;
        String Status;

        if(rowView == null) {
            LayoutInflater layoutInflater = LayoutInflater.from(context);
            rowView = layoutInflater.inflate(R.layout.item, parentViewGroup, false);

            viewHolder = new UserViewHolder();
            viewHolder.image = (ImageView) rowView.findViewById(R.id.itemImage);
            viewHolder.name = (TextView) rowView.findViewById(R.id.itemName);

            rowView.setTag(viewHolder);
            Status = "created";
        } else {
            viewHolder = (UserViewHolder) rowView.getTag();

            Status = "reused";
        }

        String Tag = rowView.getTag().toString();
        int idx = Tag.indexOf("@");
        String tag = Tag.substring(idx + 1);

        Drug drug = (Drug) mList.get(position);

        viewHolder.image.setImageBitmap(drug.getDrug_image_bitmap());
        viewHolder.name.setText(drug.getDrug_name());

        return rowView;
    }
}
