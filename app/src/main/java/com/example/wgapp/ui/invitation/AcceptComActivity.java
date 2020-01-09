package com.example.wgapp.ui.invitation;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.wgapp.MainActivity;
import com.example.wgapp.R;
import com.example.wgapp.ui.start.StartScreenActivity;

public class AcceptComActivity extends AppCompatActivity

    {

        private static final int RC_SIGN_IN = 123;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accept_join);


        //db get commune

    }


    public void acceptInvitation(View view){
        //db get commune , add user

        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public void declineInvitation(View view){
        //todo zur main view

        Intent intent = new Intent(this, StartScreenActivity.class);
        startActivity(intent);
    }

    }