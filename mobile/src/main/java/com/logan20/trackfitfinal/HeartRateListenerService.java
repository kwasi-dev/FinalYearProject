package com.logan20.trackfitfinal;

import android.content.Context;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.wearable.MessageEvent;
import com.google.android.gms.wearable.Wearable;
import com.google.android.gms.wearable.WearableListenerService;

import java.nio.ByteBuffer;

import static android.content.ContentValues.TAG;

/**
 * This class acts as the Broadcast service receiver.
 * It accepts messages that are sent from the watch and then processes it accordingly.
 * @author Logan20
 */


public class HeartRateListenerService extends WearableListenerService{
    private static GoogleApiClient client;
    private static WatchListener listener;
    private static int currHeartRate;

    public static void init(Context context) {
        /*Build the GoogleAPIClient from the context that is passed in as a parameter*/
        client = new GoogleApiClient.Builder(context)
                .addApi(Wearable.API)
                .build();

        /*Connect to the client*/
        client.connect();
    }

    public static void setListener(WatchListener l){
        //Sets the interface so that the information can be passed from the Service to the appropriate activity
        listener = l;
    }

    /*Sends the heartrate value to the appropriate activity who's currently set as the listener of the Service.
    * The method also calls the appropriate method based on if the heart rate remains constant or if it has changed*/
    @Override
    public void onMessageReceived(MessageEvent messageEvent) {
        if (listener!=null){
            String num = new String(messageEvent.getData());
            int curr = Integer.parseInt(num);
            if (curr==currHeartRate){
                listener.onWatchDataReceive();
            }
            else{
                listener.onHeartRateChange(curr);
                currHeartRate=curr;
            }
        }
    }
}
