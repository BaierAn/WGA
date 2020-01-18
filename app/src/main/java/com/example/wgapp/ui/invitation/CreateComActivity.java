package com.example.wgapp.ui.invitation;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.example.wgapp.MainActivity;
import com.example.wgapp.R;
import com.example.wgapp.models.Commune;
import com.example.wgapp.models.Roommate;
import com.example.wgapp.ui.start.StartScreenActivity;
import com.example.wgapp.util.Database;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.dynamiclinks.DynamicLink;
import com.google.firebase.dynamiclinks.FirebaseDynamicLinks;

import java.util.UUID;

public class CreateComActivity extends AppCompatActivity {

    EditText InputName ;
    private ProgressDialog mProgressDialog;
    Roommate usr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_com);
        InputName = (EditText) findViewById(R.id.WGNameInput);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    public void createCommune(View view){
        Commune commune = new Commune();


        //todo roommate nullpointer
        usr = MainActivity.getLocalUser();
        if(usr == null){
            mCheckInforUsrServer(MainActivity.getUserWriteRef());
        }


        commune.setCommuneId(UUID.randomUUID().toString());
        commune.setCommuneLink(createLink(commune.getCommuneId()));
        usr.setCommuneID(commune.getCommuneId());
        MainActivity.setLocalUser(usr);
        MainActivity.getUserWriteRef().setValue(usr);
        commune.addRommate(usr);

        String test = InputName.getText().toString();
        commune.setCommuneName(test);
        MainActivity.initCommuneDataBase();
        MainActivity.getCommuneWriteRef().setValue(commune);
        MainActivity.setCommune(commune);
        finish();
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }


    public String createLink(String id){

        String appCode = "<app_code>";
        String packageName = getApplicationContext().getPackageName();

        DynamicLink dynamicLink = FirebaseDynamicLinks.getInstance().createDynamicLink()
                .setLink(Uri.parse("https://www.example.com/"+id))
                .setDomainUriPrefix("https://wgapp.page.link")
                // Open links with this app on Android
                .setAndroidParameters(new DynamicLink.AndroidParameters.Builder().build())
                // Open links with com.example.ios on iOS
                .buildDynamicLink();

        return dynamicLink.getUri().toString();
    }
    public boolean onOptionsItemSelected(MenuItem item){
        finish();
        return true;
    }


    public void mCheckInforUsrServer(DatabaseReference ref) {
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
                    Roommate mate = data.getValue(Roommate.class);
                    usr = mate;
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