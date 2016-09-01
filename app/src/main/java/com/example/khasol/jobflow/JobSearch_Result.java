package com.example.khasol.jobflow;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import webservice_control.AllJobs_Webservices;

public class JobSearch_Result extends AppCompatActivity {

    private List<CustomeDataAdapter> jobs_list = new ArrayList<>();
    List<String> list = new ArrayList();
    private RecyclerView recyclerView;
    private JobsAdapter mAdapter;
    LinearLayoutManager mLayoutManager;
    ActionBar mActionBar;
    List<String> name_list = new ArrayList<>();
    CustomeDataAdapter customeDataAdapter;
    private boolean check = false;
    public static List<String> job_name;
    public static List<String> days;
    public static List<String> job_type;
    public static List<String> location;
    public static List<String> job_id;
    public static List<String> company_name;
    ConnectionDetector connectionDetector;
    Boolean isInternetPresent;
    ProgressDialog progressDialog;
    AllJobs_Webservices allJobs_webservices;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.without_loginjobs);
        init();
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        mAdapter = new JobsAdapter(jobs_list);
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
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                // currentFirstVisibleItem = mLayoutManager.findFirstVisibleItemPosition();
                if (dy > 2) {


                } else if (dy < -5) {

                }
            }
        });
        new control_alljobs_services().execute();
    }

    void init() {
        allJobs_webservices = new AllJobs_Webservices();
        job_name = new ArrayList<>();
        job_type = new ArrayList<>();
        location = new ArrayList<>();
        days = new ArrayList<>();
        job_id = new ArrayList<>();
        company_name = new ArrayList<>();
        progressDialog = new ProgressDialog(JobSearch_Result.this);
        progressDialog.setTitle("JobFlow");
        progressDialog.setMessage("Please wait!s");
        ImageView back = (ImageView) findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(JobSearch_Result.this, Search.class);
                startActivity(intent);
            }
        });


    }

    public interface ClickListener {
        void onClick(View view, int position);

        void onLongClick(View view, int position);
    }

    public static class RecyclerTouchListener implements RecyclerView.OnItemTouchListener {

        private GestureDetector gestureDetector;
        private JobSearch_Result.ClickListener clickListener;

        public RecyclerTouchListener(Context context, final RecyclerView recyclerView, final JobSearch_Result.ClickListener clickListener) {
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
            jobs_list.clear();
           progressDialog.show();


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
                    job_id.add(jsonObject1.getString("JobId").toString());
                    company_name.add(jsonObject1.getString("CompanyName").toString());

                }

                Log.i("jobs", obj.toString());
            } catch (UnsupportedEncodingException e) {
               e.printStackTrace();
            } catch (JSONException e) {
            e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            progressDialog.cancel();
            if (check == false) {

                if (Search.check == true) {
                    Search.check = false;
                    List<String> lowe_case_name = new ArrayList<>();
                    for (int i = 0; i < job_name.size(); i++) {
                        lowe_case_name.add(job_name.get(i).toLowerCase().toString());
                    }
                    String job_search_name = getIntent().getExtras().getString("job_name");
                    Log.i("check_job", "" + job_name.size());
                    for (int i = 0; i < job_name.size(); i++) {

                        if (job_name.get(i).contains(job_search_name) || lowe_case_name.get(i).contains(job_search_name)) {

                            customeDataAdapter = new CustomeDataAdapter(job_name.get(i).toString(), job_type.get(i).toString(), days.get(i).toString(),
                                    location.get(i).toString(), "", "");
                            jobs_list.add(customeDataAdapter);

                            check = true;
                        }
                    }
                    if (check == false) {

                        Toast.makeText(JobSearch_Result.this, "No jobs exist", Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(JobSearch_Result.this, Search.class);
                        startActivity(intent);
                    }
                    mAdapter.notifyDataSetChanged();
                } else {

                    if (job_name.size() > 0) {
                        for (int i = 0; i < job_name.size(); i++) {
                            customeDataAdapter = new CustomeDataAdapter(job_name.get(i).toString(), job_type.get(i).toString(), days.get(i).toString(), location.get(i).toLowerCase(),
                                    company_name.get(i).toString(), job_id.get(i).toString());
                            jobs_list.add(customeDataAdapter);

                        }
                        mAdapter.notifyDataSetChanged();
                    }
                }
            } else {
                Toast.makeText(JobSearch_Result.this, "Check your network or server", Toast.LENGTH_SHORT).show();

            }

        }

    }
}
