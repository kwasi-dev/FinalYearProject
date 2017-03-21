package com.logan20.trackfit;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.os.CountDownTimer;

import com.facebook.FacebookSdk;

public class SplashScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FacebookSdk.setApplicationId(getString(R.string.facebook_app_id));
        new CountDownTimer(2000,1000){

            @Override
            public void onTick(long l) {

            }

            @Override
            public void onFinish() {
                startActivity(new Intent(SplashScreen.this,LoginActivity.class));
                finish();
            }
        }.start();
    }
}
