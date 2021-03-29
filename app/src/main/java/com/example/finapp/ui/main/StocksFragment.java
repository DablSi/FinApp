package com.example.finapp.ui.main;

import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.finapp.utils.Network;
import com.example.finapp.R;
import com.example.finapp.recycler.RecyclerViewAdapter;
import com.example.finapp.recycler.StockScrollView;

import java.util.LinkedList;

/**
 * A simple {@link Fragment} subclass.
 */
public class StocksFragment extends Fragment {
    public static final int numberPerLoad = 15;

    private RecyclerView recyclerView;
    public static RecyclerViewAdapter adapter;
    private boolean isLoading;

    private MainActivity parentingActivity;

    // Required public constructor
    public StocksFragment() {
    }

    public StocksFragment(MainActivity parentingActivity) {
        this.parentingActivity = parentingActivity;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.fragment_main, container, false);

        if (parentingActivity == null && container.getContext() instanceof MainActivity)
            parentingActivity = (MainActivity) container.getContext();

        recyclerView = layout.findViewById(R.id.recycler);
        recyclerView.setNestedScrollingEnabled(false);

        ((StockScrollView) layout.findViewById(R.id.scroll_layout)).setBottomCallback(this::loadMoreRecords);
        setupRecyclerView();
        new Handler().postDelayed(this::getCache, 100);
        return layout;
    }

    private void setupRecyclerView() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());

        layoutManager.setStackFromEnd(false);
        layoutManager.setReverseLayout(false);
        layoutManager.setItemPrefetchEnabled(false);

        recyclerView.setLayoutManager(layoutManager);
        adapter = new RecyclerViewAdapter(parentingActivity.getApplicationContext(), recyclerView, new LinkedList<>(), false);
        recyclerView.setAdapter(adapter);
    }

    private void loadMoreRecords() {
        if (isLoading) return;
        isLoading = true;

        if (Network.loadMoreStocks(numberPerLoad, adapter.dataset)) {
            adapter.filteredDataset = adapter.dataset;
            adapter.getFilter().filter(MainActivity.query);
        }
        isLoading = false;
    }

    private void getCache() {
        if (isLoading) return;
        isLoading = true;

        Network.readFromCache(parentingActivity, adapter, false);
        adapter.filteredDataset = adapter.dataset;
        adapter.getFilter().filter(MainActivity.query);
        isLoading = false;

        if (adapter.dataset.size() == 0) new Handler().postDelayed(this::loadMoreRecords, 100);
    }

    @Override
    public void onStop() {
        super.onStop();
        Network.writeToCache(adapter.dataset, false);
    }
}