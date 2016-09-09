package com.example.khasol.jobflow;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONObject;

import java.io.UnsupportedEncodingException;

import webservice_control.SignUp_Webservices;

/**
 * Created by Khasol on 8/18/2016.
 */
public class SignUp extends Activity {

    public static String URL = "http://khasol.com/jobflow/myphpservices/";
    public static boolean check = false;
    SignUp_Webservices signUp_webservices;
    EditText user_password,repeat_password,email,first_name,last_name,phone_number;
    String Tag = "exception";
    Button btn_signup;
    ConnectionDetector connectionDetector;
    Boolean isInternetPresent = false;
    String res;
    SessionManager sessionManager;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup);

        sessionManager = new SessionManager(SignUp.this);
        signUp_webservices = new SignUp_Webservices();
        connectionDetector = new ConnectionDetector(SignUp.this);
        init();


        btn_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isInternetPresent = connectionDetector.isConnectingToInternet();
                if (isInternetPresent) {
                    if (first_name.getText().equals("") || first_name.getText().length() == 0) {
                        Toast.makeText(SignUp.this, "Please enter first name", Toast.LENGTH_SHORT).show();


                    } else if (user_password.getText().equals("") || user_password.getText().length() == 0) {
                        Toast.makeText(SignUp.this, "Please enter password", Toast.LENGTH_SHORT).show();

                    }
                    else if (repeat_password.getText().equals("") || repeat_password.getText().length() == 0) {
                        Toast.makeText(SignUp.this, "Please enter repeat password", Toast.LENGTH_SHORT).show();

                    }

                    else if (last_name.getText().equals("") || last_name.getText().length() == 0) {
                        Toast.makeText(SignUp.this, "Please enter last name", Toast.LENGTH_SHORT).show();

                    }
                    else if (email.getText().equals("") || email.getText().length() == 0) {
                        Toast.makeText(SignUp.this, "Please enter email id", Toast.LENGTH_SHORT).show();

                    }

                    else if (phone_number.getText().equals("") ||phone_number.getText().length() == 0) {
                        Toast.makeText(SignUp.this, "Please enter phone number", Toast.LENGTH_SHORT).show();

                    }
                    else {

                        new control_login_services().execute();

                    }
                } else {

                    Toast.makeText(SignUp.this, "Please check your internet or network", Toast.LENGTH_SHORT).show();
                    ;
                }
            }

        });

        action();
    }
    void init() {


        first_name = (EditText) findViewById(R.id.first_name);
        last_name = (EditText) findViewById(R.id.last_name);
        email = (EditText) findViewById(R.id.user_email);
        phone_number = (EditText) findViewById(R.id.phone_number);
        repeat_password = (EditText) findViewById(R.id.repeat_password);
        user_password = (EditText) findViewById(R.id.user_password);
        btn_signup = (Button) findViewById(R.id.btn_signup);
        progressDialog = new ProgressDialog(SignUp.this);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setTitle("JobFLow");
        progressDialog.setMessage("Please wait!");

    }

    private void action() {


    }

    class control_login_services extends AsyncTask<Void, Void, Void> {
        String obj;
        String f_name,l_name,email_get,phone, password,r_password;
        JSONObject jsonObject;
        String _name;
        String _id;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            f_name = first_name.getText().toString();
            l_name = last_name.getText().toString();
            email_get = email.getText().toString();
            phone = phone_number.getText().toString();
            password = user_password.getText().toString();
            r_password = repeat_password.getText().toString();
            progressDialog.show();
        }
        @Override
        protected Void doInBackground(Void... voids) {
            try {
                obj = signUp_webservices.Send_data(f_name,l_name,email_get,phone, password,r_password);
                Log.i("sign",obj.toString());
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            progressDialog.cancel();
            if (check == false) {
           Log.i("infoe",obj.toString());
                res = obj.toString();
                if (res.contains("1")) {
                    Toast.makeText(SignUp.this, "Please verify your email address", Toast.LENGTH_LONG).show();
                    sessionManager.createLoginSession(_id, _name, password);
                    Intent intent = new Intent(SignUp.this, Login.class);
                    startActivity(intent);
                    finish();
                }
                else if (res.equals("2")) {
                    Toast.makeText(SignUp.this, "This email id is already exist. please chose different one", Toast.LENGTH_LONG).show();


                }
            } else {
                Toast.makeText(SignUp.this, "Please check your network or service", Toast.LENGTH_SHORT).show();
            }

        }
    }
}
