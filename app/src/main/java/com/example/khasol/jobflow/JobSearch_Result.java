package com.example.khasol.jobflow;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class JobSearch_Result extends AppCompatActivity {

    private List<CustomeDataAdapter> jobs_list = new ArrayList<>();
    List<String> list = new ArrayList();
    private RecyclerView recyclerView;
    private JobsAdapter mAdapter;
    RelativeLayout relativeLayout;
    LinearLayoutManager mLayoutManager;
    View test1View;
   ActionBar mActionBar;
List<String> name_list = new ArrayList<>();
    CustomeDataAdapter customeDataAdapter;
    private  boolean check;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.jobsearch_result);
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
                customeDataAdapter  = jobs_list.get(position);
                Toast.makeText(getApplicationContext(), customeDataAdapter.getTitle() + " is selected!", Toast.LENGTH_SHORT).show();
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
                if (dy >2) {
                    mActionBar.hide();


                } else if (dy <-5) {
                    mActionBar.show();
                }
            }
        });
        prepareMovieData();

    }
    void init(){

        mActionBar = getSupportActionBar();
        mActionBar.setDisplayShowTitleEnabled(false);
        mActionBar.setDisplayShowHomeEnabled(false);
        mActionBar.setDisplayShowCustomEnabled(true);
        mActionBar.setDisplayShowTitleEnabled(false);
        LayoutInflater mInflater = LayoutInflater.from(this);
        View mCustomView = mInflater.inflate(R.layout.customize_action_bar_logout, null);
        ActionBar.LayoutParams layoutParams = new ActionBar.LayoutParams(ActionBar.LayoutParams.MATCH_PARENT,
                ActionBar.LayoutParams.MATCH_PARENT);
        mActionBar.setCustomView(mCustomView,layoutParams);
        Toolbar parent = (Toolbar) mCustomView.getParent();
        parent.setContentInsetsAbsolute(0, 0);

        ImageView image_home = (ImageView) mCustomView.findViewById(R.id.back);
        image_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(JobSearch_Result.this,Search.class);
                startActivity(intent);
            }
        });


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

                  customeDataAdapter = new CustomeDataAdapter(name_list.get(i).toString(), "Full time", "25 days", "stockholm","","");
                  jobs_list.add(customeDataAdapter);
                  check = true;
              }
          }
          if (check == false){

              Toast.makeText(JobSearch_Result.this, "No jobs exist", Toast.LENGTH_LONG).show();
              Intent intent = new Intent(JobSearch_Result.this, FirstScreen.class);
              startActivity(intent);

          }
      }

        else{
          customeDataAdapter = new CustomeDataAdapter("Android developer", "Full time", "25 days", "stockholm","","");
          jobs_list.add(customeDataAdapter);
          customeDataAdapter = new CustomeDataAdapter("PHP developer", "Full time", "25 days", "stockholm","","");
          jobs_list.add(customeDataAdapter);
          customeDataAdapter = new CustomeDataAdapter("IOS developer", "Full time", "25 days", "stockholm","","");
          jobs_list.add(customeDataAdapter);
          customeDataAdapter = new CustomeDataAdapter(".Net developer", "Full time", "25 days", "stockholm","","");
          jobs_list.add(customeDataAdapter);


      }
        mAdapter.notifyDataSetChanged();
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
}
