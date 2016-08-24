package com.example.khasol.jobflow;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import webservice_control.AppliedJob_Webservices;

/**
 * Created by Khasol on 8/10/2016.
 */
public class AppliedJob extends android.support.v4.app.Fragment {

    private List<CustomeDataAdapter> jobs_list = new ArrayList<>();
    List<String> list = new ArrayList();
    private JobsAdapter mAdapter;
    LinearLayoutManager mLayoutManager;
    CustomeDataAdapter customeDataAdapter;
    RecyclerView recyclerView;
    private boolean check = false;
    String Tag = "exception";
    public static List<String> job_name;
    public static List<String> days;
    public static List<String> job_type;
    public static List<String> location;
    public static List<String> job_id;
    public static List<String> company_name;
    ProgressDialog progressDialog;
    ConnectionDetector connectionDetector;
    private boolean isInternetPresent;
    AppliedJob_Webservices appliedJob_webservices;
    SessionManager sessionManager;
    String user_id;

    public AppliedJob() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (container == null) {

            return null;
        }
        View view = inflater.inflate(R.layout.applied, container, false);

        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        mAdapter = new JobsAdapter(jobs_list);
        mLayoutManager = new LinearLayoutManager(ControlViewPager.activity);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);

        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(ControlViewPager.activity, recyclerView, new ClickListener() {
            @Override
            public void onClick(View view, int position) {
                CustomeDataAdapter movie = jobs_list.get(position);
                Toast.makeText(ControlViewPager.activity, movie.getTitle() + " is selected!", Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onLongClick(View view, int position) {

            }
        }));

        //


        this.recyclerView.setOnScrollListener(new RecyclerView.OnScrollListener() {
            boolean scroll_down;
            int mLastFirstVisibleItem = -1;
            int currentFirstVisibleItem = 0;

            int firstVisibleItem = ((LinearLayoutManager) recyclerView.getLayoutManager()).findFirstVisibleItemPosition();


            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                Log.d("current", "" + currentFirstVisibleItem);


                if (dy > 5) {
                    ControlViewPager.toolbar.setVisibility(View.GONE);


                } else if (dy < -5) {
                    ControlViewPager.toolbar.setVisibility(View.VISIBLE);
                }
            }

        });
        //init();
        return view;
    }

    void init() {

        job_name = new ArrayList<>();
        job_type = new ArrayList<>();
        location = new ArrayList<>();
        days = new ArrayList<>();
        job_id = new ArrayList<>();
        company_name = new ArrayList<>();
        appliedJob_webservices = new AppliedJob_Webservices();
       sessionManager = new SessionManager(ControlViewPager.activity);
        progressDialog = new ProgressDialog(ControlViewPager.activity);
        progressDialog.setTitle("JobFlow");
        progressDialog.setMessage("Please wait!s");
        connectionDetector = new ConnectionDetector(ControlViewPager.activity);
        isInternetPresent = connectionDetector.isConnectingToInternet();
        if (isInternetPresent) {
            new control_appliedjob_services().execute();

        } else {

            Toast.makeText(ControlViewPager.activity, "Please check your internet or network", Toast.LENGTH_SHORT).show();
        }
    }

    public interface ClickListener {
        void onClick(View view, int position);

        void onLongClick(View view, int position);
    }

    public static class RecyclerTouchListener implements RecyclerView.OnItemTouchListener {

        private GestureDetector gestureDetector;
        // ControlViewPager.activity.ClickListener clickListener;
        ClickListener clickListener;

        public RecyclerTouchListener(Context context, final RecyclerView recyclerView, final ClickListener clickListener) {
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


    class control_appliedjob_services extends AsyncTask<Void, Void, Void> {
        String obj;
String res;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            jobs_list.clear();
            job_name.clear();
            HashMap<String, String> user = sessionManager.getUserDetails();
            user_id = user.get(SessionManager.KEY_USER_ID);
            progressDialog.show();

        }

        @Override
        protected Void doInBackground(Void... voids) {

            try {
                obj = appliedJob_webservices.get_jobs(user_id);
res = obj.toString();
    Log.i("res",res);
if (res!="0") {

    JSONObject jsonObject = new JSONObject(obj);
    JSONArray jsonArray = jsonObject.getJSONArray("all_applied_job");
    for (int i = 0; i < jsonArray.length(); i++) {

        JSONObject jsonObject1 = jsonArray.getJSONObject(i);
        job_name.add(jsonObject1.getString("JobTitle").toString());
        job_type.add(jsonObject1.getString("JobType").toString());
        location.add(jsonObject1.getString("Address").toString());
        days.add(jsonObject1.getString("Days").toString());
        job_id.add(jsonObject1.getString("JobId").toString());
        company_name.add(jsonObject1.getString("CompanyName").toString());
    }
    }
                Log.i(Tag, obj.toString());
            } catch (UnsupportedEncodingException e) {
                Log.i(Tag, e.getMessage().toString());
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
                    if (res.equals("0")) {
                        job_name.clear();
                        jobs_list.clear();
                        Toast.makeText(ControlViewPager.activity, "No job exist", Toast.LENGTH_SHORT).show();
                    }
                    else {
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
                    Toast.makeText(ControlViewPager.activity, "Check your network or server", Toast.LENGTH_SHORT).show();

                }

        }
    }
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            // Fetch data or something...
           init();
//            Toast.makeText(getActivity(),"run apply",Toast.LENGTH_SHORT).show();

        }
    }
}
