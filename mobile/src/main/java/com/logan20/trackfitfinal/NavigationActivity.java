package com.logan20.trackfitfinal;

import android.os.AsyncTask;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;
import com.logan20.trackfitcommon.DatabaseHandler;


public class NavigationActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{
    private static int userid;
    private NavigationView navigationView;
    private FrameLayout fl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("TrackFit");
        setSupportActionBar(toolbar);
        fl = ((FrameLayout)findViewById(R.id.fl_content));
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
    }


    private void setEmailPanel() {
        final TextView panel = (TextView) navigationView.findViewById(R.id.email_panel);
        new AsyncTask<Void,Void,String>(){
            @Override
            protected String doInBackground(Void... voids) {
                return (DatabaseHandler.getEmailFromID(userid));
            }

            @Override
            protected void onPostExecute(String s) {
                panel.setText(s);
            }
        }.execute();

    }

    private void setNamePanel() {
        final TextView namePanel = (TextView) navigationView.findViewById(R.id.name_panel);
        new AsyncTask<Void,Void,String>(){
            @Override
            protected String doInBackground(Void... voids) {
                return (DatabaseHandler.getNameFromID(userid));
            }

            @Override
            protected void onPostExecute(String s) {
                namePanel.setText(s);
            }
        }.execute();

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
    public boolean onNavigationItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.nav_my_home:

                break;
            case R.id.nav_my_exercises:

                break;
            case R.id.nav_my_profile:

                break;

            case R.id.nav_my_history:

                break;

            case R.id.nav_my_heartrate:
                getSupportFragmentManager().beginTransaction().replace(R.id.fl_content,new ViewHeartrateFragment()).commit();
                break;
            case R.id.nav_settings:

                break;
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

}
