package com.example.wgapp.ui.stocks;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.example.wgapp.MainActivity;
import com.example.wgapp.R;
import com.example.wgapp.ui.budget.BudgetViewModel;
import com.example.wgapp.util.RecyclerViewAdapter;
import com.example.wgapp.util.SwipeToDeleteCallback;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

import static androidx.core.content.ContextCompat.startActivity;

public class StocksFragment extends Fragment {

    private StocksViewModel stocksViewModel;
    RecyclerViewAdapter mAdapter;
    RecyclerView stockRecView;
    CoordinatorLayout coordinatorLayout;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        stocksViewModel =
                ViewModelProviders.of(this).get(StocksViewModel.class);
        View root = inflater.inflate(R.layout.fragment_stocks, container, false);

        stockRecView = root.findViewById(R.id.stocks_list_view);

        coordinatorLayout = root.findViewById(R.id.coordinatorLayoutStock);

        stocksViewModel.getStocksList();

        stocksViewModel.getStocksList().observe(this, new Observer<List<Pair<String,String>>>() {
            @Override
            public void onChanged(List<Pair<String,String>> budgetList) {
                // update UI
                // android.widget.ListAdapter adapter = new ArrayAdapter<String>( getActivity(),
                //          android.R.layout.simple_list_item_1, android.R.id.text1, budgetList);
                // Assign adapter to ListView
                // budgetListView.setAdapter(adapter);

                mAdapter = new RecyclerViewAdapter((ArrayList<Pair<String,String>>) budgetList);
                stockRecView.setAdapter(mAdapter);
            }
        });

        enableSwipeToDeleteAndUndo();

        getActivity().setTitle(MainActivity.getCommune().getCommuneName());



        return root;
    }

    private void enableSwipeToDeleteAndUndo() {
        SwipeToDeleteCallback swipeToDeleteCallback = new SwipeToDeleteCallback(getContext()) {
            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int i) {


                final int position = viewHolder.getAdapterPosition();
                final Pair<String,String> item = mAdapter.getData().get(position);

                mAdapter.createTookSingleEvent(position);


                Snackbar snackbar = Snackbar
                        .make(coordinatorLayout, "Item was removed from the list.", Snackbar.LENGTH_LONG);
                snackbar.setAction("UNDO", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        mAdapter.restoreItem(item, position);
                        stockRecView.scrollToPosition(position);
                    }
                });

                snackbar.setActionTextColor(Color.YELLOW);
                snackbar.show();

            }
        };

        ItemTouchHelper itemTouchhelper = new ItemTouchHelper(swipeToDeleteCallback);
        itemTouchhelper.attachToRecyclerView(stockRecView);
    }


}