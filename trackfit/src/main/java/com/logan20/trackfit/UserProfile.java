package com.logan20.trackfit;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class UserProfile extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);
        
        ArrayList<BarEntry> exercises = new ArrayLIst<>();
        exercises.add(new BarEntry(, 0));
        exercises.add(new BarEntry(, 1));
        exercises.add(new BarEntry(, 2));
        exercises.add(new BarEntry(, 3));
        exercises.add(new BarEntry(, 4));
        exercises.add(new BarEntry(, 5));
        BarDataSet dataset = new BarDataSet(exercises, “Time”);

        ArrayList<String> labels = new ArrayLIst<String>();
        labels.add("");
        labels.add("");
        labels.add("");
        labels.add("");
        labels.add("");
        labels.add("");
        BarChart chart = new BarChart(context);
        setContentView(chart);

        BarData data = new BArData(labels, dataset);
        Chart.setData(data);

        Chart.setDescription(“Exercise Duration”)
        Dataset.setColors(ColorTemplate.COLORFUL_COLORS);

        //SETTING THE GOAL LINE SO USERS CAN SEE THEIR PROGRESS WITHRESPECT TO THE OTHER EXERCISES
            LimitLine line = new LimitLine();//input the limit
            Data.addLimitLine(line);

    }
}
