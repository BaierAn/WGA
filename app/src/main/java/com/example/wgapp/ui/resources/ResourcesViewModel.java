package com.example.wgapp.ui.resources;

import android.os.Handler;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class ResourcesViewModel extends ViewModel {

    private MutableLiveData<List<String>> resourcesList;
    public LiveData<List<String>> getResourcesList() {
        if (resourcesList == null) {
            resourcesList = new MutableLiveData<List<String>>();
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
                ArrayList<String> fruitsStringList = new ArrayList<String>();
                fruitsStringList.add("Mango");
                fruitsStringList.add("Apple");
                fruitsStringList.add("Orange");
                fruitsStringList.add("Banana");
                fruitsStringList.add("Grapes");
                long seed = System.nanoTime();
                Collections.shuffle(fruitsStringList, new Random(seed));

                resourcesList.setValue(fruitsStringList);
            }
        }, 5000);

    }

    public ResourcesViewModel() {

    }

}