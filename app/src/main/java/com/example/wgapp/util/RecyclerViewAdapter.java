package com.example.wgapp.util;

import android.graphics.Paint;
import android.util.Pair;
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
import com.example.wgapp.models.Commune;
import com.example.wgapp.models.Stock;
import com.example.wgapp.models.StockCreationTypes;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
import com.google.gson.Gson;

import java.util.ArrayList;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.MyViewHolder> {

    private ArrayList<Pair<String, String>> data;

    public class MyViewHolder extends RecyclerView.ViewHolder {

        private TextView mTitle;
        RelativeLayout relativeLayout;
        private boolean isSwipable;

        public MyViewHolder(View itemView) {
            super(itemView);

            mTitle = itemView.findViewById(R.id.txtTitle);
            isSwipable = true;
        }

        public boolean isSwipable() {
            return isSwipable;
        }

        public void setSwipable(boolean swipable) {
            isSwipable = swipable;
        }
    }

    public RecyclerViewAdapter(ArrayList<Pair<String, String>> data) {
        this.data = data;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview_row, parent, false);




        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        holder.mTitle.setText(data.get(position).first);

        Stock stock = new Gson().fromJson(data.get(position).second, Stock.class);
        if(stock != null){
                if(stock.getStockType() == StockCreationTypes.SHARE){
                    holder.setSwipable(false);
                    return;
                }
                if(stock.getStockType() == StockCreationTypes.SINGLEUSE){
                    return;
                }



            for (CoEvent ev : MainActivity.getCommune().getCoEvents()) {
                if( ev.getType() == CoEventTypes.PAID){
                    Stock comStock = new Gson().fromJson(ev.getData(), Stock.class);
                    if(ev.getData().equals(data.get(position).second)){
                        holder.setSwipable(false);
                        holder.mTitle.setPaintFlags(holder.mTitle.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                        return;
                    }
                }
            }
            }

    }




    @Override
    public int getItemCount() {
        return data.size();
    }


    public void createTookSingleEvent(int position){

        Stock stock = new Gson().fromJson(data.get(position).second, Stock.class );

        if(stock.getStockType() == StockCreationTypes.SINGLEUSE || stock.getStockType() == StockCreationTypes.TOOKSINGLE){
            Stock newStock = new Stock(stock.getTotalAmount(),
                    stock.getLeftAmount() - 1 ,
                    stock.getTotalCost(),
                    StockCreationTypes.TOOKSINGLE,
                    stock.getStockName());
            newStock.setID(stock.getID());

            CoEvent stockCoEvent = new CoEvent(CoEventTypes.STOCK, new Gson().toJson(newStock));

            MainActivity.getCommune().addCoEvent(stockCoEvent);
            MainActivity.getCommuneWriteRef().setValue(MainActivity.getCommune());

            notifyDataSetChanged();

        }
    }


    public void createPaidEvent(int position){
        CoEvent stockCoEvent = new CoEvent(CoEventTypes.PAID, data.get(position).second);

        MainActivity.getCommune().addCoEvent(stockCoEvent);
        MainActivity.getCommuneWriteRef().setValue(MainActivity.getCommune());

        notifyDataSetChanged();

    }

    public void removeItem(int position) {



        data.remove(position);


        notifyItemRemoved(position);
    }

    public void restoreItem(Pair<String, String> item, int position) {
        data.add(position, item);
        notifyItemInserted(position);
    }



    public ArrayList<Pair<String, String>> getData() {
        return data;
    }
}

