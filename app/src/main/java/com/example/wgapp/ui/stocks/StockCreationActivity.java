package com.example.wgapp.ui.stocks;

import android.content.Intent;
import android.os.Bundle;
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


    private Spinner DropdownType;
    private EditText InputName ;

    private EditText TotalCostInput;

    private EditText TotalAmountInput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        DropdownType = findViewById(R.id.StockCreationDropdownType);
        ArrayAdapter<StockCreationTypes> adapter = new ArrayAdapter<StockCreationTypes>(this, android.R.layout.simple_spinner_dropdown_item, StockCreationTypes.values());
        DropdownType.setAdapter(adapter);



        InputName = findViewById(R.id.StockCreationInputName);

        TotalCostInput = findViewById(R.id.StockCreationTotalCostInput);

        TotalAmountInput = findViewById(R.id.StockCreationTotalAmountInput);
    }

    public void getLastInput(){

    }

    public void scanBarcode(){

    }

    public void createStock(){
        Stock stockData = new Stock(Integer.parseInt(TotalAmountInput.getText().toString()),
                                    Integer.parseInt(TotalCostInput.getText().toString()),
                                    (StockCreationTypes)DropdownType.getSelectedItem(),
                                    InputName.getText().toString());

        CoEvent stockCoEvent = new CoEvent(CoEventTypes.STOCK, new Gson().toJson(stockData));
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("CoEvent", stockCoEvent);
        startActivity(intent);
        //todo add event to commune
    }

}
