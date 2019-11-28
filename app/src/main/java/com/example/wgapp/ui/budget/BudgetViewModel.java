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

import com.example.wgapp.models.CoEvent;
import com.example.wgapp.models.CoEventTypes;
import com.example.wgapp.models.Commune;
import com.example.wgapp.models.Roommate;
import com.example.wgapp.models.Stock;
import com.google.firebase.auth.FirebaseAuth;
import com.google.gson.Gson;

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
                ArrayList<String> budgetStringList = new ArrayList<String>();
                for (CoEvent event : commune.getCoEvents()) {
                    budgetStringList.addAll(CalculateBudget( event));
                }
                budgetList.setValue(budgetStringList);
            }
        }, 5000);

    }


    private  ArrayList<String> CalculateBudget(CoEvent event){
        ArrayList<String> budgetList = new ArrayList<>();
        switch(event.getType()){

            case STOCK:
                budgetList.addAll(CalculateStock(new Gson().fromJson(event.getData(), Stock.class)));
                break;

            case RESOURCE:
                CalculateResources();
                break;

            default:
                throw new IllegalStateException("Unexpected value: " + event.getType());
        }
        return budgetList;
    }


//todo change to budget object
    private ArrayList<String> CalculateStock(Stock stock){
        ArrayList<String> budgetList = new ArrayList<>();

        switch(stock.getStockType()){
            case SHARE:
                float fragment  = stock.getTotalCost() / com.getRoommates().size();
                float costValue = 0 ;
                if(stock.getRommmateId().equals(FirebaseAuth.getInstance().getCurrentUser().getUid())){
                    for (Roommate mate  : com.getRoommates()) {
                        if(!stock.getRommmateId().equals(FirebaseAuth.getInstance().getCurrentUser().getUid())){

                            budgetList.add("get from " +mate.name + " : +" + fragment +"€");
                        }
                    }
                }else{
                    budgetList.add("owe to " + stock.getRommmateId() + " : -" + fragment +"€");

                }

                break;
            case SINGLEUSE:
                //todo implement singleuse
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + stock.getStockType());
        }

    }
    private void CalculateResources(){

    }


    public BudgetViewModel() {
    }




}