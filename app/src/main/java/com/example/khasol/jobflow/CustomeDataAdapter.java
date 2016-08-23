package com.example.khasol.jobflow;

/**
 * Created by Khasol on 7/29/2016.
 */
public class CustomeDataAdapter {

    private String job_title, job_type,days,locaion,compnay_name,job_id;

    public  CustomeDataAdapter(){


    }
    public CustomeDataAdapter(String job_title,String job_type,String days,String location,String compnay_name,String job_id){

        this.job_title = job_title;
        this.job_type = job_type;
        this.days = days;
        this.locaion = location;
        this.compnay_name = compnay_name;
        this.job_id = job_id;
    }

    public String getJob_id(){

        return job_id;
    }
    public void setJob_id(String job_id){

        this.job_id = job_id;
    }

    public String getCompnay_name(){

        return compnay_name;
    }
    public void setCompnay_name(String compnay_name){

        this.compnay_name = compnay_name;
    }


    public String getTitle(){

        return job_title;
    }
    public void setTitle(String name){

        this.job_title = name;
    }

    public String getType(){

        return job_type;
    }
    public void setType(String job_type){

        this.job_title = job_type;
    }

    public String getLocation(){

        return locaion;
    }
    public void setLocaion(String location){

        this.job_title = location;
    }


    public String getDays(){

        return days;
    }
    public void setDays(String days){

        this.job_title = days;
    }

}
