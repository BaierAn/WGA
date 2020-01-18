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
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.wgapp.MainActivity;
import com.example.wgapp.R;
import com.example.wgapp.models.CoEvent;
import com.example.wgapp.models.CoEventTypes;
import com.example.wgapp.models.Lock;
import com.example.wgapp.models.Resource;
import com.example.wgapp.ui.resources.creation.CreateLockActivity;
import com.example.wgapp.ui.resources.creation.ResourceCreationActivity;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

public class ResourceActivity extends AppCompatActivity {

    private List<String> lockList  = new ArrayList<String>();
    private Lock lock;
    ListView lockListView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resource);

        Intent intent = getIntent();
        Resource res = new Gson().fromJson(intent.getStringExtra("data"), Resource.class);

        setTitle(res.getResName());

        for (CoEvent event: MainActivity.getCommune().getCoEvents()) {
            if(event.getType() == CoEventTypes.LOCK ){
                lock = new Gson().fromJson(event.getData(), Lock.class);
                if(lock.getResID().equals(res.getResId())){
                    lockList.add(lock.getDescription() + "\nAbgeschlossen: " + lock.isExpired());
                }
            }
        }

        lockListView = findViewById(R.id.lock_list_view);
        lockListView.setAdapter(new ArrayAdapter<String>(getBaseContext(),
                android.R.layout.simple_list_item_1, android.R.id.text1, lockList));
    }

    public void createLock(View view) {
        Intent resIntent = getIntent();
        Intent intent = new Intent(this, CreateLockActivity.class);
        intent.putExtra("data", resIntent.getStringExtra("data"));
        startActivity(intent);
    }
}