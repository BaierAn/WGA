package com.example.wgapp.ui.budget;

import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import android.os.Handler;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class BudgetViewModel extends ViewModel {



    private MutableLiveData<List<String>> budgetList;
    public LiveData<List<String>> getBudgetList() {
        if (budgetList == null) {
            budgetList = new MutableLiveData<List<String>>();
            loadBudget();
        }
        return budgetList;
    }


    private void loadBudget() {
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

                budgetList.setValue(fruitsStringList);
            }
        }, 5000);

    }


    public BudgetViewModel() {
    }




}