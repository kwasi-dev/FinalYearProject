package com.logan20.trackfitfinal;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by kwasi on 4/11/2017.
 */

class HistoryAdapter extends BaseAdapter {
    private final Context context;
    private final ArrayList<HistoryItem> list;

    public HistoryAdapter(Context context, ArrayList<HistoryItem> list) {
        this.context=context;
        this.list=list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int i) {
        return list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if (view==null){
            view = LayoutInflater.from(context).inflate(R.layout.history_item,null);
        }
        HistoryItem item = (HistoryItem) getItem(i);

        ((TextView) view.findViewById(R.id.tv_points)).setText(String.valueOf(item.getPoints()));
        ((TextView) view.findViewById(R.id.tv_percent)).setText(String.valueOf(item.getPercent()));
        ((TextView) view.findViewById(R.id.tv_name)).setText(item.getName());

        return view ;
    }
}
