package com.example.khasol.jobflow;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.GestureDetector;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Khasol on 7/29/2016.
 */
public class FirstScreen extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private List<CustomeDataAdapter_Firstacreen> firstscrren_list = new ArrayList<>();
    List<String> list = new ArrayList();
    private RecyclerView recyclerView;
    private FirstScreenAdapter mAdapter;
    RelativeLayout relativeLayout;
    LinearLayoutManager mLayoutManager;
    View test1View;
    android.support.v7.app.ActionBar mActionBar;
    CustomeDataAdapter_Firstacreen name;
    DrawerLayout drawer;
    Toolbar toolbar;
    ImageView profile_img;
    NavigationView navigationView;
    SessionManager sessionManager;
    TextView txt_name, txt_login, txt_signup, txt_feedback, txt_help, txt_invitefriend, txt_setting, txt_profile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().requestFeature(Window.FEATURE_ACTION_BAR_OVERLAY);
        sessionManager = new SessionManager(FirstScreen.this);
        if (sessionManager.isLoggedIn()) {
            setContentView(R.layout.activity_main);

            HashMap<String, String> user = sessionManager.getUserDetails();
            String name = user.get(SessionManager.USER_NAME);
            String id = user.get(SessionManager.KEY_USER_ID);
            String password = user.get(SessionManager.User_Password);
        } else {
            setContentView(R.layout.withoutlogin_activity_main);
        }
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


        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);

        mAdapter = new FirstScreenAdapter(firstscrren_list);
        mLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);

        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getApplicationContext(), recyclerView, new ClickListener() {
            @Override
            public void onClick(View view, int position) {
                name = firstscrren_list.get(position);
                Intent intent = new Intent(FirstScreen.this, ControlViewPager.class);
                startActivity(intent);

            }


            @Override
            public void onLongClick(View view, int position) {

            }
        }));


        this.recyclerView.setOnScrollListener(new RecyclerView.OnScrollListener() {
            boolean scroll_down;
            int mLastFirstVisibleItem = -1;
            int currentFirstVisibleItem = 0;


            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {

                super.onScrollStateChanged(recyclerView, newState);

            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                currentFirstVisibleItem = mLayoutManager.findFirstVisibleItemPosition();
                Log.d("current", "" + currentFirstVisibleItem);
                if (dy > 5) {
                    toolbar.setVisibility(View.GONE);
                } else if (dy < -5) {
                    toolbar.setVisibility(View.VISIBLE);
                }
            }

        });

        prepareFirstScreenData();
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

        //noinspection SimplifiableIfStatement
        if(id == R.id.search){
            Intent intent = new Intent(FirstScreen.this,Search.class);
            startActivity(intent);

        }
       else if(id == R.id.home){
            /*Intent intent = new Intent(FirstScreen.this,Search.class);
            startActivity(intent);
*/
        }
        else if(id == R.id.inbox){
            Intent intent = new Intent(FirstScreen.this,Search.class);
            startActivity(intent);


        }
       else if (id == R.id.profile) {
            if (drawer.isDrawerOpen(Gravity.RIGHT)) {
                drawer.closeDrawer(Gravity.RIGHT);

            } else {

                drawer.openDrawer(Gravity.RIGHT);
                TextView user_name = (TextView) findViewById(R.id.txt_user_name);

                if (sessionManager.isLoggedIn()) {
                    HashMap<String, String> user = sessionManager.getUserDetails();
                    String name = user.get(SessionManager.USER_NAME);

                    user_name.setText(name);


                } else {
                    user_name.setText("JobFlow");

                    txt_login = (TextView) findViewById(R.id.txt_login);
                    txt_login.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Intent intent = new Intent(FirstScreen.this, Login.class);
                            startActivity(intent);
                            drawer.closeDrawer(Gravity.RIGHT);
                            //  finish();

                        }
                    });
                    txt_signup = (TextView) findViewById(R.id.txt_sign_up);
                    txt_signup.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Toast.makeText(FirstScreen.this, "Not available yet ", Toast.LENGTH_SHORT).show();
                        }
                    });


                }
                profile_img = (ImageView) findViewById(R.id.user_img);

                profile_img.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Toast.makeText(FirstScreen.this, "Image click now", Toast.LENGTH_SHORT).show();
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


    private void prepareFirstScreenData() {
        CustomeDataAdapter_Firstacreen firstacreen = new CustomeDataAdapter_Firstacreen("Store", R.drawable.first_img);
        firstscrren_list.add(firstacreen);
        firstacreen = new CustomeDataAdapter_Firstacreen("Cafe", R.drawable.second_img);
        firstscrren_list.add(firstacreen);
        firstacreen = new CustomeDataAdapter_Firstacreen("Super market", R.drawable.third_img);
        firstscrren_list.add(firstacreen);


        mAdapter.notifyDataSetChanged();
    }

    public interface ClickListener {
        void onClick(View view, int position);

        void onLongClick(View view, int position);
    }

    public static class RecyclerTouchListener implements RecyclerView.OnItemTouchListener {

        private GestureDetector gestureDetector;
        private ClickListener clickListener;

        public RecyclerTouchListener(Context context, final RecyclerView recyclerView, final FirstScreen.ClickListener clickListener) {
            this.clickListener = clickListener;
            gestureDetector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener() {
                @Override
                public boolean onSingleTapUp(MotionEvent e) {
                    return true;
                }

                @Override
                public void onLongPress(MotionEvent e) {
                    View child = recyclerView.findChildViewUnder(e.getX(), e.getY());
                    if (child != null && clickListener != null) {
                        clickListener.onLongClick(child, recyclerView.getChildPosition(child));
                    }
                }
            });
        }


        @Override
        public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {

            View child = rv.findChildViewUnder(e.getX(), e.getY());
            if (child != null && clickListener != null && gestureDetector.onTouchEvent(e)) {
                clickListener.onClick(child, rv.getChildPosition(child));
            }
            return false;
        }

        @Override
        public void onTouchEvent(RecyclerView rv, MotionEvent e) {

        }

        @Override
        public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

        }
    }

}
