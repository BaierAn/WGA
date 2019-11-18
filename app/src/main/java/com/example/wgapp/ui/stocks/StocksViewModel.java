package com.example.wgapp.ui.stocks;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class StocksViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public StocksViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is stocks fragment hard code");
    }

    public LiveData<String> getText() {
        return mText;
    }
}