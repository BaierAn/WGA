package com.example.wgapp.ui.budget;

import android.app.ListActivity;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.example.wgapp.MainActivity;
import com.example.wgapp.R;
import com.example.wgapp.util.RecyclerViewAdapter;
import com.example.wgapp.util.SwipeToDeleteCallback;
import com.google.android.material.snackbar.Snackbar;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class BudgetFragment extends Fragment {

    private BudgetViewModel budgetViewModel;
    RecyclerViewAdapter mAdapter;
    RecyclerView budgetRecView;
    CoordinatorLayout coordinatorLayout;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        budgetViewModel =
                ViewModelProviders.of(this).get(BudgetViewModel.class);
        View root = inflater.inflate(R.layout.fragment_budget, container, false);

         budgetRecView = root.findViewById(R.id.budget_list_view);
         coordinatorLayout = root.findViewById(R.id.coordinatorLayout);

        budgetViewModel.getBudgetList();

        budgetViewModel.getBudgetList().observe(this, new Observer<List<Pair<String,String>>>() {
            @Override
            public void onChanged(List<Pair<String,String>> budgetList) {
                // update UI
                // android.widget.ListAdapter adapter = new ArrayAdapter<String>( getActivity(),
                //          android.R.layout.simple_list_item_1, android.R.id.text1, budgetList);
                // Assign adapter to ListView
                // budgetListView.setAdapter(adapter);

                mAdapter = new RecyclerViewAdapter((ArrayList<Pair<String,String>>) budgetList);
                budgetRecView.setAdapter(mAdapter);
            }
        });

        enableSwipeToDeleteAndUndo();
/*
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
*/        getActivity().setTitle(MainActivity.getCommune().getCommuneName());

        budgetViewModel.loadBudget();
        return root;
    }



    private void enableSwipeToDeleteAndUndo() {
        SwipeToDeleteCallback swipeToDeleteCallback = new SwipeToDeleteCallback(getContext()) {
            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int i) {


                final int position = viewHolder.getAdapterPosition();
                final Pair<String,String> item = mAdapter.getData().get(position);

                mAdapter.createPaidEvent(position);
                budgetViewModel.loadBudget();


                Snackbar snackbar = Snackbar
                        .make(coordinatorLayout, "Item was removed from the list.", Snackbar.LENGTH_LONG);
                snackbar.setAction("UNDO", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        mAdapter.restoreItem(item, position);
                        budgetRecView.scrollToPosition(position);
                    }
                });

                snackbar.setActionTextColor(Color.YELLOW);
                snackbar.show();

            }
        };

        ItemTouchHelper itemTouchhelper = new ItemTouchHelper(swipeToDeleteCallback);
        itemTouchhelper.attachToRecyclerView(budgetRecView);
    }

}