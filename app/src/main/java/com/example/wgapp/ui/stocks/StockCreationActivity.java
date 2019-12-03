package com.example.wgapp.ui.stocks;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;

import com.example.wgapp.MainActivity;
import com.example.wgapp.R;
import com.example.wgapp.models.CoEvent;
import com.example.wgapp.models.CoEventTypes;
import com.example.wgapp.models.Stock;
import com.example.wgapp.models.StockCreationTypes;
import com.example.wgapp.ui.signIn.FirebaseUIActivity;
import com.google.gson.Gson;

public class StockCreationActivity extends AppCompatActivity {


     Spinner DropdownType;
     EditText InputName ;

     EditText TotalCostInput;

     EditText TotalAmountInput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        //View root = getLayoutInflater().inflate(R.layout.activity_stocks_creation, null);

        //LayoutInflater layoutInflater = (LayoutInflater)this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        //View root = layoutInflater.inflate(R.layout.activity_stocks_creation, null );
        setContentView(R.layout.activity_stocks_creation);



        DropdownType = findViewById(R.id.StockCreationDropdownType);
        ArrayAdapter<StockCreationTypes> adapter = new ArrayAdapter<StockCreationTypes>(this, android.R.layout.simple_spinner_dropdown_item, StockCreationTypes.values());
        DropdownType.setAdapter(adapter);

        InputName = (EditText) findViewById(R.id.StockCreationInputName);

        TotalCostInput = findViewById(R.id.StockCreationTotalCostInput);

        TotalAmountInput = findViewById(R.id.StockCreationTotalAmountInput);
        setContentView(R.layout.activity_stocks_creation);


    }

    public void getLastInput(){

    }

    public void scanBarcode(){

    }

    public void createStock(View view){
        /*Stock stockData = new Stock(Integer.parseInt(TotalAmountInput.getText().toString()),
                                    Integer.parseInt(TotalCostInput.getText().toString()),
                                    (StockCreationTypes)DropdownType.getSelectedItem(),
                                    InputName.getText().toString());
        */
        Stock stockData = new Stock(60,
                60,
                StockCreationTypes.SHARE,
                "bananarama");

        CoEvent stockCoEvent = new CoEvent(CoEventTypes.STOCK, new Gson().toJson(stockData));

        MainActivity.addEvent(stockCoEvent);

        Intent intent = new Intent(this, MainActivity.class);


        startActivity(intent);
        //todo add event to commune
    }

}