package com.example.khasol.jobflow;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.widget.RelativeLayout;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Khasol on 8/10/2016.
 */
public class SaveJob extends android.support.v4.app.Fragment {
    private List<CustomeDataAdapter> jobs_lis = new ArrayList<>();
    List<String> list = new ArrayList();
    private SavedJob_Adapter mAdapter;
    RelativeLayout relativeLayout;
    LinearLayoutManager mLayoutManager;
    CustomeDataAdapter customeDataAdapter;
    RecyclerView recyclerView;
    Animation animation;
    private boolean check = false;

    public SaveJob() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //inflater is replace with custom layout
     //   return super.onCreateView(R.layout.savejob, container, savedInstanceState);
        if (container == null){

            return null;
        }
        View view = inflater.inflate(R.layout.savejob,container,false);
        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        mAdapter = new SavedJob_Adapter(jobs_lis);
        mLayoutManager = new LinearLayoutManager(ControlViewPager.activity);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);

        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(ControlViewPager.activity, recyclerView, new ClickListener() {
            @Override
            public void onClick(View view, int position) {
                CustomeDataAdapter movie = jobs_lis.get(position);
                Toast.makeText(ControlViewPager.activity, movie.getTitle() + " is selected!", Toast.LENGTH_SHORT).show();
            }


            @Override
            public void onLongClick(View view, int position) {

            }
        }));

        this.recyclerView.setOnScrollListener(new RecyclerView.OnScrollListener() {
            boolean scroll_down;
            int mLastFirstVisibleItem = -1;
            int currentFirstVisibleItem = 0;

            int firstVisibleItem = ((LinearLayoutManager) recyclerView.getLayoutManager()).findFirstVisibleItemPosition();

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {


                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                //currentFirstVisibleItem = mLayoutManager.findFirstVisibleItemPosition();
                if (dy > 2) {
                        ControlViewPager.mActionBar.hide();

                } else if (dy < -5) {
                        ControlViewPager.mActionBar.show();

                }
            }

        });
        preParedDate();


        return view;
    }


    private void preParedDate() {
        jobs_lis.clear();
        customeDataAdapter = new CustomeDataAdapter("Android developer", "Full time", "25 days", "stockholm","","");
        jobs_lis.add(customeDataAdapter);
        customeDataAdapter = new CustomeDataAdapter("PHP developer", "Full time", "25 days", "stockholm","","");
        jobs_lis.add(customeDataAdapter);
        customeDataAdapter = new CustomeDataAdapter("IOS developer", "Full time", "25 days", "stockholm","","");
        jobs_lis.add(customeDataAdapter);
        customeDataAdapter = new CustomeDataAdapter(".Net developer", "Full time", "25 days", "stockholm","","");
        jobs_lis.add(customeDataAdapter);

        mAdapter.notifyDataSetChanged();
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

}
