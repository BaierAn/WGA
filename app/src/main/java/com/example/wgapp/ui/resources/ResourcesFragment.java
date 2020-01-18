package com.example.wgapp.ui.resources;

import android.content.Intent;
import android.os.Bundle;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.wgapp.MainActivity;
import com.example.wgapp.R;
import com.example.wgapp.models.MyPair;
import com.example.wgapp.ui.resources.creation.ResourceCreationActivity;

import java.util.List;

public class ResourcesFragment extends Fragment {

    private ResourcesViewModel resourcesViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        resourcesViewModel =
                ViewModelProviders.of(this).get(ResourcesViewModel.class);
        View root = inflater.inflate(R.layout.fragment_resources, container, false);

        final ListView resourcesListView = root.findViewById(R.id.resources_list_view);





        resourcesViewModel.getResourcesList().observe(this, new Observer<List<MyPair<String,String>>>() {
            @Override
            public void onChanged(List<MyPair<String,String>> resourcesList) {
                // update UI
                android.widget.ListAdapter adapter = new ArrayAdapter<MyPair<String,String>>( getActivity(),
                        android.R.layout.simple_list_item_1, android.R.id.text1, resourcesList);

                // Assign adapter to ListView
                resourcesListView.setAdapter(adapter);
            }
        });

        resourcesListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position,
                                    long id) {
                MyPair<String, String> ps = (MyPair<String, String>) parent.getItemAtPosition(position);
                Intent usrDatIntent = new Intent(getContext(), ResourceActivity.class);
                usrDatIntent.putExtra("data", ps.second);
                startActivity(usrDatIntent);
            }
        });
        getActivity().setTitle(MainActivity.getCommune().getCommuneName());

        return root;
    }


}

