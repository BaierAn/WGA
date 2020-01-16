package com.example.wgapp.ui.budget;

import android.util.Pair;
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
import com.example.wgapp.models.StockCreationTypes;
import com.google.firebase.auth.FirebaseAuth;
import com.google.gson.Gson;

public class BudgetViewModel extends ViewModel {


    private MutableLiveData <List<Pair<String,String>>> budgetList;
    public LiveData<List<Pair<String,String>>> getBudgetList() {
        if (budgetList == null) {
            budgetList = new MutableLiveData<List<Pair<String,String>>>();
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
                ArrayList<Pair<String,String>> budgetStringList = new ArrayList<Pair<String,String>>();
                    budgetStringList.addAll(CalculateBudget());
                budgetList.setValue(budgetStringList);
            }
        }, 5);

    }


    private  ArrayList<Pair<String,String>> CalculateBudget(){
        ArrayList<Pair<String,String>> budgetList = new ArrayList<>();
        for (CoEvent event : MainActivity.getCommune().getCoEvents()) {
            switch(event.getType()){
                case STOCK:
                    budgetList.addAll(CalculateStock(new Gson().fromJson(event.getData(), Stock.class),event.getData() ));
                    break;
                case PAID:
                    for (Pair<String, String> rawStock : budgetList) {
                        if(event.getData().equals(rawStock.second)){
                            //todo alter ui
                            budgetList.add(new Pair<String, String>(rawStock.first + " || PAID", rawStock.second));
                            budgetList.remove(rawStock);
                            break;
                        }
                        }
                    break;

                case RESOURCE:
                    CalculateResources();
                    break;

                default:
                    throw new IllegalStateException("Unexpected value: " + event.getType());
            }
        }
        return budgetList;
    }


    private ArrayList<Pair<String,String>> CalculateStock(Stock stock, String stockRaw){
        ArrayList<Pair<String,String>> budgetList = new ArrayList<>();
        float fragment = 0;
        switch(stock.getStockType()){
            case SHARE:
                fragment  = stock.getTotalCost() / MainActivity.getCommune().getRoommates().size();
                break;
            case TOOKSINGLE:
                fragment  = stock.getTotalCost() / stock.getTotalAmount();
                break;
            default:
                break;
        }
        //todo fix logical error you dont get money from others by single use maybe pay yourself ?
        //if i created the stock
        if(stock.getRommmateId().equals(MainActivity.getLocalUser().getId())){

            for (Roommate mate  : MainActivity.getCommune().getRoommates()) {
                if(stock.getStockType() == StockCreationTypes.SHARE){
                    if(!stock.getRommmateId().equals(mate.getId())){
                        budgetList.add(new Pair<String, String>("get from " +mate.getDisplayName() + " : +" + fragment +"€", stockRaw));
                    }
                }else {
                    if(stock.getRommmateId().equals(mate.getId())){
                        budgetList.add(new Pair<String, String>("get from " +mate.getDisplayName() + " : +" + fragment +"€", stockRaw));
                    }
                }
            }
        }else{
            //if someone else created the stock
            budgetList.add(new Pair<String, String>("owe to " + stock.getUserName() + " : -" + fragment +"€", stockRaw));
        }
        return budgetList;
    }
    private void CalculateResources(){

    }


    public BudgetViewModel() {
    }




}