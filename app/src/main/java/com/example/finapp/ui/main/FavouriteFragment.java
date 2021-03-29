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

import java.util.LinkedList;

/**
 * A simple {@link Fragment} subclass.
 */
public class FavouriteFragment extends Fragment {

    private RecyclerView recyclerView;
    public static RecyclerViewAdapter adapter;
    private boolean isLoading;
    private MainActivity parentingActivity;

    public FavouriteFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.fragment_main, container, false);

        if (parentingActivity == null && container.getContext() instanceof MainActivity)
            parentingActivity = (MainActivity) container.getContext();

        recyclerView = layout.findViewById(R.id.recycler);
        recyclerView.setNestedScrollingEnabled(false);

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
        adapter = new RecyclerViewAdapter(parentingActivity.getApplicationContext(), recyclerView, new LinkedList<>(), true);
        recyclerView.setAdapter(adapter);
    }

    private void getCache() {
        if (isLoading) return;
        isLoading = true;

        Network.readFromCache(parentingActivity, adapter, true);
        adapter.filteredDataset = adapter.dataset;
        adapter.getFilter().filter(MainActivity.query);
        isLoading = false;
    }

    @Override
    public void onStop() {
        super.onStop();
        Network.writeToCache(adapter.dataset, true);
    }
}