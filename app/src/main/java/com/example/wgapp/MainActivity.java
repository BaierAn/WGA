package com.example.wgapp;

import android.os.Bundle;

import com.example.wgapp.models.CoEvent;
import com.example.wgapp.models.CoEventTypes;
import com.example.wgapp.models.Commune;
import com.example.wgapp.models.Roommate;
import com.example.wgapp.models.Stock;
import com.example.wgapp.models.StockCreationTypes;
import com.example.wgapp.ui.signIn.FirebaseUIActivity;
import com.example.wgapp.ui.start.StartScreenActivity;
import com.example.wgapp.ui.stocks.StockCreationActivity;
import com.example.wgapp.util.Database;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.gson.Gson;

import android.content.Intent;
import android.view.View;


import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity {


    private static Commune commune = new Commune();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initTestData();

        setContentView(R.layout.activity_main);
        BottomNavigationView navView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_budget, R.id.navigation_stocks, R.id.navigation_resources, R.id.navigation_settings)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(navView, navController);
        // [START check_current_user]

        //Todo check for invitation LINK



        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user == null) {
            final AppCompatActivity self = this;
           new Thread(
                   new Runnable() {
                       @Override
                       public void run() {
                           Intent intent = new Intent(self, FirebaseUIActivity.class);
                           startActivity(intent);
                       }
                   }
           ).start();
        }else{
            Database db = new Database();
            Roommate localUser = db.readUserFromDb("User/"+FirebaseAuth.getInstance().getCurrentUser().getUid());
            if(localUser != null || localUser.getCommuneID() != "None"){
                commune = db.readCommuneFromDb(localUser.getCommuneID());
            }else{
                Intent intent = new Intent(this, StartScreenActivity.class);
                startActivity(intent);
            }


        }
        // [END check_current_user]

    }

    public void initTestData(){

        Database db = new Database();
        commune.setCommuneId("123");

       // if(commune.getRoommates().size() < 1){
            ArrayList<Roommate> rl = new ArrayList<Roommate>();

            rl.add(new Roommate(10, FirebaseAuth.getInstance().getCurrentUser()));
            Stock stockData = new Stock(10,30,StockCreationTypes.SHARE,"Testi");

            CoEvent stockCoEvent = new CoEvent(CoEventTypes.STOCK, new Gson().toJson(stockData));

            MainActivity.addEvent(stockCoEvent);

            commune.setRoommates(rl);

         //db.basicReadWrite(commune);
         //db.readFromDb();
            String id1 =  "123";
            String id2 =  "223";
         db.writeToDb("User/"+"mcVIEWuPmIfSYOQFKwIOblHqQa32", commune.getRoommates().get(0) );

        db.readUserFromDb("User/"+"mcVIEWuPmIfSYOQFKwIOblHqQa32");
       // }
    }

    public void createNewStock(View view){
        Intent intent = new Intent(this , StockCreationActivity.class);
        startActivity(intent);
    }


    public void linkAndMerge(AuthCredential credential) {
        FirebaseAuth mAuth = FirebaseAuth.getInstance();

        // [START auth_link_and_merge]
        FirebaseUser prevUser = FirebaseAuth.getInstance().getCurrentUser();
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    //@NonNull
                    public void onComplete( Task<AuthResult> task) {
                        FirebaseUser currentUser = task.getResult().getUser();
                        // Merge prevUser and currentUser accounts and data
                        // ...
                    }
                });
        // [END auth_link_and_merge]
    }


    public void getGoogleCredentials() {
        String googleIdToken = "";
        // [START auth_google_cred]
        AuthCredential credential = GoogleAuthProvider.getCredential(googleIdToken, null);
        // [END auth_google_cred]
    }

    public void signOut() {
        // [START auth_sign_out]
        FirebaseAuth.getInstance().signOut();
        // [END auth_sign_out]
    }

    public static Commune getCommune() {
        return commune;
    }

    public static void setCommune(Commune commune) {
        MainActivity.commune = commune;
    }

    public static void addEvent(CoEvent coEvent) {
        List<CoEvent> l = MainActivity.commune.getCoEvents();
        l.add(coEvent);
        MainActivity.commune.setCoEvents(l);
    }
}
