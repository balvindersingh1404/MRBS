package com.example.roombooking.roombooking.roomList;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;


import com.example.roombooking.roombooking.R;
import com.example.roombooking.roombooking.models.Data;

import java.util.List;


public class MyAdapter extends ArrayAdapter<List<Data>> {
    SelectedItem listener;
    Context context;
    List<Datum> list;


    public MyAdapter(Context context, List<Datum> List,SelectedItem listener) {
        super(context, R.layout.list_item);
        this.list = List;
        this.context = context;
        this.listener=listener;
    }

    @Override
    public int getCount() {
        if (list != null)
            return list.size();
        else return 0;
    }



    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Datum data = list.get(position);
        ViewHolder holder = null;
        if (convertView == null) {

            convertView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.list_item, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        if (data != null) {
            if (data.getNoofseats()!= null && !data.getNoofseats().isEmpty()) {
                holder.title.setText(data.getName());
            } else {
                holder.title.setVisibility(View.INVISIBLE);
            }
            }
        holder.title.setTag(position);
        final ViewHolder finalHolder = holder;
        holder.title.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(listener!=null){
                   int positin= (int) finalHolder.title.getTag();
                    listener.seletedItem(positin);
                }
            }
        });
        return convertView;
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {

        private TextView title;

        public ViewHolder(View itemLayoutView) {
            super(itemLayoutView);
            title = (TextView) itemLayoutView.findViewById(R.id.name);
        }
    }

    public interface  SelectedItem{
        void seletedItem(int positin);
    }

}