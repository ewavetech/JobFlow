package com.example.khasol.jobflow;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;

/**
 * Created by Khasol on 8/24/2016.
 */
public class Control_ProgressDialog extends Activity {
ProgressDialog progressDialog;
    ControlDialog controlDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        controlDialog = new ControlDialog();

    }
    public  void start_dialog(){
controlDialog.start(Control_ProgressDialog.this);


    }
    public  void stop_dialog(){
       controlDialog.stop();


    }

}
