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

import com.example.wgapp.MainActivity;
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
                for (CoEvent event : MainActivity.getCommune().getCoEvents()) {
                    budgetStringList.addAll(CalculateBudget( event));
                }
                budgetList.setValue(budgetStringList);
            }
        }, 5);

    }


    private  ArrayList<String> CalculateBudget(CoEvent event){
        ArrayList<String> budgetList = new ArrayList<>();
        switch(event.getType()){

            case STOCK:
                budgetList.addAll(CalculateStock(new Gson().fromJson(event.getData(), Stock.class)));
                break;
            case PAID:
                Stock s = new Gson().fromJson(event.getData(), Stock.class);
                //budgetList;
                // wenn id gleich
                // paid dazuschreiben
                //todo durchsuche
                break;

            case RESOURCE:
                CalculateResources();
                break;

            default:
                throw new IllegalStateException("Unexpected value: " + event.getType());
        }
        return budgetList;
    }


    private ArrayList<String> CalculateStock(Stock stock){
        ArrayList<String> budgetList = new ArrayList<>();
        float fragment = 0;
        switch(stock.getStockType()){
            case SHARE:
                fragment  = stock.getTotalCost() / MainActivity.getCommune().getRoommates().size();
                break;
            case TOOKSINGLE:
                fragment  = stock.getTotalCost() / stock.getTotalAmount();
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + stock.getStockType());
        }

        if(stock.getRommmateId().equals(MainActivity.getLocalUser().getId())){
            for (Roommate mate  : MainActivity.getCommune().getRoommates()) {
                if(stock.getRommmateId().equals(mate.getId())){
                }else{

                    budgetList.add("get from " +mate.getDisplayName() + " : +" + fragment +"€");
                }
            }
        }else{
            budgetList.add("owe to " + stock.getUserName() + " : -" + fragment +"€");

        }
        return budgetList;
    }
    private void CalculateResources(){

    }


    public BudgetViewModel() {
    }




}