package com.example.wgapp.models;

import java.io.Serializable;
import java.util.Date;

public class CoEvent implements Serializable {

    private CoEventTypes type;
    private Date dateTime;
    private String data;

    public CoEvent(CoEventTypes type,  String data) {
        this.type = type;
        this.dateTime = new Date();
        this.data = data;
    }

    public CoEvent(){

    }

    public void setDateTime(Date dateTime) {
        this.dateTime = dateTime;
    }

    public CoEventTypes getType() {
        return type;
    }

    public Date getDateTime() {
        return dateTime;
    }

    public String getData() {
        return data;
    }

    public void setType(CoEventTypes type) {
        this.type = type;
    }


    public void setData(String data) {
        this.data = data;
    }
}

