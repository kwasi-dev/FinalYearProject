package com.logan20.trackfitfinal;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import static com.google.android.gms.wearable.DataMap.TAG;

/**
 * Created by kwasi on 4/10/2017.
 *
 */

class Exercise {
    private final JSONObject data;

    public Exercise(JSONObject obj) {
        this.data = obj;
        Log.e(TAG, "Exercise: "+data.toString());
    }

    public String  getName() {
        try {
            return data.getString("name");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return "";
    }

    public String getDuration() {
        try {
            return data.getString("duration");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return "";
    }

    public int getDurationInSeconds() {
        String [] tokens = getDuration().split(":");
        return Integer.parseInt(tokens[0])*3600 + Integer.parseInt(tokens[1])*60 + Integer.parseInt(tokens[2]);
    }

    public int getMax() {
        try {
            return data.getInt("zoneMax");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public int getMin() {
        try {
            return data.getInt("zoneMin");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public int getId() {
        try {
            return data.getInt("id");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return 0;
    }
}
