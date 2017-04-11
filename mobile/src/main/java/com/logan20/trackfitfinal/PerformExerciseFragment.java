package com.logan20.trackfitfinal;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Chronometer;
import android.widget.TextView;
import android.widget.Toast;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.logan20.trackfitcommon.DatabaseHandler;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Locale;
import java.util.Random;

import static com.google.android.gms.wearable.DataMap.TAG;

/**
 * Created by kwasi on 4/11/2017.
 */

public class PerformExerciseFragment extends Fragment implements WatchListener{
    private Exercise exercise;
    private static final int PROJECTION = 30;
    private static long TIMEOUT = 1000;
    private TextView tv;
    private LineChart lc;
    private ArrayList<Entry> entryList;
    private int x;
    private Runnable updater;
    private View v;
    private int currVal;
    private boolean canUpdate;
    private long remaining;
    private CountDownTimer timer;
    private ArrayList<Entry> maxList;
    private ArrayList<Entry> minList;
    private long notifTime;
    private long lastNotifTime;
    private ArrayList<String> increaseNotifs;
    private ArrayList<String>  okNotifs;
    private ArrayList<String>  decreaseNotifs;
    private int ID;
    private boolean exerciseFinished;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_perform_exercise,null);
        lc = (LineChart)v.findViewById(R.id.lc_chart);
        tv = ((TextView)v.findViewById(R.id.tv_heartRate));
        ((TextView)v.findViewById(R.id.tv_exname)).setText(exercise.getName());
        ((TextView)v.findViewById(R.id.tv_duration)).setText("Duration: "+exercise.getDuration());
        notifTime = getContext().getSharedPreferences(getString(R.string.app_name), Context.MODE_PRIVATE).getInt("timer",30);
        notifTime*=1000;
        lastNotifTime= System.currentTimeMillis();
        exerciseFinished = false;
        v.findViewById(R.id.btn_stopExercise).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new AlertDialog.Builder(getContext())
                    .setMessage("Are you sure you want to cancel?")
                    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            ((NavigationActivity)getActivity()).loadHome();
                        }
                    })
                    .setNegativeButton(android.R.string.no,null)
                    .setCancelable(false)
                    .show();
            }
        });
        remaining = exercise.getDurationInSeconds();
        timer = new CountDownTimer(remaining*1000,1000) {
            @Override
            public void onTick(long l) {
                remaining=l;
                long seconds = l / 1000;
                ((TextView)v.findViewById(R.id.elapsed)).setText(String.format(Locale.getDefault(),"Remaining: %02d:%02d",seconds/60,seconds%60));
                long currTime = System.currentTimeMillis();
                if (currTime-lastNotifTime > notifTime){
                    lastNotifTime=currTime;
                    showNotification();
                }
            }

            @Override
            public void onFinish() {
                saveHistory();
                exerciseFinished=true;
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        new AlertDialog.Builder(getActivity())
                                .setMessage("Congratulations.")
                                .setTitle("Exercise complete")
                                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        ((NavigationActivity)getActivity()).loadExerciseList();
                                    }
                                })
                                .setCancelable(false)
                                .show();
                    }
                });
            }
        };
        entryList = new ArrayList<>();
        maxList = new ArrayList<>();
        minList = new ArrayList<>();
        x=0;
        canUpdate=false;
        updater = new Runnable() {
            @Override
            public void run() {
                if (canUpdate){
                    onHeartRateChange(currVal);
                }
                v.postDelayed(this,TIMEOUT);
            }
        };
        v.postDelayed(updater,TIMEOUT);
        return v;
    }

    private void saveHistory() {
        HeartRateListenerService.setListener(null);
        int optimalCount=0;
        int max = exercise.getMax();
        int min = exercise.getMin();
        for (Entry e:entryList){
            if (e.getY()<=max && e.getY()>=min){
                optimalCount++;
            }
        }
        final float percentOptimal = optimalCount*100/entryList.size();
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                DatabaseHandler.saveExHistory(ID,exercise.getId(),percentOptimal,getPoints(percentOptimal));
                return null;
            }
        }.execute();
    }

    private int getPoints(float percentOptimal) {
        if (percentOptimal<50) return 1;
        if (percentOptimal<60) return 2;
        if (percentOptimal<70) return 3;
        if (percentOptimal<80) return 4;
        if (percentOptimal<90) return 5;
        return 10;
    }

    private void showNotification() {
        int max = exercise.getMax();
        int min = exercise.getMin();
        int curr=0;
        if (entryList.size()>0) {
            curr = (int) entryList.get(entryList.size()-1).getY();
        }

        if (curr>max){
            showRandomNotification("decrease");
        }
        else if (curr<min){
            showRandomNotification("increase");
        }
        else{
            showRandomNotification("normal");
        }

    }

    private void showRandomNotification(String type) {
        int rand;
        switch (type){
            case "decrease":
                rand = new Random().nextInt(decreaseNotifs.size());
                Toast.makeText(getContext(), decreaseNotifs.get(rand), Toast.LENGTH_SHORT).show();
                break;
            case "increase":
                rand = new Random().nextInt(increaseNotifs.size());
                Toast.makeText(getContext(), increaseNotifs.get(rand), Toast.LENGTH_SHORT).show();
                break;
            case "normal":
                rand = new Random().nextInt(okNotifs.size());
                Toast.makeText(getContext(), okNotifs.get(rand), Toast.LENGTH_SHORT).show();
                break;
        }
    }

    @Override
    public void onWatchDataReceive() {

    }

    @Override
    public void onResume() {
        super.onResume();
        HeartRateListenerService.setListener(this);
    }

    @Override
    public void onHeartRateChange(final int newVal) {
        if (!exerciseFinished){
            if (!canUpdate){
                timer.start();
            }
            canUpdate=true;
            tv.post(new Runnable() {
                @Override
                public void run() {
                    tv.setText(String.valueOf(newVal));
                }
            });
            currVal = newVal;
            entryList.add(new Entry(x,newVal));
            maxList.add(new Entry(x,exercise.getMax()));
            minList.add(new Entry(x,exercise.getMin()));

            LineDataSet lineDataSet = new LineDataSet(new ArrayList<>(entryList),"");
            LineDataSet lineDataSet2 = new LineDataSet(new ArrayList<>(maxList),"");
            LineDataSet lineDataSet3 = new LineDataSet(new ArrayList<>(minList),"");
            if (entryList.size()>PROJECTION){
                lineDataSet = new LineDataSet(new ArrayList<>(entryList).subList(entryList.size()-PROJECTION,entryList.size()-1),"");
                lineDataSet2 = new LineDataSet(new ArrayList<>(maxList).subList(maxList.size()-PROJECTION,maxList.size()-1),"");
                lineDataSet3 = new LineDataSet(new ArrayList<>(minList).subList(minList.size()-PROJECTION,minList.size()-1),"");
            }
            lineDataSet2.setColor(Color.RED);
            lineDataSet2.setCircleColor(Color.RED);
            lineDataSet3.setCircleColor(Color.RED);
            lineDataSet3.setColor(Color.RED);
            lc.setData(new LineData(lineDataSet,lineDataSet2,lineDataSet3));
            x++;
            lc.post(new Runnable() {
                @Override
                public void run() {
                    lc.invalidate();
                }
            });
        }
    }

    public void setExercise(Exercise exercise) {
        this.exercise = exercise;
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        HeartRateListenerService.setListener(null);
        timer.cancel();
    }

    public void loadNotifs() {
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                increaseNotifs = DatabaseHandler.getNotifByType(2);
                decreaseNotifs = DatabaseHandler.getNotifByType(1);
                okNotifs = DatabaseHandler.getNotifByType(3);
                return null;
            }
        }.execute();
    }

    public void setID(int ID) {
        this.ID = ID;
    }
}
