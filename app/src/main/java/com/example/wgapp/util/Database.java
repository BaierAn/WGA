package com.example.wgapp.util;

import android.os.AsyncTask;
import android.util.Log;

import androidx.annotation.NonNull;

import com.example.wgapp.models.Commune;
import com.example.wgapp.models.Roommate;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Database extends AsyncTask<String, Void, Boolean> {

    private String TAG = "bla";
    private Commune commune;
    private Roommate mate;


    public void writeToDb(String Path, Object object){
        DatabaseReference database = FirebaseDatabase.getInstance().getReference();
        DatabaseReference myRef = database.child(Path);
        myRef.setValue(object);
    }

    public void setCommune(Commune commune) {
        this.commune = commune;
    }

    public void setMate(Roommate mate) {
        this.mate = mate;
    }

    public Commune getCommune() {
        return commune;
    }

    public Roommate getMate() {
        return mate;
    }

    public Roommate readUserFromDb(String Path){
        DatabaseReference database = FirebaseDatabase.getInstance().getReference();
        DatabaseReference myRef = database.child(Path);
        final Roommate post;
        ValueEventListener postListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // Get Post object and use the values to update the UI
                Roommate mate = dataSnapshot.getValue(Roommate.class);
                setMate(mate);
                // ...
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Getting Post failed, log a message
                Log.w(TAG, "loadPost:onCancelled", databaseError.toException());
                // ...
            }
        };
        myRef.addValueEventListener(postListener);
        return  getMate();
    }

    public Commune readCommuneFromDb(String Path){
        DatabaseReference database = FirebaseDatabase.getInstance().getReference();
        DatabaseReference myRef = database.child(Path);
        Commune com = new Commune();
        ValueEventListener postListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // Get Post object and use the values to update the UI
                Commune com = dataSnapshot.getValue(Commune.class);
                setCommune(com);
                // ...
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Getting Post failed, log a message
                Log.w(TAG, "loadPost:onCancelled", databaseError.toException());
                // ...
            }
        };
        myRef.addValueEventListener(postListener);

        return getCommune();
    }

    public void deleteFromDb(){

    }

    @Override
    protected Boolean doInBackground(String... strings) {

        return null;
    }
}