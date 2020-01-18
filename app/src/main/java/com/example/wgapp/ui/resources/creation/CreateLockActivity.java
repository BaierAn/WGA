package com.example.wgapp.ui.resources.creation;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.net.ParseException;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.example.wgapp.MainActivity;
import com.example.wgapp.R;
import com.example.wgapp.models.CoEvent;
import com.example.wgapp.models.CoEventTypes;
import com.example.wgapp.models.Lock;
import com.example.wgapp.models.Resource;
import com.example.wgapp.ui.resources.ResourceActivity;
import com.example.wgapp.ui.resources.ResourcesFragment;
import com.google.gson.Gson;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class CreateLockActivity extends AppCompatActivity {

    EditText InputTime ;
    DatePickerDialog  InputDatePicker ;
    EditText InputDesc ;
    Button dateButton;
    EditText InputDate;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_lock_creation);

        InputDesc = (EditText) findViewById(R.id.LockInputDesc);
        InputTime = (EditText) findViewById(R.id.LockInputTime);
        InputDate = (EditText) findViewById(R.id.LockInputDate);
        dateButton = findViewById(R.id.datePickerButton);

    }

    public void pickDate(View view){
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);

        InputDatePicker = new DatePickerDialog(this,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                        InputDate.setText(day + "/" + (month + 1) + "/" + year);
                    }
                }, year, month, dayOfMonth);

        InputDatePicker.show();


    }

    public void createLock(View view){
        Intent resIntent = getIntent();
        Resource res = new Gson().fromJson(resIntent.getStringExtra("data"), Resource.class);


        Lock resData = new Lock(InputDesc.getText().toString(), getDateFromString(InputDate.getText().toString()) , Integer.parseInt(InputTime.getText().toString()), res.getResId() );
        CoEvent lockCoEvent = new CoEvent(CoEventTypes.LOCK, new Gson().toJson(resData));

        MainActivity.getCommune().addCoEvent(lockCoEvent);
        MainActivity.getCommuneWriteRef().setValue(MainActivity.getCommune());

        Intent intent = new Intent(this, ResourceActivity.class);
        intent.putExtra("data",resIntent.getStringExtra("data") );
        startActivity(intent);


    }

    public  java.util.Date getDateFromString(String datePicker){

        Date date = new Date();
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        try {
            date = format.parse(datePicker);
            System.out.println(date);
        } catch (ParseException e) {
            e.printStackTrace();
        } catch (java.text.ParseException e) {
            e.printStackTrace();
        }


        return date;
    }

}

