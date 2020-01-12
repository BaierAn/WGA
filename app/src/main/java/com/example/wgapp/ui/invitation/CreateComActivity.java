package com.example.wgapp.ui.invitation;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
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
import com.google.firebase.dynamiclinks.DynamicLink;
import com.google.firebase.dynamiclinks.FirebaseDynamicLinks;

import java.util.UUID;

public class CreateComActivity extends AppCompatActivity {

    private static final int RC_SIGN_IN = 123;
    EditText InputName ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_com);
        InputName = (EditText) findViewById(R.id.WGNameInput);
    }

    public void createCommune(View view){
        Commune commune = new Commune();

        Roommate usr = MainActivity.getLocalUser();
        commune.setCommuneLink(createLink());
        commune.setCommuneId(UUID.randomUUID().toString());
        usr.setCommuneID(commune.getCommuneId());
        MainActivity.setLocalUser(usr);
        MainActivity.getUserWriteRef().setValue(usr);
        commune.addRommate(usr);

        String test = InputName.getText().toString();
        commune.setCommuneName(test);
        MainActivity.initCommuneDataBase();
        MainActivity.getCommuneWriteRef().setValue(commune);

        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }


    public String createLink(){
        DynamicLink dynamicLink = FirebaseDynamicLinks.getInstance().createDynamicLink()
                .setLink(Uri.parse("https://www.example.com/"))
                .setDomainUriPrefix("https://wgapp.page.link")
                // Open links with this app on Android
                .setAndroidParameters(new DynamicLink.AndroidParameters.Builder().build())
                // Open links with com.example.ios on iOS
                .buildDynamicLink();

        return dynamicLink.getUri().toString();
    }
}