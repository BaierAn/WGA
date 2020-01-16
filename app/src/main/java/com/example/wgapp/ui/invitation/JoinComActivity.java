package com.example.wgapp.ui.invitation;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.example.wgapp.MainActivity;
import com.example.wgapp.R;
import com.example.wgapp.models.Commune;
import com.example.wgapp.models.Roommate;
import com.example.wgapp.ui.signIn.FirebaseUIActivity;

import java.util.UUID;

public class JoinComActivity extends AppCompatActivity {

    private static final int RC_SIGN_IN = 123;

    EditText InputName ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join_com);

        InputName = (EditText) findViewById(R.id.WGIdInput);

        Bundle extras = getIntent().getExtras();
        String userName;

        if (extras != null) {
            InputName.setText(extras.getString("link"));
        }

    }

    public void joinWG(View view){

        Roommate rm = MainActivity.getLocalUser();
        rm.setCommuneID(InputName.getText().toString());

        MainActivity.initCommuneDataBase();
        Commune com = MainActivity.getCommune();

        com.addRommate(rm);
        MainActivity.getUserWriteRef().setValue(rm);
        MainActivity.getCommuneWriteRef().setValue(com);

        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

}