package com.logan20.trackfit;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.content.Intent;
import com.facebook.FacebookSdk;
import com.logan20.trackfit.database.DatabaseHandler;

import android.widget.TextView;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        FacebookSdk.sdkInitialize(this);
        setContentView(R.layout.activity_login);
        try{
            DatabaseHandler.init();
        }catch(Exception e){
            e.printStackTrace();
        }

    }

    public void register(View view) {
        startActivity(new Intent(LoginActivity.this,RegisterActivity.class));
    }

    public void login(View view) {
        String email = ((EditText)findViewById(R.id.et_email)).getText().toString();
        String password = ((EditText)findViewById(R.id.et_password)).getText().toString();

        if (email.isEmpty()){
            Toast.makeText(this, "Please enter an email!", Toast.LENGTH_SHORT).show();
            return;
        }
        if (password.isEmpty()){
            Toast.makeText(this, "Please enter a password", Toast.LENGTH_SHORT).show();
            return;
        }
        Toast.makeText(this, "Todo - match data with server and login",Toast.LENGTH_SHORT).show();
    }
}
