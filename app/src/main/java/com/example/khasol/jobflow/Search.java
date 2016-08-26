package com.example.khasol.jobflow;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;

/**
 * Created by Khasol on 8/9/2016.
 */
public class Search extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    android.support.v7.app.ActionBar mActionBar;
    public static boolean check = false;
    EditText name, location;
    Button btn_search;

    DrawerLayout drawer;
    Toolbar toolbar;
    ImageView profile_img;
    NavigationView navigationView;
    SessionManager sessionManager;
    TextView txt_name, txt_login, txt_signup, txt_feedback, txt_help, txt_invitefriend, txt_setting, txt_profile;
    LinearLayout searh_top_login;
    LinearLayout search_top_profile;
    ImageView home, profile;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_main);
        init();
        sessionManager = new SessionManager(Search.this);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle(null);
        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
        toggle.setDrawerIndicatorEnabled(false);
        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        right_navigation();
    }

    void init() {
        name = (EditText) findViewById(R.id.name_search);
        location = (EditText) findViewById(R.id.location_search);
        btn_search = (Button) findViewById(R.id.btn_search);
        btn_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                check = true;
                String job_name = name.getText().toString();
                String user_location = location.getText().toString();
                Intent intent = new Intent(Search.this, JobSearch_Result.class);
                intent.putExtra("job_name", job_name);
                intent.putExtra("user_location", user_location);
                startActivity(intent);
            }
        });

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.END)) {
            drawer.closeDrawer(GravityCompat.END);
        } else {
            super.onBackPressed();
        }
    }

    void right_navigation() {

        home = (ImageView) findViewById(R.id.search_home);
        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(Search.this, "Not available yet ", Toast.LENGTH_SHORT).show();
            }
        });
        profile = (ImageView) findViewById(R.id.search_profile);
        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (drawer.isDrawerOpen(Gravity.RIGHT)) {
                    drawer.closeDrawer(Gravity.RIGHT);

                } else {
                    drawer.openDrawer(Gravity.RIGHT);
                    TextView user_name = (TextView) findViewById(R.id.search_txt_user_name);
                    search_top_profile = (LinearLayout) findViewById(R.id.search_top_profile);
                    searh_top_login = (LinearLayout) findViewById(R.id.search_top_login);
                    if (sessionManager.isLoggedIn()) {
                        HashMap<String, String> user = sessionManager.getUserDetails();
                        String name = user.get(SessionManager.USER_NAME);
                        searh_top_login.setVisibility(View.GONE);
                        search_top_profile.setVisibility(View.VISIBLE);
                        user_name.setText(name);


                    } else {

                        searh_top_login.setVisibility(View.VISIBLE);
                        search_top_profile.setVisibility(View.GONE);
                        user_name.setText("JobFlow");

                        txt_login = (TextView) findViewById(R.id.search_txt_login);
                        txt_login.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Intent intent = new Intent(Search.this, Login.class);
                                startActivity(intent);
                                drawer.closeDrawer(Gravity.RIGHT);
                                //  finish();

                            }
                        });
                        txt_signup = (TextView) findViewById(R.id.search_txt_signup);
                        txt_signup.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Toast.makeText(Search.this, "Not available yet ", Toast.LENGTH_SHORT).show();
                            }
                        });

                    }
                }

            }
        });

    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.END);
        return true;
    }
}
