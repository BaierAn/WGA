package com.example.wgapp.ui.resources;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.wgapp.R;

import java.util.List;

public class ResourcesFragment extends Fragment {

    private ResourcesViewModel resourcesViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        resourcesViewModel =
                ViewModelProviders.of(this).get(ResourcesViewModel.class);
        View root = inflater.inflate(R.layout.fragment_resources, container, false);

        final ListView resourcesListView = root.findViewById(R.id.resources_list_view);
        resourcesViewModel.getResourcesList().observe(this, new Observer<List<String>>() {
            @Override
            public void onChanged(List<String> resourcesList) {
                // update UI
                android.widget.ListAdapter adapter = new ArrayAdapter<String>( getActivity(),
                        android.R.layout.simple_list_item_1, android.R.id.text1, resourcesList);
                // Assign adapter to ListView
                resourcesListView.setAdapter(adapter);
            }
        });

        return root;
    }
}