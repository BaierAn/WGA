package com.example.wgapp;

import android.app.ProgressDialog;
import android.content.Context;
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
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.TaskCompletionSource;
import com.google.android.gms.tasks.Tasks;
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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;

import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;


import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutionException;


public class MainActivity extends AppCompatActivity {

    private ProgressDialog mProgressDialog;


    private static DatabaseReference CommuneReadRef;
    private static DatabaseReference CommuneWriteRef;

    private static DatabaseReference UserReadRef;
    private static DatabaseReference UserWriteRef;

    private static Commune commune = new Commune();
    private static Roommate localUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //initTestData();
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
            //Database db = new Database();
            //Tasks.await(taskFromFirebase);



            //initUserDataBase();
            //localUser = getUserDataBaseSynchron();
            UserWriteRef = FirebaseDatabase.getInstance().getReference().child("User/"+FirebaseAuth.getInstance().getCurrentUser().getUid());
            UserReadRef = FirebaseDatabase.getInstance().getReference().child("User/"+FirebaseAuth.getInstance().getCurrentUser().getUid());
            mCheckInforInServer(UserReadRef);


        }
        // [END check_current_user]

    }



    public static void initUserDataBase(){
        if (UserReadRef == null || UserWriteRef == null){
            UserReadRef = FirebaseDatabase.getInstance().getReference().child("User/"+FirebaseAuth.getInstance().getCurrentUser().getUid());
            UserWriteRef = FirebaseDatabase.getInstance().getReference().child("User/"+FirebaseAuth.getInstance().getCurrentUser().getUid());

            ValueEventListener postListener = new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    // Get Post object and use the values to update the UI
                    Roommate mate = dataSnapshot.getValue(Roommate.class);
                    localUser = mate;

                }
                @Override
                public void onCancelled(DatabaseError databaseError) {
                    // Getting Post failed, log a message
                    Log.w("TAG", "loadPost:onCancelled", databaseError.toException());
                    // ...
                }
            };
            UserReadRef.addValueEventListener(postListener);
        }
    }

    public static Roommate getUserDataBaseSynchron(){
        final CountDownLatch  done = new CountDownLatch(1);
        UserReadRef = FirebaseDatabase.getInstance().getReference().child("User/"+FirebaseAuth.getInstance().getCurrentUser().getUid());
            ValueEventListener postListener = new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    // Get Post object and use the values to update the UI
                    Roommate mate = dataSnapshot.getValue(Roommate.class);
                    localUser = mate;
                    done.countDown();
                }
                @Override
                public void onCancelled(DatabaseError databaseError) {
                    // Getting Post failed, log a message
                    Log.w("TAG", "loadPost:onCancelled", databaseError.toException());
                    // ...
                }
            };
            UserReadRef.addValueEventListener(postListener);
        try {
            done.await(); //it will wait till the response is received from firebase.
        } catch(InterruptedException e) {
            e.printStackTrace();
        }
        return localUser;
    }




    public static void initCommuneDataBase(){

        if (CommuneReadRef == null || CommuneWriteRef == null){
            CommuneReadRef = FirebaseDatabase.getInstance().getReference().child("Commune/"+localUser.getCommuneID());
            CommuneWriteRef = FirebaseDatabase.getInstance().getReference().child("Commune/"+localUser.getCommuneID());

            ValueEventListener postListener = new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    // Get Post object and use the values to update the UI
                    Commune com = dataSnapshot.getValue(Commune.class);
                    commune = com;
                }
                @Override
                public void onCancelled(DatabaseError databaseError) {
                    // Getting Post failed, log a message
                    Log.w("TAG", "loadPost:onCancelled", databaseError.toException());
                    // ...
                }
            };
            CommuneReadRef.addValueEventListener(postListener);
        }

    }



    public interface OnGetDataListener {
        //this is for callbacks
        void onSuccess(DataSnapshot dataSnapshot);
        void onStart();
        void onFailure(DatabaseError databaseError);
    }

    public void mReadDataOnce(DatabaseReference ref, final OnGetDataListener listener) {
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

    private void mCheckInforInServer(DatabaseReference ref) {
        final Context self = this;
        mReadDataOnce(ref, new OnGetDataListener() {
            @Override
            public void onStart() {
                //DO SOME THING WHEN START GET DATA HERE
                if (mProgressDialog == null) {
                    mProgressDialog = new ProgressDialog( self);
                    mProgressDialog.setMessage("Loading User Data");
                    mProgressDialog.setIndeterminate(true);
                }

                mProgressDialog.show();
            }

            @Override
            public void onSuccess(DataSnapshot data) {
                if (mProgressDialog != null && mProgressDialog.isShowing()) {
                    mProgressDialog.dismiss();
                    Roommate mate = data.getValue(Roommate.class);
                    localUser = mate;

                    if(localUser == null || localUser.getCommuneID() == "None"){
                        Intent intent = new Intent(self, StartScreenActivity.class);
                        startActivity(intent);
                    }else{
                        initCommuneDataBase();
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


    public static void setCommuneReadRef(DatabaseReference communeReadRef) {
        CommuneReadRef = communeReadRef;
    }

    public static void setCommuneWriteRef(DatabaseReference communeWriteRef) {
        CommuneWriteRef = communeWriteRef;
    }

    public static void setUserReadRef(DatabaseReference userReadRef) {
        UserReadRef = userReadRef;
    }

    public static void setUserWriteRef(DatabaseReference userWriteRef) {
        UserWriteRef = userWriteRef;
    }

    public static DatabaseReference getCommuneReadRef() {
        return CommuneReadRef;
    }

    public static DatabaseReference getCommuneWriteRef() {
        return CommuneWriteRef;
    }

    public static DatabaseReference getUserReadRef() {
        return UserReadRef;
    }

    public static DatabaseReference getUserWriteRef() {
        return UserWriteRef;
    }

    public static void setLocalUser(Roommate localUser) {
        MainActivity.localUser = localUser;
    }
    public static Roommate getLocalUser() {
        return localUser;
    }
}
