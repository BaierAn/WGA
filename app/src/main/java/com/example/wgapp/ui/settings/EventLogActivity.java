package com.example.wgapp.ui.settings;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Pair;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.wgapp.MainActivity;
import com.example.wgapp.R;
import com.example.wgapp.models.CoEvent;
import com.example.wgapp.models.MyPair;
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

        List<MyPair<String, String>> makitstop = new ArrayList<MyPair<String, String>>();

        for( CoEvent k: MainActivity.getCommune().getCoEvents() )
        {
            makitstop.add(new MyPair<String, String>(k.getType() +" || " +k.getDateTime().toString() , k.getData()));
        }

        android.widget.ListAdapter adapter = new ArrayAdapter<MyPair<String, String>>( this,
                android.R.layout.simple_list_item_1, android.R.id.text1, makitstop);
        // Assign adapter to ListView
        EventLogListView.setAdapter(adapter);

        final Context self = this;
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        EventLogListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position,
                                    long id) {

                MyPair<String, String> k = (MyPair<String, String>) parent.getItemAtPosition(position);

                Intent usrDatIntent = new Intent(self, PlainTextActivity.class);
                usrDatIntent.putExtra("data", k.second);
                startActivity(usrDatIntent);


            }

        //db get commune

    });


}
    public boolean onOptionsItemSelected(MenuItem item){
        finish();
        return true;
    }

}
