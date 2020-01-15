package com.example.wgapp.util;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.wgapp.MainActivity;
import com.example.wgapp.R;
import com.example.wgapp.models.CoEvent;
import com.example.wgapp.models.CoEventTypes;
import com.example.wgapp.models.Stock;
import com.example.wgapp.models.StockCreationTypes;
import com.google.gson.Gson;

import java.util.ArrayList;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.MyViewHolder> {

    private ArrayList<String> data;

    public class MyViewHolder extends RecyclerView.ViewHolder {

        private TextView mTitle;
        RelativeLayout relativeLayout;

        public MyViewHolder(View itemView) {
            super(itemView);

            mTitle = itemView.findViewById(R.id.txtTitle);
        }
    }

    public RecyclerViewAdapter(ArrayList<String> data) {
        this.data = data;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview_row, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.mTitle.setText(data.get(position));
    }

    @Override
    public int getItemCount() {
        return data.size();
    }


    public void createTookSingleEvent(int position){

        /* Stock stockData = new Stock(Integer.parseInt(TotalAmountInput.getText().toString()),
                Float.parseFloat(TotalCostInput.getText().toString()),
                StockCreationTypes.TOOKSINGLE,
                InputName.getText().toString());
       Stock stockData = new Stock(60,
                60,
                StockCreationTypes.SHARE,
                "bananarama");

        CoEvent stockCoEvent = new CoEvent(CoEventTypes.STOCK, new Gson().toJson(stockData));

        MainActivity.getCommune().addCoEvent(stockCoEvent);
        MainActivity.getCommuneWriteRef().setValue(MainActivity.getCommune());
    }
    public void createPaidEvent(int position){

        CoEvent stockCoEvent = new CoEvent(CoEventTypes.STOCK, new Gson().toJson(stockData));

        MainActivity.getCommune().addCoEvent(stockCoEvent);
        MainActivity.getCommuneWriteRef().setValue(MainActivity.getCommune());
         */
    }

    public void removeItem(int position) {



        data.remove(position);


        //todo here send payed event




        notifyItemRemoved(position);
    }

    public void restoreItem(String item, int position) {
        data.add(position, item);
        notifyItemInserted(position);
    }

    public ArrayList<String> getData() {
        return data;
    }
}