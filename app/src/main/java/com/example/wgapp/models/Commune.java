package com.example.wgapp.models;

import java.util.ArrayList;
import java.util.List;

public class Commune {

    private List<Roommate> roommates = new ArrayList<Roommate>();
    private List<CoEvent> coEvents = new ArrayList<CoEvent>();

    public List<Roommate> getRoommates() {
        return roommates;
    }

    public List<CoEvent> getCoEvents() {
        return coEvents;
    }

    public  Commune(){

    }

}
