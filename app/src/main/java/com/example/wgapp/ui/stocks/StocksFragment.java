package com.example.wgapp.ui.stocks;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.wgapp.R;

import java.util.List;

public class StocksFragment extends Fragment {

    private StocksViewModel stocksViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        stocksViewModel =
                ViewModelProviders.of(this).get(StocksViewModel.class);
        View root = inflater.inflate(R.layout.fragment_budget, container, false);

        final ListView stocksListView = root.findViewById(R.id.stocks_list_view);
        stocksViewModel.getStocksList().observe(this, new Observer<List<String>>() {
            @Override
            public void onChanged(List<String> stocksList) {
                // update UI
                android.widget.ListAdapter adapter = new ArrayAdapter<String>( getActivity(),
                        android.R.layout.simple_list_item_1, android.R.id.text1, stocksList);
                // Assign adapter to ListView
                stocksListView.setAdapter(adapter);
            }
        });

        return root;
    }
}