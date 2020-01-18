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
    public void loadStocks() {
        // do async operation to fetch users

                ArrayList<Pair<String,String>> stocksStringList = new ArrayList<Pair<String,String>>();

                Commune c =  MainActivity.getCommune();
                Roommate a =  MainActivity.getLocalUser();
                for (CoEvent event : MainActivity.getCommune().getCoEvents()) {


                    if(event.getType().equals(CoEventTypes.STOCK)){
                        Stock stock = new Gson().fromJson(event.getData(), Stock.class);
                        if(stock != null){
                        switch(stock.getStockType()) {
                            case SHARE:
                                stocksStringList.add(new Pair<String, String>("Name: " +stock.getStockName() +"\nErsteller: "+ stock.getUserName()   +"\nAnzahl: " + stock.getTotalAmount() + "\nKosten: " +stock.getTotalCost()+ "\nTyp: " +stock.getStockType() ,event.getData()));
                                break;
                            case SINGLEUSE:
                                CoEvent tempEvent = event;
                                Stock tempStock = stock;

                                for (CoEvent event2 : MainActivity.getCommune().getCoEvents()) {
                                    Stock stock2 = new Gson().fromJson(event2.getData(), Stock.class);
                                    if(stock2 != null && stock.getID().equals(stock2.getID())){
                                        if(event2.getDateTime().compareTo(event.getDateTime()) > 0){
                                            tempEvent = event2;
                                            stock2.setStockType(stock.getStockType());
                                            tempStock = stock2;
                                        }
                                    }
                                }
                                stocksStringList.add(new Pair<String, String>("Name: " +tempStock.getStockName() +"\nErsteller: "+ tempStock.getUserName()   +"\nAnzahl: " + tempStock.getLeftAmount() + "\nKosten: " +tempStock.getTotalCost()+ "\nTyp: " +tempStock.getStockType(),tempEvent.getData()));
                                break;
                            case TOOKSINGLE:
                                break;
                            default:
                                break;
                        }
                        }
                    }
                }
                stocksList.setValue(stocksStringList);

    }
    public StocksViewModel() {
    }

}