package com.logan20.trackfitfinal;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by kwasi on 4/11/2017.
 */

class ExerciseAdapter extends BaseAdapter{
    private final ExerciseListener listener;
    private ArrayList<Exercise> data;
    private Context context;

    public ExerciseAdapter(Context context, ArrayList<Exercise> exerciseList, ExerciseListener listener) {
        this.context=context;
        this.data=exerciseList;
        this.listener=listener;
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int i) {
        return data.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if (view==null){
            view = LayoutInflater.from(context).inflate(R.layout.exercise_listview_item,null);
        }
        final Exercise item = (Exercise) getItem(i);
        ((TextView)view.findViewById(R.id.tv_exname)).setText(item.getName());
        view.findViewById(R.id.btn_start).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startExercise(item);
            }
        });
        return view;
    }

    private void startExercise(Exercise item) {
        listener.onExerciseStart(item);
    }
}
