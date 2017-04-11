package com.logan20.trackfitfinal;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import com.logan20.trackfitcommon.DatabaseHandler;


public class NavigationActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{
    private static int userid;
    private NavigationView navigationView;
    private String name;
    private String email;
    private HomeFragment homeFrag;
    private ExerciseFragment exFrag;
    private ProfileFragment proFrag;
    private HistoryFragment histFrag;
    private ViewHeartrateFragment currHRFrag;
    private SettingsFragment settFrag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("TrackFit");
        setSupportActionBar(toolbar);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close){
            @Override
            public void onDrawerOpened(View drawerView) {
                setNamePanel();
                setEmailPanel();
                super.onDrawerOpened(drawerView);
            }
        };
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        userid = getIntent().getIntExtra("userid",-1);
        loadFragments();
        load(homeFrag);
    }

    private void loadFragments() {
        homeFrag =new HomeFragment();
        homeFrag.setId(userid);

        exFrag = new ExerciseFragment();
        exFrag.setId(userid);

        proFrag = new ProfileFragment();
        proFrag.setId(userid);

        histFrag = new HistoryFragment();
        histFrag.setId(userid);

        currHRFrag = new ViewHeartrateFragment();

        settFrag = new SettingsFragment();
    }


    private void setEmailPanel() {
        final TextView panel = (TextView) navigationView.findViewById(R.id.email_panel);
        if (email==null){
            new AsyncTask<Void,Void,String>(){
                @Override
                protected String doInBackground(Void... voids) {
                    email= (DatabaseHandler.getEmailFromID(userid));
                    return email;
                }

                @Override
                protected void onPostExecute(String s) {
                    panel.setText(s);
                }
            }.execute();
        }
        else{
            panel.post(new Runnable() {
                @Override
                public void run() {
                    panel.setText(email);
                }
            });
        }


    }

    private void setNamePanel() {
        final TextView namePanel = (TextView) navigationView.findViewById(R.id.name_panel);
        //get the user's name if it isn't fetched
        if (name==null){
            new AsyncTask<Void,Void,String>(){
                @Override
                protected String doInBackground(Void... voids) {
                    name = (DatabaseHandler.getNameFromID(userid));
                    return name;
                }

                @Override
                protected void onPostExecute(String s) {
                    namePanel.setText(s);
                }
            }.execute();
        }
        else{
            namePanel.post(new Runnable() {
                @Override
                public void run() {
                    namePanel.setText(name);
                }
            });
        }

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.nav_my_home:
                load(homeFrag);
                break;
            case R.id.nav_my_exercises:
                load(exFrag);
                break;
            case R.id.nav_my_profile:
                load(proFrag);
                break;
            case R.id.nav_my_history:
                load(histFrag);
                break;
            case R.id.nav_my_heartrate:
                load(currHRFrag);
                break;
            case R.id.nav_settings:
                load(settFrag);
                break;
            case R.id.nav_logout:
                logout();
                break;
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void logout() {
        new AlertDialog.Builder(this)
                .setTitle("Logout?")
                .setMessage("Are you sure you want to logout?")
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        startActivity(new Intent(NavigationActivity.this,LoginActivity.class));
                        finish();
                    }
                })
                .setNegativeButton(android.R.string.no,null)
                .setCancelable(false)
                .show();
    }

    private void load(Fragment frag) {
        getSupportFragmentManager().beginTransaction().replace(R.id.fl_content,frag).commit();
    }

    public void loadHome() {
        load(homeFrag);
    }

    @Override
    protected void onPause() {
        super.onPause();
        finish();
    }

    public void loadExerciseList() {
        load(exFrag);
    }
}
