package com.example.wgapp.ui.settings;

import android.os.Handler;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class SettingsViewModel extends ViewModel {

    private MutableLiveData<List<String>> settingsList;
    public LiveData<List<String>> getSettingsList() {
        if (settingsList == null) {
            settingsList = new MutableLiveData<List<String>>();
            loadSettings();
        }
        return settingsList;
    }


    private void loadSettings() {
        // do async operation to fetch users

        Handler myHandler = new Handler();
        myHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                ArrayList<String> SettingsStringList = new ArrayList<String>();
                SettingsStringList.add("EventLog");
                SettingsStringList.add("Mit Link einladen");
                SettingsStringList.add("Nutzerdaten");
                SettingsStringList.add("WG verlassen");
                SettingsStringList.add("Abmelden");
                SettingsStringList.add("Nutzerdaten löschen");
                settingsList.setValue(SettingsStringList);
            }
        }, 1);

    }
    public SettingsViewModel() {
    }

}