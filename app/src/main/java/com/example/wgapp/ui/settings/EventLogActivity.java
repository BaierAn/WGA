package com.example.wgapp.ui.settings;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.wgapp.MainActivity;
import com.example.wgapp.R;
import com.example.wgapp.models.CoEvent;
import com.example.wgapp.ui.start.StartScreenActivity;

import java.util.ArrayList;
import java.util.List;

public class EventLogActivity extends AppCompatActivity

{

    private static final int RC_SIGN_IN = 123;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.acticity_event_log);
        final ListView EventLogListView = findViewById(R.id.log_list);

        List<String> makitstop = new ArrayList<String>();

        for( CoEvent k: MainActivity.getCommune().getCoEvents() )
        {
            makitstop.add(k.getDateTime().toString());
        }

        android.widget.ListAdapter adapter = new ArrayAdapter<String>( this,
                android.R.layout.simple_list_item_1, android.R.id.text1, makitstop);
        // Assign adapter to ListView
        EventLogListView.setAdapter(adapter);

        final Context self = this;

        EventLogListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position,
                                    long id) {

                String item = ((TextView) view).getText().toString();

                for( CoEvent k: MainActivity.getCommune().getCoEvents() )
                {
                    if(k.getDateTime().toString().equals(item)){
                        Intent usrDatIntent = new Intent(self, PlainTextActivity.class);
                        usrDatIntent.putExtra("data", k.getData());
                        startActivity(usrDatIntent);
                    }
                }

            }

        //db get commune

    });


}


}
