package com.logan20.trackfitfinal;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.logan20.trackfitcommon.DatabaseHandler;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;

/**
 * Created by kwasi on 4/10/2017.
 */

public class HistoryFragment extends Fragment{
    private ArrayList<HistoryItem> list;
    public void setId(final int id) {
        list = new ArrayList<>();
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                JSONArray arr = DatabaseHandler.getHistoryById(id);
                for (int a=0;a<arr.length();a++){
                    try {
                        list.add(new HistoryItem(arr.getJSONObject(a)));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                return null;
            }
        }.execute();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_history,null);
        ListView lv = (ListView)v.findViewById(R.id.lv_history);
        lv.setAdapter(new HistoryAdapter(getContext(),list));
        return v;
    }
}
