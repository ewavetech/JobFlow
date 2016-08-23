package com.example.khasol.jobflow;

/**
 * Created by Khasol on 7/29/2016.
 */
public class CustomeDataAdapter_Firstacreen {

    private String text_view;
    Integer imageId;

    public CustomeDataAdapter_Firstacreen(){


    }
    public CustomeDataAdapter_Firstacreen(String text_view,Integer imageId) {

        this.text_view = text_view;
        this.imageId = imageId;
    }

    public String getTitle(){

        return text_view;
    }
    public void setTitle(String name){

        this.text_view = name;
    }

    public Integer getimageId(){

        return imageId;
    }
    public void setimageId(String job_type){

        this.imageId = imageId;
    }

}
