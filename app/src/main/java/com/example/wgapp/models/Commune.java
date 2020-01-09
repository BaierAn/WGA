package com.example.wgapp.models;

import java.util.ArrayList;
import java.util.List;

public class Commune {

    private List<Roommate> roommates = new ArrayList<Roommate>();
    private List<CoEvent> coEvents = new ArrayList<CoEvent>();
    private String CommuneId;
    private String CommuneLink;
    private String CommuneName;

    public void setCommuneLink(String communeLink) {
        CommuneLink = communeLink;
    }

    public void setCommuneName(String communeName) {
        CommuneName = communeName;
    }

    public String getCommuneLink() {
        return CommuneLink;
    }

    public String getCommuneName() {
        return CommuneName;
    }

    public List<Roommate> getRoommates() {
        return roommates;
    }

    public List<CoEvent> getCoEvents() {
        return coEvents;
    }

    public void setCommuneId(String communeId) {
        CommuneId = communeId;
    }

    public String getCommuneId() {
        return CommuneId;
    }

    public void setRoommates(List<Roommate> roommates) {
        this.roommates = roommates;
    }

    public void setCoEvents(List<CoEvent> coEvents) {
        this.coEvents = coEvents;
    }

    public Roommate getRommate(int i){
        return roommates.get(i);
    }

    public void addRommate(Roommate r){
        roommates.add(r);
    }

    public CoEvent getCoEvent(int i){
        return coEvents.get(i);
    }

    public void addCoEvent(CoEvent e){
        coEvents.add(e);
    }

    public  Commune(){

    }

}
