package com.example.wgapp.ui.settings;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.wgapp.R;

import java.util.List;

public class SettingsFragment extends Fragment {

    private SettingsViewModel settingsViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        settingsViewModel =
                ViewModelProviders.of(this).get(SettingsViewModel.class);
        View root = inflater.inflate(R.layout.fragment_budget, container, false);

        final ListView settingsListView = root.findViewById(R.id.settings_list_view);
        settingsViewModel.getSettingsList().observe(this, new Observer<List<String>>() {
            @Override
            public void onChanged(List<String> settingsList) {
                // update UI
                android.widget.ListAdapter adapter = new ArrayAdapter<String>( getActivity(),
                        android.R.layout.simple_list_item_1, android.R.id.text1, settingsList);
                // Assign adapter to ListView
                settingsListView.setAdapter(adapter);
            }
        });

        return root;
    }
}