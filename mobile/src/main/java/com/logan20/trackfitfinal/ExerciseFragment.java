package com.logan20.trackfitfinal;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.logan20.trackfitcommon.DatabaseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import static com.google.android.gms.wearable.DataMap.TAG;

/**
 * Created by kwasi on 4/10/2017.
 */

public class ExerciseFragment extends Fragment implements ExerciseListener{
    private View v;
    private ArrayList<Exercise> exerciseList;
    private int userId;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_exercise,null);
        ListView lv = (ListView)v.findViewById(R.id.lv_exercises);

        lv.setAdapter(new ExerciseAdapter(getContext(),exerciseList,this));

        return v;
    }

    public void setId(final int id) {
        this.userId=id;
        exerciseList = new ArrayList<>();
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                JSONArray exercises = DatabaseHandler.getUserExercises(id);
                if (exercises!=null){
                    for (int a=0;a<exercises.length();a++){
                        try {
                            JSONObject obj = (JSONObject) exercises.get(a);
                            exerciseList.add(new Exercise(obj));
                        } catch (JSONException e) {}
                    }
                }
                else{
                    Log.e(TAG, "doInBackground: NULL JSONARR" );
                }
                return null;
            }

        }.execute();
    }


    @Override
    public void onExerciseStart(Exercise e){
        PerformExerciseFragment performExerciseFragment = new PerformExerciseFragment();
        performExerciseFragment.setExercise(e);
        performExerciseFragment.setID(userId);
        performExerciseFragment.loadNotifs();
        getActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.fl_content,performExerciseFragment)
                .commit();
    }
}
