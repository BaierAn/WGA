package com.example.wgapp.ui.stocks;

import android.content.Intent;
import android.os.Bundle;
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
import com.example.wgapp.models.Stock;
import com.example.wgapp.models.StockCreationTypes;
import com.example.wgapp.util.BarcodeCaptureActivity;
import com.google.android.gms.common.api.CommonStatusCodes;
import com.google.android.gms.vision.CameraSource;
import com.google.android.gms.vision.barcode.Barcode;
import com.google.gson.Gson;

public class StockCreationActivity extends AppCompatActivity {

    CameraSource mCameraSource;
     Spinner DropdownType;
     EditText InputName ;
    SurfaceView camerapreview;
     EditText TotalCostInput;

     EditText TotalAmountInput;
    CameraSource cameraSource;
    EditText Barcode;

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

        Barcode = findViewById(R.id.StockCreationBarcodeInput);



        Bundle extras = getIntent().getExtras();
        String type;
        String data;
        if (extras != null) {
            type = extras.getString("type");
            switch (type) {
                case "Barcode":
                    data = extras.getString("data");
                    Barcode.setText(data);
                    break;
                case "Input":
                    data = extras.getString("data");
                    for (CoEvent event : MainActivity.getCommune().getCoEvents()) {
                        if(event.getType() == CoEventTypes.STOCK){
                            if(event.getBarcode().equals(data)){
                                Gson gson = new Gson();
                                Stock stock = gson.fromJson(event.getData(), Stock.class);

                                InputName.setText(stock.getName());
                                TotalCostInput.setText(String.valueOf(stock.getTotalCost()));
                                TotalAmountInput.setText(String.valueOf(stock.getTotalAmount()));
                                Barcode.setText(event.getBarcode());
                                DropdownType.setSelection(((ArrayAdapter) DropdownType.getAdapter()).getPosition(stock.getStockType()));

                            }

                        }


                    }
                    break;

            }

        }


    }


    public void getLastInput(View view){
        Intent intent = new Intent(this, BarcodeCaptureActivity.class);
        intent.putExtra(BarcodeCaptureActivity.AutoFocus, true);
        intent.putExtra(BarcodeCaptureActivity.UseFlash, false);
        startActivityForResult(intent, 9001);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 9001) {
            if (resultCode == CommonStatusCodes.SUCCESS) {
                if (data != null) {
                    com.google.android.gms.vision.barcode.Barcode barcode = data.getParcelableExtra(BarcodeCaptureActivity.BarcodeObject);
                    Barcode.setText(barcode.displayValue);
                }
            }
        }
        else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }
    public void scanBarcode(View view){


    }


    public void createStock(View view){

        int a = Integer.parseInt(TotalAmountInput.getText().toString());
        float b = Float.parseFloat(TotalCostInput.getText().toString());
        StockCreationTypes c = (StockCreationTypes)DropdownType.getSelectedItem();
        String d = InputName.getText().toString();


        Stock stockData = new Stock(Integer.parseInt(TotalAmountInput.getText().toString()),
                                    Float.parseFloat(TotalCostInput.getText().toString()),
                                    (StockCreationTypes)DropdownType.getSelectedItem(),
                                    InputName.getText().toString());
        /*Stock stockData = new Stock(60,
                60,
                StockCreationTypes.SHARE,
                "bananarama");
*/
        CoEvent stockCoEvent = new CoEvent(CoEventTypes.STOCK, new Gson().toJson(stockData));

        stockCoEvent.setBarcode(Barcode.getText().toString());
        MainActivity.getCommune().addCoEvent(stockCoEvent);

        MainActivity.getCommuneWriteRef().setValue(MainActivity.getCommune());

        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        //todo add event to commune

    }

}
