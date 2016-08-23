package com.example.khasol.jobflow;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

/**
 * Created by Khasol on 8/9/2016.
 */
public class Search extends AppCompatActivity {
    android.support.v7.app.ActionBar mActionBar;
    public  static  boolean check = false;
    EditText name,location;
    Button btn_search;
    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.searchjob);
        init();
    }

    void init(){

        mActionBar = getSupportActionBar();
        mActionBar.setDisplayShowHomeEnabled(false);
        mActionBar.setDisplayShowTitleEnabled(false);
        mActionBar.setDisplayShowCustomEnabled(true);
        mActionBar.setDisplayShowTitleEnabled(false);
        LayoutInflater mInflater = LayoutInflater.from(this);
        View mCustomView = mInflater.inflate(R.layout.customize_action_bar, null);
        ActionBar.LayoutParams layoutParams = new ActionBar.LayoutParams(ActionBar.LayoutParams.MATCH_PARENT,
                ActionBar.LayoutParams.MATCH_PARENT);
        mActionBar.setCustomView(mCustomView,layoutParams);
        Toolbar parent = (Toolbar) mCustomView.getParent();
        parent.setContentInsetsAbsolute(0, 0);

        ImageView image_home = (ImageView) mCustomView.findViewById(R.id.home);
        image_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(Search.this,ShowAlljobs_WithoutLogin.class);
                startActivity(intent);
            }
        });

       /* ImageView image_search = (ImageView) mCustomView.findViewById(R.id.search);
        image_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(Search.this,Search.class);
                startActivity(intent);
            }
        });*/

        name = (EditText) findViewById(R.id.name_search);
        location = (EditText) findViewById(R.id.location_search);
        btn_search = (Button) findViewById(R.id.btn_search);
        btn_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                check = true;
                String job_name = name.getText().toString();
                String user_location = location.getText().toString();
                Intent intent = new Intent(Search.this,JobSearch_Result.class);
                intent.putExtra("job_name",job_name);
                intent.putExtra("user_location",user_location);
                startActivity(intent);
            }
        });





    }
}
