package com.logan20.trackfitfinal;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by kwasi on 4/11/2017.
 */

class HistoryItem {
    private JSONObject data;

    HistoryItem(JSONObject data){
        this.data=data;
    }

    public String getName(){
        try {
            return data.getString("name");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return "";
    }

    public int getPoints(){
        try {
            return data.getInt("points");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public float getPercent(){
        try {
            return (float) data.getDouble("percent");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return 0f;
    }
}
