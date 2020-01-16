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
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.wgapp.MainActivity;
import com.example.wgapp.R;
import com.example.wgapp.models.CoEvent;
import com.example.wgapp.models.Commune;
import com.example.wgapp.models.Roommate;
import com.example.wgapp.ui.settings.EventLogActivity;
import com.example.wgapp.ui.settings.PlainTextActivity;
import com.example.wgapp.ui.signIn.FirebaseUIActivity;
import com.example.wgapp.ui.start.StartScreenActivity;
import com.google.gson.Gson;

import java.util.List;

public class ResourcesFragment extends Fragment {

    private ResourcesViewModel resourcesViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        resourcesViewModel =
                ViewModelProviders.of(this).get(ResourcesViewModel.class);
        View root = inflater.inflate(R.layout.fragment_resources, container, false);

        final ListView resourcesListView = root.findViewById(R.id.resources_list_view);

        resourcesListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position,
                                    long id) {
                Pair<String, String> ps = (Pair<String, String>) parent.getItemAtPosition(position);
                Intent usrDatIntent = new Intent(getContext(), SingleResourceActivity.class);
                usrDatIntent.putExtra("data", ps.second);
                startActivity(usrDatIntent);
            }
        });



        resourcesViewModel.getResourcesList().observe(this, new Observer<List<Pair<String,String>>>() {
            @Override
            public void onChanged(List<Pair<String,String>> resourcesList) {
                // update UI
                android.widget.ListAdapter adapter = new ArrayAdapter<Pair<String,String>>( getActivity(),
                        android.R.layout.simple_list_item_1, android.R.id.text1, resourcesList);
                // Assign adapter to ListView
                resourcesListView.setAdapter(adapter);
            }
        });

        return root;
    }


    public void linkToCreateResource(View view){
        Intent usrDatIntent = new Intent(getContext(), ResourceCreationActivity.class);
        startActivity(usrDatIntent);
    }
}

