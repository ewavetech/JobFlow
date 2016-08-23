package com.example.khasol.jobflow;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
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
import java.util.List;

import webservice_control.AllJobs_Webservices;

/**
 * Created by Khasol on 8/10/2016.
 */
public class Jobs extends android.support.v4.app.Fragment {
    private List<CustomeDataAdapter> job_list = new ArrayList<>();
    List<String> list = new ArrayList();
    private JobsAdapter mAdapter;
    LinearLayoutManager mLayoutManager;
    CustomeDataAdapter jobs;;
    RecyclerView recyclerView;
    private boolean check = false;
    AllJobs_Webservices allJobs_webservices;
    String Tag = "exception";
   public static List<String> job_name ;
    public static  List<String> days ;
    public static List<String> job_type ;
    public static   List<String> location;
    public static  List<String> job_id;
    public static List<String> company_name;

    ConnectionDetector connectionDetector;
    Boolean isInternetPresent;
    ProgressDialog progressDialog;
    public Jobs() {
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
        mAdapter = new JobsAdapter(job_list);
        View view = inflater.inflate(R.layout.jobs, container, false);
        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
              mLayoutManager = new LinearLayoutManager(ControlViewPager.activity);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);

        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(ControlViewPager.activity, recyclerView, new ClickListener() {
            @Override
            public void onClick(View view, int position) {
                int at_position = position;
                String con_position = String.valueOf(at_position);
                Intent intent = new Intent(ControlViewPager.activity,JobDetails.class);
                intent.putExtra("position",con_position);
                startActivity(intent);
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
                if (dy > 5) {

                        ControlViewPager.toolbar.setVisibility(View.GONE);

                } else if (dy < -5) {
                    ControlViewPager.toolbar.setVisibility(View.VISIBLE);

                }
            }

        });
        allJobs_webservices = new AllJobs_Webservices();
        job_name = new ArrayList<>();
        job_type = new ArrayList<>();
        location = new ArrayList<>();
        days = new ArrayList<>();
        job_id = new ArrayList<>();
        company_name = new ArrayList<>();
         progressDialog  = new ProgressDialog(getActivity());
        progressDialog.setTitle("JobFlow");
        progressDialog.setMessage("Please wait!s");
        connectionDetector = new ConnectionDetector(getActivity());
        isInternetPresent = connectionDetector.isConnectingToInternet();
        if (isInternetPresent){
            new control_alljobs_services().execute();

        }
        else{

            Toast.makeText(getActivity(),"Please check your internet or network",Toast.LENGTH_SHORT).show();
        }

        return view;
    }


    public interface ClickListener {
        void onClick(View view, int position);

        void onLongClick(View view, int position);
    }

    public static class RecyclerTouchListener implements RecyclerView.OnItemTouchListener {

        private GestureDetector gestureDetector;
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


    class control_alljobs_services extends AsyncTask<Void, Void, Void> {
        String obj;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            job_list.clear();
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
            progressDialog.hide();
            if (check == false) {
                if (job_name.size()>0){
                    for (int i = 0; i<job_name.size();i++){
                        jobs = new CustomeDataAdapter(job_name.get(i).toString(), job_type.get(i).toString(), days.get(i).toString(),location.get(i).toLowerCase(),
                                company_name.get(i).toString(),job_id.get(i).toString() );
                        job_list.add(jobs);

                    }
                    mAdapter.notifyDataSetChanged();
                }

            } else {
                Toast.makeText(getActivity(),"Check your network or server",Toast.LENGTH_SHORT).show();

            }

        }
    }
}
