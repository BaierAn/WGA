package com.example.wgapp.models;

import com.example.wgapp.MainActivity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Resource {
    private String resName;
    private String currentUser;
    List<Lock> lockList;

    public Resource(String resName) {
        this.resName = resName;
        this.currentUser = MainActivity.getLocalUser().getDisplayName();
        this.lockList = new ArrayList<Lock>() {
        };
    }


    public boolean isLocked(){
        for (Lock lock: lockList) {
            if(!lock.isExpired()){
                return true;
            }

        }
        return false;
    }

    public void addLock(Lock lock){
        lockList.add(lock);
    }


    public Resource() {

    }

    public String getResName() {
        return resName;
    }

    public void setResName(String resName) {
        this.resName = resName;
    }

    public String getCurrentUser() {
        return currentUser;
    }

    public void setCurrentUser(String currentUser) {
        this.currentUser = currentUser;
    }


    public List<Lock> getLockList() {
        return lockList;
    }

    public void setLockList(List<Lock> lockList) {
        this.lockList = lockList;
    }
}
