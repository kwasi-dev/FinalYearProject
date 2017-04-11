package com.logan20.trackfitfinal;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.logan20.trackfitcommon.DatabaseHandler;

import static com.google.android.gms.wearable.DataMap.TAG;

/**
 * Created by kwasi on 4/10/2017.
 */

public class HomeFragment extends Fragment {
    private String name;
    private View v;
    private String currWeight;
    private String startWeight;
    private String rank;
    private String exPref;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_home,null);
        reload();
        return v;
    }

    public void setId(final int id) {
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                name = DatabaseHandler.getNameFromID(id);
                startWeight = String.valueOf(DatabaseHandler.getStartingWeight(id));
                currWeight = String.valueOf(DatabaseHandler.getCurrentWeight(id));
                rank = String.valueOf(DatabaseHandler.getRank(id));
                exPref = DatabaseHandler.getExercisePrefAsText(id);
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                reload();
            }
        }.execute();
    }

    private void reload() {
        ((TextView)v.findViewById(R.id.tv_welcome)).setText("Welcome Back: "+name.split(" ")[0]);
        ((TextView)v.findViewById(R.id.tv_startWeight)).setText("Starting Weight:  "+ startWeight);
        ((TextView)v.findViewById(R.id.tv_currWeight)).setText("Current Weight:  "+ currWeight);
        ((TextView)v.findViewById(R.id.tv_rank)).setText("Current Rank:  "+ rank);
        ((TextView)v.findViewById(R.id.tv_expref)).setText("Exercise Preference:  "+ exPref);

    }

}
