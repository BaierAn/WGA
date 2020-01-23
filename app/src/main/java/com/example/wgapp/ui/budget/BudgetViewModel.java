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




    public void loadBudget() {
        // do async operation to fetch users
                ArrayList<Pair<String,String>> budgetStringList = new ArrayList<Pair<String,String>>();
                    budgetStringList.addAll(CalculateBudget());
                budgetList.setValue(budgetStringList);
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
                            String s = rawStock.first;
                            if(!s.contains("✓")){
                                budgetList.add(new Pair<String, String>(rawStock.first + " ✓" , rawStock.second));
                                budgetList.remove(rawStock);
                            }

                            break;
                        }
                        }
                    break;

                case RESOURCE:
                    CalculateResources();
                    break;

                default:
                    break;
            }
        }
        return budgetList;
    }


    private ArrayList<Pair<String,String>> CalculateStock(Stock stock, String stockRaw) {
        ArrayList<Pair<String, String>> budgetList = new ArrayList<>();
        float fragment = 0;
        switch (stock.getStockType()) {
            case SHARE:
                fragment = stock.getTotalCost() / MainActivity.getCommune().getRoommates().size();
                for (Roommate mate : MainActivity.getCommune().getRoommates()) {

                    if(!mate.getId().equals(MainActivity.getLocalUser().getId())){
                        budgetList.add(createView(stock, mate, fragment , stockRaw));
                    }
                }
                break;
            case TOOKSINGLE:
                fragment = stock.getTotalCost() / stock.getTotalAmount();
                Roommate mate2 = new Roommate();
                for (Roommate mate : MainActivity.getCommune().getRoommates()) {
                    if (mate.getId().equals(stock.getRommmateId())) {
                        mate2 = mate;
                    }
                }
                if(!MainActivity.getLocalUser().getId().equals(stock.getRommmateId())){

                    budgetList.add(createView(stock, mate2, fragment, stockRaw));

                }

                break;
            default:
                break;
        }
        //todo fix logical error you dont get money from others by single use maybe pay yourself ?
        //if i created the stock

            for (Roommate mate : MainActivity.getCommune().getRoommates()) {


            }

            return budgetList;
    }

    private Pair<String,String> createView (Stock stock, Roommate mate, float fragment, String stockRaw) {
        if (stock.getRommmateId().equals(MainActivity.getLocalUser().getId())) {
            return new Pair<String, String>("Erhalte von " + mate.getDisplayName() + " " + fragment + "€\nStock: " + stock.getStockName(), stockRaw);
        } else {
            //if someone else created the stock
            return new Pair<String, String>("Zahle an " + stock.getUserName() + " " + fragment + "€\nStock: " + stock.getStockName(), stockRaw);
        }
    }

    private void CalculateResources(){

    }


    public BudgetViewModel() {
    }




}