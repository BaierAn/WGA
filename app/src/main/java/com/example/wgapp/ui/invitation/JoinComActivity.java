package com.example.wgapp.ui.invitation;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.example.wgapp.MainActivity;
import com.example.wgapp.R;
import com.example.wgapp.models.Commune;
import com.example.wgapp.models.Roommate;
import com.example.wgapp.ui.signIn.FirebaseUIActivity;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.UUID;

public class JoinComActivity extends AppCompatActivity {

    private static final int RC_SIGN_IN = 123;

    EditText InputName ;
    private ProgressDialog mProgressDialog;
    Commune cm;
    Roommate rm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join_com);

        InputName = (EditText) findViewById(R.id.WGIdInput);

        Bundle extras = getIntent().getExtras();
        String userName;

        if (extras != null) {
            InputName.setText(extras.getString("link").substring(1));
        }

    }

    public void joinWG(View view){

        rm = MainActivity.getLocalUser();


        rm.setCommuneID(InputName.getText().toString());


        MainActivity.setCommuneReadRef(null);
        MainActivity.setCommuneWriteRef(null);
        MainActivity.initCommuneDataBase();

        mCheckInforComServer(MainActivity.getCommuneReadRef());





    }
    public boolean onOptionsItemSelected(MenuItem item){
        finish();
        return true;
    }


    public void mCheckInforComServer(DatabaseReference ref) {
        final Context self = this;
        mReadDataOnce(ref, new MainActivity.OnGetDataListener() {
            @Override
            public void onStart() {
                //DO SOME THING WHEN START GET DATA HERE
                if (mProgressDialog == null) {
                    mProgressDialog = new ProgressDialog(self);
                    mProgressDialog.setMessage("Lade WG Daten");
                    mProgressDialog.setIndeterminate(true);
                }

                mProgressDialog.show();
            }

            @Override
            public void onSuccess(DataSnapshot data) {
                if (mProgressDialog != null && mProgressDialog.isShowing()) {
                    mProgressDialog.dismiss();
                    Commune com = data.getValue(Commune.class);
                    cm = com;
                    if(cm != null && cm.getCommuneId() != null){
                        cm.addRommate(rm);
                        MainActivity.getUserWriteRef().setValue(rm);
                        MainActivity.getCommuneWriteRef().setValue(cm);

                        finish();
                        Intent intent = new Intent(self, MainActivity.class);
                        startActivity(intent);
                    }else{
                        if (mProgressDialog == null) {
                            mProgressDialog = new ProgressDialog( self);
                            mProgressDialog.setMessage("WG konnte nicht gefunden werden");
                            mProgressDialog.setIndeterminate(true);
                        }

                        mProgressDialog.show();

                    }
                }
                //DO SOME THING WHEN GET DATA SUCCESS HERE
            }

            @Override
            public void onFailure(DatabaseError databaseError) {
                //DO SOME THING WHEN GET DATA FAILED HERE
            }
        });

    }
    public void mReadDataOnce(DatabaseReference ref, final MainActivity.OnGetDataListener listener) {
        listener.onStart();
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                listener.onSuccess(dataSnapshot);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                listener.onFailure(databaseError);
            }
        });

    }
}