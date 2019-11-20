package com.example.wgapp.ui.budget;

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

import java.util.ArrayList;
import java.util.List;

public class BudgetFragment extends Fragment {

    private BudgetViewModel budgetViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        budgetViewModel =
                ViewModelProviders.of(this).get(BudgetViewModel.class);
        View root = inflater.inflate(R.layout.fragment_budget, container, false);

        final ListView budgetListView = root.findViewById(R.id.budget_list_view);
        budgetViewModel.getBudgetList().observe(this, new Observer<List<String>>() {
            @Override
            public void onChanged(List<String> budgetList) {
                // update UI
                android.widget.ListAdapter adapter = new ArrayAdapter<String>( getActivity(),
                        android.R.layout.simple_list_item_1, android.R.id.text1, budgetList);
                // Assign adapter to ListView
                budgetListView.setAdapter(adapter);
            }
        });

        return root;
    }
}