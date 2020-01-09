package com.example.wgapp.ui.invitation;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.wgapp.R;
import com.example.wgapp.ui.signIn.FirebaseUIActivity;

public class JoinComActivity extends AppCompatActivity {

    private static final int RC_SIGN_IN = 123;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join_com);

        //todo wg link reinschallern
    }


    public void joinWG(View view){

        Intent intent = new Intent(this, AcceptComActivity.class);
        startActivity(intent);

    }

}