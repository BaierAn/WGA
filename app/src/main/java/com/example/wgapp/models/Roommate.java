package com.example.wgapp.models;

import com.google.firebase.auth.FirebaseUser;

import java.net.CookieHandler;

public class Roommate {

    private int budget;
    private String displayName;
    private String id;
    private String CommuneID;
   // private FirebaseUser userData;

    public Roommate(int budget,FirebaseUser user) {
        this.budget = budget;
        //this.userData = user;
        this.displayName = user.getDisplayName();
        this.id = user.getUid();
        this.CommuneID = "None";
    }

    public Roommate() {
    }

    public int getBudget() {
        return budget;
    }

    public String getDisplayName() {
        return displayName;
    }

    public String getId() {
        return id;
    }
    public String getCommuneID() {
        return CommuneID;
    }

    public void setCommuneID(String CommuneID) {
        this.CommuneID = CommuneID;
    }

   // public FirebaseUser getUserData() {return userData;}

    public void setBudget(int budget) {
        this.budget = budget;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }
}
