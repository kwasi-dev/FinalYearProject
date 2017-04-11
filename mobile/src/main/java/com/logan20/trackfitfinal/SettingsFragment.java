package com.logan20.trackfitfinal;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

/**
 * Created by kwasi on 4/10/2017.
 */

public class SettingsFragment extends Fragment{
    private String[] values;
    private int currIdx;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        values = getResources().getStringArray(R.array.timer_values);
        View v = inflater.inflate(R.layout.fragment_settings,null);
        currIdx=0;
        ((Spinner)v.findViewById(R.id.spnr_timer)).setAdapter(new ArrayAdapter<>(getContext(),android.R.layout.simple_spinner_dropdown_item,values));
        ((Spinner)v.findViewById(R.id.spnr_timer)).setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                currIdx = i;
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        v.findViewById(R.id.btn_submit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new AlertDialog.Builder(getContext())
                        .setMessage("Do you want to save your changes?")
                        .setTitle("Save")
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                save();
                            }
                        })
                        .setNegativeButton(android.R.string.no,null)
                        .setCancelable(false)
                        .show();
            }
        });
        return v;
    }

    private void save() {
        int timer=0;
        switch (values[currIdx]){
            case "30 seconds":
                timer = 30;
                break;
            case "45 seconds":
                timer = 45;
                break;
            case "60 seconds":
                timer = 60;
                break;
            case "120 seconds":
                timer = 120;
                break;
            case "180 seconds":
                timer = 180;
                break;
            case "300 seconds":
                timer = 300;
                break;
            case "600 seconds":
                timer = 600;
                break;
        }
        getContext().getSharedPreferences(getString(R.string.app_name), Context.MODE_PRIVATE).edit().putInt("timer",timer).apply();
    }
}
