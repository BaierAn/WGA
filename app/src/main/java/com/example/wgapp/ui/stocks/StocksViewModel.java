package com.example.wgapp.ui.stocks;

import android.os.Handler;
import android.util.Pair;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;


import com.example.wgapp.MainActivity;
import com.example.wgapp.models.CoEvent;
import com.example.wgapp.models.CoEventTypes;
import com.example.wgapp.models.Commune;
import com.example.wgapp.models.Roommate;
import com.example.wgapp.models.Stock;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

public class StocksViewModel extends ViewModel {



    private MutableLiveData<List<Pair<String,String>>> stocksList;
    public LiveData<List<Pair<String,String>>> getStocksList() {
        if (stocksList == null) {
            stocksList = new MutableLiveData<List<Pair<String,String>>>();
            loadStocks();
        }
        return stocksList;
    }
    private void loadStocks() {
        // do async operation to fetch users
        Handler myHandler = new Handler();
        myHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                ArrayList<Pair<String,String>> stocksStringList = new ArrayList<Pair<String,String>>();

                Commune c =  MainActivity.getCommune();
                Roommate a =  MainActivity.getLocalUser();
                for (CoEvent event : MainActivity.getCommune().getCoEvents()) {

                    if(event.getType().equals(CoEventTypes.STOCK)){
                        Stock stock = new Gson().fromJson(event.getData(), Stock.class);
                        if(stock != null){
                            stocksStringList.add(new Pair<String, String>("Name: " +stock.getStockName() +"|| Roommate"+ stock.getUserName()   +"|| Amount: " + stock.getTotalAmount() + "|| Cost: " +stock.getTotalCost(),event.getData()));
                        }
                    }
                }
                stocksList.setValue(stocksStringList);
            }
        }, 5);
    }
    public StocksViewModel() {
    }

}