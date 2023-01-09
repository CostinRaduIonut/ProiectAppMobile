package com.example.desters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

public class itemAdapter extends BaseAdapter {
    String[] initialList;
    int[] initialImages;
    LayoutInflater inflater;

    public itemAdapter(Context context, String[] initialList, int[] initialImages) {
        this.initialList = initialList;
        this.initialImages = initialImages;
        this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return initialList.length;
    }

    @Override
    public Object getItem(int position) {
        return initialList[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View currentView = inflater.inflate(R.layout.my_main_list, null);
        TextView tvText = currentView.findViewById(R.id.list_text);
        ImageView tvTmage = currentView.findViewById(R.id.list_img);
        tvText.setText(initialList[position]);
        tvTmage.setImageResource(initialImages[position]);

        return currentView;
    }
}
