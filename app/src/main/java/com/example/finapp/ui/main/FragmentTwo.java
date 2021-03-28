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
import com.example.finapp.Network;
import com.example.finapp.R;
import com.example.finapp.recycler.RecyclerViewAdapter;
import com.example.finapp.recycler.StockScrollView;

import java.util.LinkedList;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentTwo extends Fragment {
    public static final int numberPerLoad = 15;

    private View emptyView;
    private RecyclerView recyclerView;
    private RecyclerViewAdapter adapter;

    private int loadedNumber;
    private boolean isLoading;

    private MainActivity parentingActivity;

    // Required public constructor
    public FragmentTwo() {
    }

    public FragmentTwo(MainActivity parentingActivity) {
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
        adapter = new RecyclerViewAdapter(parentingActivity.getApplicationContext(), recyclerView, new LinkedList<>());
        recyclerView.setAdapter(adapter);
    }

    private void getCache() {
        if (isLoading) return;
        isLoading = true;

        Network.readFromCache(parentingActivity, adapter, true);
        adapter.notifyDataSetChanged();
        isLoading = false;
    }

    @Override
    public void onStop() {
        super.onStop();
        Network.writeToCache(adapter.dataset, true);
    }
}