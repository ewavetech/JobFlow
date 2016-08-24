package com.example.khasol.jobflow;

import android.app.ProgressDialog;
import android.content.Context;

/**
 * Created by Khasol on 8/24/2016.
 */
public class ControlDialog {
    ProgressDialog progressDialog;
    public void start(Context context){

        progressDialog = new ProgressDialog(context);
        progressDialog.setTitle("JobFlow");
        progressDialog.setMessage("Please wait!");
        progressDialog.show();
    }
public void stop(){
    progressDialog.cancel();

}

}
