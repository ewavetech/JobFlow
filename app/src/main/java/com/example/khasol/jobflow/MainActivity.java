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
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
        DrawerLayout drawer;

    View mCustomView;
    ImageView profile_img;
    NavigationView navigationView;

    private List<CustomeDataAdapter> jobs_list = new ArrayList<>();
    List<String> list = new ArrayList();
    private RecyclerView recyclerView;
    private JobsAdapter mAdapter;
    RelativeLayout relativeLayout;
    LinearLayoutManager mLayoutManager;
    View test1View;
   android.support.v7.app.ActionBar mActionBar;
List<String> name_list = new ArrayList<>();
    CustomeDataAdapter movie;
    private  boolean check;
    Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_ACTION_BAR_OVERLAY);
        setContentView(R.layout.activity_main);

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

        mAdapter = new JobsAdapter(jobs_list);
        mLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);

        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getApplicationContext(), recyclerView, new ClickListener() {
            @Override
            public void onClick(View view, int position) {
                CustomeDataAdapter movie = jobs_list.get(position);
                Toast.makeText(getApplicationContext(), movie.getTitle() + " is selected!", Toast.LENGTH_SHORT).show();
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
                Log.d("current",""+currentFirstVisibleItem);
                if (dy >2) {

                    toolbar.setVisibility(View.GONE);
                } else if (dy <-5) {
                    //toolbar.animate().translationY(toolbar.getHeight()).setInterpolator(new AccelerateInterpolator()).start();

                    toolbar.setVisibility(View.VISIBLE);
                }
            }
        });
        prepareMovieData();

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
        if (id == R.id.profile) {
            Toast.makeText(MainActivity.this, "click", Toast.LENGTH_LONG).show();
            if (drawer.isDrawerOpen(Gravity.RIGHT)) {
                drawer.closeDrawer(Gravity.RIGHT);

            } else {
                drawer.openDrawer(Gravity.RIGHT);

                profile_img = (ImageView) findViewById(R.id.user_img);

                profile_img.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Toast.makeText(MainActivity.this, "Image click now", Toast.LENGTH_SHORT).show();
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

    private void prepareMovieData() {

      if (Search.check == true) {
          name_list.add("Android developer");
          name_list.add("PHP developer");
          name_list.add(".NET developer");
          name_list.add("IOS developer");
          List<String> lowe_case_name = new ArrayList<>();
          for (int i = 0; i<name_list.size();i++ ){

              lowe_case_name.add(name_list.get(i).toLowerCase().toString());
          }

String job_name = getIntent().getExtras().getString("job_name");
          if (name_list.get(0).contains(job_name)){

          }
          for (int i = 0; i < name_list.size(); i++) {
              String lower_case = name_list.get(i).toLowerCase().toString();
              if (name_list.get(i).contains(job_name) || lowe_case_name.get(i).contains(job_name)) {

                      movie = new CustomeDataAdapter(name_list.get(i).toString(), "Full time", "25 days", "stockholm","","");
                  jobs_list.add(movie);
                  check = true;
              }
          }
          if (check == false){

              Toast.makeText(MainActivity.this, "No jobs exist", Toast.LENGTH_LONG).show();
              Intent intent = new Intent(MainActivity.this, FirstScreen.class);
              startActivity(intent);

          }
      }

        else{
          movie = new CustomeDataAdapter("Android developer", "Full time", "25 days", "stockholm","","");
          jobs_list.add(movie);
          movie = new CustomeDataAdapter("PHP developer", "Full time", "25 days", "stockholm","","");
          jobs_list.add(movie);
          movie = new CustomeDataAdapter("IOS developer", "Full time", "25 days", "stockholm","","");
          jobs_list.add(movie);
          movie = new CustomeDataAdapter(".Net developer", "Full time", "25 days", "stockholm","","");
          jobs_list.add(movie);


      }
        mAdapter.notifyDataSetChanged();
    }


    public interface ClickListener {
        void onClick(View view, int position);

        void onLongClick(View view, int position);
    }

    public static class RecyclerTouchListener implements RecyclerView.OnItemTouchListener {

        private GestureDetector gestureDetector;
        private MainActivity.ClickListener clickListener;

        public RecyclerTouchListener(Context context, final RecyclerView recyclerView, final MainActivity.ClickListener clickListener) {
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
