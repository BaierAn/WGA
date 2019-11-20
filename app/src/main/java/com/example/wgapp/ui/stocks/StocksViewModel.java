package com.example.wgapp.ui.stocks;

import android.os.Handler;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class StocksViewModel extends ViewModel {

    private MutableLiveData<List<String>> stocksList;
    public LiveData<List<String>> getStocksList() {
        if (stocksList == null) {
            stocksList = new MutableLiveData<List<String>>();
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
                ArrayList<String> fruitsStringList = new ArrayList<String>();
                fruitsStringList.add("Mango");
                fruitsStringList.add("Apple");
                fruitsStringList.add("Orange");
                fruitsStringList.add("Banana");
                fruitsStringList.add("Grapes");
                long seed = System.nanoTime();
                Collections.shuffle(fruitsStringList, new Random(seed));

                stocksList.setValue(fruitsStringList);
            }
        }, 5000);

    }

    public StocksViewModel() {
    }

}