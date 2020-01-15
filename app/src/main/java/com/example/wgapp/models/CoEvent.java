package com.example.wgapp.models;

import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

public class CoEvent implements Serializable {

    private CoEventTypes type;
    private Date dateTime;
    private String data;
    private String Barcode = "";
    private String ID;

    public CoEvent(CoEventTypes type,  String data) {
        this.type = type;
        this.dateTime = new Date();
        this.data = data;
        this.ID =  UUID.randomUUID().toString() ;
    }

    public CoEvent(){

    }

    public String getID() {
        return ID;
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

    public void setBarcode(String barcode) {
        Barcode = barcode;
    }

    public String getBarcode() {
        return Barcode;
    }

    public void setType(CoEventTypes type) {
        this.type = type;
    }


    public void setData(String data) {
        this.data = data;
    }
}

