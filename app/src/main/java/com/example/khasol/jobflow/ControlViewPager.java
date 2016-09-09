package com.example.khasol.jobflow;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Khasol on 8/10/2016.
 */
public class ControlViewPager extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    ViewPager viewPager;
    private PagerAdapter myPagerAdapter;
    //4772254
    public static TabLayout tabLayout;
    public static android.support.v7.app.ActionBar mActionBar;
    public static Activity activity;
    public static Toolbar toolbar;
    public static Context context;
    public static TextView show_jobs;
    ProgressDialog progressDialog;
    DrawerLayout drawer;
    String result;
    View mCustomView;
    ImageView profile_img;
    NavigationView navigationView;
    int po = 0;
    SessionManager sessionManager;

    ConnectionDetector connectionDetector;
    String user_id;
    public static boolean check = false;
    MyClass myClass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.viewpager_with_navigationdrawer);
        activity = this;
        context = this;
        sessionManager = new SessionManager(this);
        init();
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
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

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


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.profile) {
            LinearLayout profile, login, logout_panel;
            profile = (LinearLayout) findViewById(R.id.top_profile);
            login = (LinearLayout) findViewById(R.id.top_login);
            logout_panel = (LinearLayout) findViewById(R.id.logout_panel);
            TextView logout = (TextView) findViewById(R.id.txt_logout);
            TextView user_name = (TextView) findViewById(R.id.jobseeker_name);
            if (drawer.isDrawerOpen(Gravity.RIGHT)) {
                drawer.closeDrawer(Gravity.RIGHT);

            } else {
                drawer.openDrawer(Gravity.RIGHT);
                if (sessionManager.isLoggedIn()) {
                    login.setVisibility(View.GONE);
                    profile.setVisibility(View.VISIBLE);
                    logout_panel.setVisibility(View.VISIBLE);
                    TextView cv_upload = (TextView) findViewById(R.id.cv_upload);
                    HashMap<String, String> user = sessionManager.getUserDetails();
                    String name = user.get(SessionManager.USER_NAME);
                    user_name.setText(name);
                    logout.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            sessionManager.logout();
                            Intent intent = new Intent(ControlViewPager.this, Login.class);
                            startActivity(intent);
                        }
                    });
                    cv_upload.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Intent intent = new Intent(ControlViewPager.this, UploadCv.class);
                            startActivityForResult(intent, 1);
                        }
                    });
                } else {
                    logout_panel.setVisibility(View.GONE);
                    profile.setVisibility(View.GONE);
                    login.setVisibility(View.VISIBLE);
                    user_name.setText("JobFlow");
                }


                profile_img = (ImageView) findViewById(R.id.user_img);

                profile_img.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Toast.makeText(ControlViewPager.this, "Image click now", Toast.LENGTH_SHORT).show();
                    }
                });
            }
            return true;
        }

        return super.onOptionsItemSelected(item);
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

    void init() {
        show_jobs = (TextView) findViewById(R.id.applied_show_job);
        viewPager = (ViewPager) findViewById(R.id.viewpager);
        setupViewPager(viewPager);
        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
        sessionManager = new SessionManager(ControlViewPager.activity);
        progressDialog = new ProgressDialog(ControlViewPager.activity);
        progressDialog.setTitle("JobFlow");
        progressDialog.setMessage("Please wait!s");

    }


    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new AppliedJob(), "Applied");
        adapter.addFragment(new SaveJob(), "Saved");
        adapter.addFragment(new Jobs(), "Jobs");
        viewPager.setAdapter(adapter);
    }


    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {

            super(manager);
        }


        @Override
        public Fragment getItem(int position) {

            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {

            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == 1) {
            if (resultCode == Activity.RESULT_OK) {
                result = data.getStringExtra("path");
                if (result.equals(null)) {
                    Toast.makeText(ControlViewPager.this, "File not exist", Toast.LENGTH_SHORT).show();
                } else {
                    connectionDetector = new ConnectionDetector(ControlViewPager.activity);
                    boolean isInternetPresent = connectionDetector.isConnectingToInternet();
                    if (isInternetPresent) {
                        new control_cv_services().execute();

                    } else {

                        Toast.makeText(ControlViewPager.activity, "Please check your internet or network", Toast.LENGTH_SHORT).show();
                    }
                }
            }
            if (resultCode == Activity.RESULT_CANCELED) {
                //Write your code if there's no result
            }
        }
    }//onActi

    class control_cv_services extends AsyncTask<Void, Void, Void> {
        int obj;
        String res;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            HashMap<String, String> user = sessionManager.getUserDetails();
            user_id = user.get(SessionManager.KEY_USER_ID);
            // Log.i("id", "" + user_id);
            progressDialog.show();
            myClass = new MyClass();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            obj = myClass.uploadFile(result, user_id);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            progressDialog.cancel();


            if (check == false) {
                // service running
                if (MyClass.response_uploadCV.contains("success")) {
                    Toast.makeText(ControlViewPager.this, "CV has been uploaded successfully ", Toast.LENGTH_LONG).show();
                } else if (MyClass.response_uploadCV.contains("fail")) {
                    Toast.makeText(ControlViewPager.this, "CV can't uploaded successfully ", Toast.LENGTH_LONG).show();
                }
            } else {
                //service not running
                Toast.makeText(ControlViewPager.this, "Please check your network and server", Toast.LENGTH_LONG).show();
            }
        }

    }


}
