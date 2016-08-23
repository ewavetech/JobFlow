package com.example.khasol.jobflow;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import webservice_control.AllJobs_Webservices;

/**
 * Created by Khasol on 8/9/2016.
 */
public class ShowAlljobs_WithoutLogin extends AppCompatActivity {

    private List<CustomeDataAdapter> customeDataAdapters = new ArrayList<>();
    List<String> list = new ArrayList();
    private RecyclerView recyclerView;
    private JobsAdapter mAdapter;
    CustomeDataAdapter jobs;
    RelativeLayout relativeLayout;
    LinearLayoutManager mLayoutManager;
    View test1View;
    android.support.v7.app.ActionBar mActionBar;
    View mCustomView;
    Toolbar parent;
    public static Boolean check = false;
    AllJobs_Webservices allJobs_webservices;
    String Tag = "exception";
    List<String> job_name ;
    List<String> days ;
    List<String> job_type ;
    List<String> location;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.without_loginjobs);
        allJobs_webservices = new AllJobs_Webservices();
        init();


        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        mAdapter = new JobsAdapter(customeDataAdapters);
        mLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);


        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getApplicationContext(), recyclerView, new ClickListener() {
            @Override
            public void onClick(View view, int position) {

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
                // currentFirstVisibleItem = mLayoutManager.findFirstVisibleItemPosition();
                if (dy > 5) {
                    //  parent.setVisibility(View.GONE);
                    mActionBar.hide();


                } else if (dy < -5) {
                    // parent.setVisibility(View.VISIBLE);
                    mActionBar.show();
                    //mActionBar.setVisibility(View.VISIBLE);
                }
            }

        });
      //  prepareMovieData();
        new control_alljobs_services().execute();

    }

    void init() {
        mActionBar = getSupportActionBar();
        mActionBar.setDisplayShowTitleEnabled(false);
        mActionBar.setDisplayShowHomeEnabled(false);
        mActionBar.setDisplayShowCustomEnabled(true);
        mActionBar.setDisplayShowTitleEnabled(false);
        LayoutInflater mInflater = LayoutInflater.from(this);
        mCustomView = mInflater.inflate(R.layout.customize_action_bar_logout, null);
        ActionBar.LayoutParams layoutParams = new ActionBar.LayoutParams(ActionBar.LayoutParams.MATCH_PARENT,
                ActionBar.LayoutParams.MATCH_PARENT);
        mActionBar.setCustomView(mCustomView, layoutParams);
        parent = (Toolbar) mCustomView.getParent();
        parent.setContentInsetsAbsolute(0, 0);

        ImageView image_home = (ImageView) mCustomView.findViewById(R.id.back);
        image_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //   Intent intent = new Intent(ShowAlljobs_WithoutLogin.this,ShowAlljobs_WithoutLogin.class);
                // startActivity(intent);
            }
        });

        job_name = new ArrayList<>();
        job_type = new ArrayList<>();
        location = new ArrayList<>();
        days = new ArrayList<>();

    }

 /*   private void prepareMovieData() {
        jobs = new CustomeDataAdapter("Android developer", "Full time", "25 days", "stockholm");
        customeDataAdapters.add(jobs);
        jobs = new CustomeDataAdapter("PHP developer", "Full time", "25 days", "stockholm");
        customeDataAdapters.add(jobs);
        jobs = new CustomeDataAdapter(".NET developer", "Part time", "25 days", "stockholm");
        customeDataAdapters.add(jobs);
        jobs = new CustomeDataAdapter("IOS developer", "Full time", "25 days", "stockholm");
        customeDataAdapters.add(jobs);
        jobs = new CustomeDataAdapter("IOS developer", "Full time", "25 days", "stockholm");
        customeDataAdapters.add(jobs);


        mAdapter.notifyDataSetChanged();
    }*/


    public interface ClickListener {
        void onClick(View view, int position);

        void onLongClick(View view, int position);
    }

    public static class RecyclerTouchListener implements RecyclerView.OnItemTouchListener {

        private GestureDetector gestureDetector;
        private ClickListener clickListener;

        public RecyclerTouchListener(Context context, final RecyclerView recyclerView, final ShowAlljobs_WithoutLogin.ClickListener clickListener) {

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

    class control_alljobs_services extends AsyncTask<Void, Void, Void> {
        String obj;


        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }

        @Override
        protected Void doInBackground(Void... voids) {

            try {
                obj = allJobs_webservices.get_jobs();

                JSONObject jsonObject = new JSONObject(obj);
                JSONArray jsonArray = jsonObject.getJSONArray("jobs");
                for (int i = 0; i < jsonArray.length(); i++) {

                    JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                    job_name.add(jsonObject1.getString("JobTitle").toString());
                    job_type.add(jsonObject1.getString("JobType").toString());
                    location.add(jsonObject1.getString("Address").toString());
                    days.add(jsonObject1.getString("Days").toString());


                }

                Log.i(Tag, obj.toString());
            } catch (UnsupportedEncodingException e) {
                Log.i(Tag, e.getMessage().toString());
            } catch (JSONException e) {
                Log.i(Tag, e.getMessage().toString());
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            if (check == false) {
                if (job_name.size()>0){
                    for (int i = 0; i<job_name.size();i++){
                        jobs = new CustomeDataAdapter(job_name.get(i).toString(), job_type.get(i).toString(), days.get(i).toString(),location.get(i).toLowerCase(),"","");
                        customeDataAdapters.add(jobs);

                    }
                    mAdapter.notifyDataSetChanged();
                }

            } else {


            }

        }
    }
}
