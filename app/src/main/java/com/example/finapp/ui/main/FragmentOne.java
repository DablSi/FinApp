package com.example.finapp.ui.main;

import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.finapp.MainActivity;
import com.example.finapp.R;
import com.example.finapp.StockRecord;
import com.example.finapp.recycler.RecyclerAdapter;
import com.example.finapp.recycler.RecyclerViewAdapter;
import yahoofinance.Stock;
import yahoofinance.YahooFinance;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentOne extends Fragment {
    public static final int numberPerLoad = 20;

    private View emptyView;
    private RecyclerView recyclerView;
    private RecyclerAdapter adapter;

    private List<StockRecord> logRecords;
    private int loadedNumber;
    private boolean isLoading;

    private MainActivity parentingActivity;

    // Required public constructor
    public FragmentOne() {
    }

    public FragmentOne(MainActivity parentingActivity) {
        this.parentingActivity = parentingActivity;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View layout = inflater.inflate(R.layout.fragment_main, container, false);

        // Try to get parenting activity if not given
        if (parentingActivity == null && container.getContext() instanceof MainActivity)
            parentingActivity = (MainActivity) container.getContext();

        // Find views by ids
        emptyView = layout.findViewById(R.id.empty_view);
        recyclerView = layout.findViewById(R.id.recycler);

        return layout;
    }

    private void setupRecyclerView() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext()) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        };

        layoutManager.setStackFromEnd(false);
        layoutManager.setReverseLayout(false);
        layoutManager.setItemPrefetchEnabled(false);

        recyclerView.setLayoutManager(layoutManager);
        logRecords = new ArrayList<>();
        adapter = new RecyclerViewAdapter(parentingActivity.getApplicationContext(), recyclerView, logRecords);
        recyclerView.setAdapter(adapter);
    }
}