package com.example.wgapp.ui.resources;

import android.os.Handler;
import android.util.Pair;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.wgapp.MainActivity;
import com.example.wgapp.models.CoEvent;
import com.example.wgapp.models.CoEventTypes;
import com.example.wgapp.models.Commune;
import com.example.wgapp.models.Resource;
import com.example.wgapp.models.Roommate;
import com.example.wgapp.models.Stock;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class ResourcesViewModel extends ViewModel {

    private MutableLiveData<List<Pair<String,String>>> resourcesList;
    public LiveData<List<Pair<String,String>>> getResourcesList() {
        if (resourcesList == null) {
            resourcesList = new MutableLiveData<List<Pair<String,String>>>();
            loadResources();
        }
        return resourcesList;
    }


    private void loadResources() {
        // do async operation to fetch users

        Handler myHandler = new Handler();
        myHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                ArrayList<Pair<String,String>> ResStringList = new ArrayList<Pair<String,String>>();

                for (CoEvent event : MainActivity.getCommune().getCoEvents()) {

                    if(event.getType().equals(CoEventTypes.RESOURCE)){
                        Resource res = new Gson().fromJson(event.getData(), Resource.class);
                        if(res != null){
                            ResStringList.add(new Pair<String, String>("Name: " + res.getResName() + " || Locked: "+res.isLocked(), event.getData()));
                        }
                    }
                }
                resourcesList.setValue(ResStringList);
            }
        }, 1);

    }

    public ResourcesViewModel() {

    }

}