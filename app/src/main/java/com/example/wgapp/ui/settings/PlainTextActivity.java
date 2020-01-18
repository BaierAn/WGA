package com.example.wgapp.ui.settings;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.wgapp.MainActivity;
import com.example.wgapp.R;
import com.example.wgapp.ui.start.StartScreenActivity;

public class PlainTextActivity extends AppCompatActivity {

    TextView dataView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_text);

        dataView = findViewById(R.id.status);

        Bundle extras = getIntent().getExtras();
        String data;

        if (extras != null) {
            data = extras.getString("data");
            dataView.setText(data);
            // and get whatever type user account id is

        }
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }
    public boolean onOptionsItemSelected(MenuItem item){
        finish();
        return true;
    }
}
