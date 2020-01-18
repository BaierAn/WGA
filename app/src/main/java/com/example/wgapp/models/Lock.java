package com.example.wgapp.models;

import java.util.Calendar;
import java.util.Date;

public class Lock {

    private String description;
    private Date startDate;
    private int completionTime;
    private String ResID;

    public Lock(){}

    public boolean isExpired() {
        Calendar cal  = Calendar.getInstance();
        cal.setTime(startDate);
        cal.add(Calendar.MINUTE, completionTime);
        if(cal.getTime().compareTo(new Date()) > 0){
            return false;
        }else{
            return true;
        }


}

    public String getResID() {
        return ResID;
    }

    public void setResID(String resID) {
        ResID = resID;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public void setCompletionTime(int completionTime) {
        this.completionTime = completionTime;
    }

    public String getDescription() {
        return description;
    }

    public Date getStartDate() {
        return startDate;
    }

    public int getCompletionTime() {
        return completionTime;
    }

    public Lock(String description, Date startDate, int completionTime, String id) {
        this.description = description;
        this.startDate = startDate;
        this.completionTime = completionTime;
        this.ResID = id;
    }
}

