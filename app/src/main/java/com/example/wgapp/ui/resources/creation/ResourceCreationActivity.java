package com.example.wgapp.ui.resources.creation;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.SurfaceView;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;

import com.example.wgapp.MainActivity;
import com.example.wgapp.R;
import com.example.wgapp.models.CoEvent;
import com.example.wgapp.models.CoEventTypes;
import com.example.wgapp.models.Resource;
import com.example.wgapp.models.Stock;
import com.example.wgapp.models.StockCreationTypes;
import com.example.wgapp.ui.resources.ResourcesFragment;
import com.example.wgapp.util.barcode.BarcodeCaptureActivity;
import com.google.android.gms.common.api.CommonStatusCodes;
import com.google.android.gms.vision.CameraSource;
import com.google.android.gms.vision.barcode.Barcode;
import com.google.gson.Gson;

public class ResourceCreationActivity extends AppCompatActivity {

     EditText InputName ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        setContentView(R.layout.activity_res_creation);

        InputName = (EditText) findViewById(R.id.createResInput);

    }

    public void createRes(View view){

        Resource resData = new Resource(InputName.getText().toString());
        CoEvent stockCoEvent = new CoEvent(CoEventTypes.RESOURCE, new Gson().toJson(resData));

        MainActivity.getCommune().addCoEvent(stockCoEvent);
        MainActivity.getCommuneWriteRef().setValue(MainActivity.getCommune());

        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);


    }
    public boolean onOptionsItemSelected(MenuItem item){
        finish();
        return true;
    }

}
