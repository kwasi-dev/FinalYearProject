package com.logan20.trackfitfinal;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by kwasi on 4/4/2017.
 */

public class ViewHeartrateFragment extends Fragment implements WatchListener{

    private TextView tv;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_view_heartrate,null);
        tv = ((TextView)v.findViewById(R.id.tv_heartRate));
        HeartRateListenerService.setListener(this);
        return v;
    }

    @Override
    public void onWatchDataReceive() {

    }

    @Override
    public void onHeartRateChange(final int newVal) {
        tv.post(new Runnable() {
            @Override
            public void run() {
                tv.setText(String.valueOf(newVal));
            }
        });

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        HeartRateListenerService.setListener(null);
    }
}
