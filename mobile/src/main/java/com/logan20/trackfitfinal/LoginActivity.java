package com.logan20.trackfitfinal;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import com.logan20.trackfitcommon.DatabaseHandler;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        HeartRateListenerService.init(this); //initialise the listener service
        DatabaseHandler.init();//initialises the database handler
    }

    /*This method will run when the register option is clicked from the login screen*/
    public void register(View view) {
        startActivity(new Intent(LoginActivity.this,RegisterActivity.class));
    }

    /*This method will wun when the login button is clicked from the login screen*/
    public void login(View view) {
        final String email = ((EditText)findViewById(R.id.et_email)).getText().toString();
        final String password = ((EditText)findViewById(R.id.et_password)).getText().toString();

        if (email.isEmpty() |! Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            Toast.makeText(this, "Please enter a valid email!", Toast.LENGTH_SHORT).show();
            return;
        }
        if (password.isEmpty()){
            Toast.makeText(this, "Please enter a password", Toast.LENGTH_SHORT).show();
            return;
        }
        //Logs the user in using an async task to ensure the UI thread isn't blocked
        new AsyncTask<String,Void,Integer>(){
            @Override
            protected Integer doInBackground(String... strings) {
                return DatabaseHandler.login(email,password);
            }

            @Override
            protected void onPostExecute(Integer integer) {
                if (integer==-1){
                    Toast.makeText(LoginActivity.this, "Wrong username or password", Toast.LENGTH_SHORT).show();
                    return;
                }
                startActivity(new Intent(LoginActivity.this,NavigationActivity.class).putExtra("userid",integer));
                finish();
            }
        }.execute();
    }
}
