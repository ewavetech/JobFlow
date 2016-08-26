package com.example.khasol.jobflow;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;

import webservice_control.Login_Webservices;

/**
 * Created by Khasol on 8/18/2016.
 */
public class Login extends Activity {

    public static String URL = "http://khasol.com/jobflow/myphpservices/";
    public static boolean check = false;
    Login_Webservices login_webservices;
    EditText user_name, user_password;
    String Tag = "exception";
    Button btn_login;
    ConnectionDetector connectionDetector;
    Boolean isInternetPresent = false;
    String res;
    SessionManager sessionManager;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        sessionManager = new SessionManager(Login.this);
        login_webservices = new Login_Webservices();
        connectionDetector = new ConnectionDetector(Login.this);
        init();


        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isInternetPresent = connectionDetector.isConnectingToInternet();
                if (isInternetPresent) {
                    if (user_name.getText().equals("") || user_name.getText().length() == 0) {
                        Toast.makeText(Login.this, "Please enter user name", Toast.LENGTH_SHORT).show();


                    } else if (user_password.getText().equals("") || user_password.getText().length() == 0) {
                        Toast.makeText(Login.this, "Please enter password", Toast.LENGTH_SHORT).show();

                    } else {

                        new control_login_services().execute();

                    }
                } else {

                    Toast.makeText(Login.this, "Please check your internet or network", Toast.LENGTH_SHORT).show();
                    ;
                }
            }

        });

        action();
    }

    void init() {


        user_name = (EditText) findViewById(R.id.user_name);
        user_password = (EditText) findViewById(R.id.user_password);
        btn_login = (Button) findViewById(R.id.btn_login);
        progressDialog = new ProgressDialog(Login.this);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setTitle("JobFLow");
        progressDialog.setMessage("Please wait!");

    }

    private void action() {


    }

    class control_login_services extends AsyncTask<Void, Void, Void> {
        String obj;
        String name, password;
        JSONObject jsonObject;
        String _name;
        String _id;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            name = user_name.getText().toString();
            password = user_password.getText().toString();
            progressDialog.show();
        }

        @Override
        protected Void doInBackground(Void... voids) {


            try {
                obj = login_webservices.Send_data(name, password);
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            progressDialog.hide();
            if (check == false) {

                res = obj.toString();
                if (res.equals("0")) {
                    Toast.makeText(Login.this, "user name and password does not exist", Toast.LENGTH_SHORT).show();
                } else if (res.equals("2")) {
                    Toast.makeText(Login.this, "Please verify your email id", Toast.LENGTH_SHORT).show();
                } else {

                    if (res.length() > 1) {
                        try {
                            JSONObject jsonObject = new JSONObject(obj);
                            JSONArray jsonArray = jsonObject.getJSONArray("login");
                            JSONObject jsonObject1 = jsonArray.getJSONObject(0);
                            _name = jsonObject1.getString("FirstName").toString();
                            _id = jsonObject1.getString("UserId").toString();

                        } catch (Exception e) {
                            e.printStackTrace();

                        }
                    }
                      sessionManager.createLoginSession(_id, _name, password);
                     Intent intent = new Intent(Login.this, ControlViewPager.class);
                    startActivity(intent);
                }
            } else {
                Toast.makeText(Login.this, "Please check your network or service", Toast.LENGTH_SHORT).show();
            }

        }
    }
}
