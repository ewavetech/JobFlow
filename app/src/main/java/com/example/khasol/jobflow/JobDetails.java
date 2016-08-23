package com.example.khasol.jobflow;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;

import webservice_control.UpplyJob_Webservices;

/**
 * Created by Khasol on 8/22/2016.
 */
public class JobDetails extends Activity {

    TextView job_name,company_name,job_type,phone,address,about_company;
    Button btn_applay;
    UpplyJob_Webservices upplyJob_webservices;
    int  at_position;
    String res;
    public  static  boolean check = false;
    private boolean isPresent = false;
    ConnectionDetector cd;
    SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.jobdetails);
        upplyJob_webservices = new UpplyJob_Webservices();
        cd = new ConnectionDetector(JobDetails.this);
        sessionManager = new SessionManager(JobDetails.this);
        init();
        put_value();

    }
    void init(){
job_name = (TextView) findViewById(R.id.job_name);
        company_name = (TextView) findViewById(R.id.companyname);
        job_type = (TextView) findViewById(R.id.jobtype_jobdetails);
        phone = (TextView) findViewById(R.id.phone);
        address = (TextView) findViewById(R.id.address);
        about_company = (TextView) findViewById(R.id.aboutcompany);
        btn_applay = (Button) findViewById(R.id.btn_apply);
        btn_applay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                isPresent = cd.isConnectingToInternet();
                if (isPresent) {

                    new control_login_services().execute();

                }
                else{

                    Toast.makeText(JobDetails.this,"Please check your internet connection",Toast.LENGTH_LONG).show();
                }
            }
        });

    }
    void put_value(){

        String position = getIntent().getStringExtra("position");
          at_position = Integer.parseInt(position);
        company_name.setText(Jobs.company_name.get(at_position));
        job_type.setText(Jobs.job_type.get(at_position));
        job_name.setText(Jobs.job_name.get(at_position));
        phone.setText("0234");
        address.setText("abc");
    }
    class control_login_services extends AsyncTask<Void, Void, Void> {
        String obj;
        String user_id, job_id;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            HashMap<String, String> user = sessionManager.getUserDetails();
            user_id = user.get(SessionManager.KEY_USER_ID);
            job_id = Jobs.job_id.get(at_position);
        }

        @Override
        protected Void doInBackground(Void... voids) {

            try {
                obj = upplyJob_webservices.Send_data(user_id, job_id);
                res = obj.toString();
                Log.i("info", res.toString());
            } catch (UnsupportedEncodingException e) {
                Log.i("info",e.getMessage().toString());
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            if (check == false) {
                if (res.equals("0")) {
                    Toast.makeText(JobDetails.this, "user name and password does not exist", Toast.LENGTH_SHORT).show();
                }
                else if(res.contains("1")){
                    Toast.makeText(JobDetails.this, "You have applied successfully", Toast.LENGTH_SHORT).show();
                }
            }
            else{

                Toast.makeText(JobDetails.this,"Please check your network or service",Toast.LENGTH_SHORT).show();
            }

        }
    }

}
