package com.example.khasol.jobflow;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Khasol on 8/24/2016.
 */
public class Fake1 extends android.support.v4.app.Fragment  {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view  = inflater.inflate(R.layout.fake1,container,false);
        return view;
    }
}
