package com.example.wgapp.ui.start;

import android.content.Intent;
import android.graphics.drawable.Icon;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.wgapp.MainActivity;
import com.example.wgapp.R;
import com.example.wgapp.ui.invitation.CreateComActivity;
import com.example.wgapp.ui.invitation.JoinComActivity;
import com.example.wgapp.ui.signIn.FirebaseUIActivity;
import com.example.wgapp.util.DownloadImageTask;
import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.IdpResponse;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class StartScreenActivity extends AppCompatActivity {

    private static final int RC_SIGN_IN = 123;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_screen);
        new DownloadImageTask((ImageView) findViewById(R.id.signInIcon))
                .execute(FirebaseAuth.getInstance().getCurrentUser().getPhotoUrl().toString());
        setTitle(FirebaseAuth.getInstance().getCurrentUser().getDisplayName());
    }

    public void linkToCreateComAc(View view){
        Intent intent = new Intent(this, CreateComActivity.class);
        startActivity(intent);
    }

    public void linkToJoinComAc(View view){
        Intent intent = new Intent(this, JoinComActivity.class);
        startActivity(intent);
    }

    public void LogOut(View view){

        FirebaseUIActivity.signOut(this);
        MainActivity.setLocalUser(null);
        finish();
        Intent intent = new Intent(this, FirebaseUIActivity.class);
        startActivity(intent);
    }
}