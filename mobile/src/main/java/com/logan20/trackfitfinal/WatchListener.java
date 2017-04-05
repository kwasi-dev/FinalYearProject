package com.logan20.trackfitfinal;

/**
 * Created by kwasi on 4/4/2017.
 */

interface WatchListener {
    void onWatchDataReceive();
    void onHeartRateChange(int newVal);
}
