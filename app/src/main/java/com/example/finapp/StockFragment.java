//package com.example.finapp;
//
//import android.os.Bundle;
//import android.os.Handler;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//
//import androidx.fragment.app.Fragment;
//import androidx.recyclerview.widget.LinearLayoutManager;
//import androidx.recyclerview.widget.RecyclerView;
//import com.example.finapp.recycler.RecyclerAdapter;
//import com.example.finapp.recycler.RecyclerViewAdapter;
//import yahoofinance.Stock;
//import yahoofinance.YahooFinance;
//
//import java.io.IOException;
//import java.math.BigDecimal;
//import java.util.ArrayList;
//import java.util.List;
//
///**
// * A simple {@link Fragment} subclass.
// */
//public class StockFragment extends Fragment {
//    public static final int numberPerLoad = 20;
//
//    private View emptyView;
//    private RecyclerView recyclerView;
//    private RecyclerAdapter adapter;
//
//    private List<StockRecord> logRecords;
//    private int loadedNumber;
//    private boolean isLoading;
//
//    private MainActivity parentingActivity;
//
//    // Required public constructor
//    public StockFragment() {
//    }
//
//    public StockFragment(MainActivity parentingActivity) {
//        this.parentingActivity = parentingActivity;
//    }
//
//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container,
//                             Bundle savedInstanceState) {
//        // Inflate the layout for this fragment
//        View layout = inflater.inflate(R.layout.fragment_stock, container, false);
//
//        // Try to get parenting activity if not given
//        if (parentingActivity == null && container.getContext() instanceof MainActivity)
//            parentingActivity = (MainActivity) container.getContext();
//
//        // Find views by ids
//        emptyView = layout.findViewById(R.id.);
//        recyclerView = layout.findViewById(R.id.recycler);
//
//        ((LogScrollView) layout.findViewById(R.id.scroll_layout)).setBottomCallback(this::loadMoreRecords);
//
//        setupRecyclerView();
//        new Handler().postDelayed(this::loadMoreRecords, 100);
//
//        return layout;
//    }
//
//    private void setupRecyclerView() {
//        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext()) {
//            @Override
//            public boolean canScrollVertically() {
//                return false;
//            }
//        };
//
//        layoutManager.setStackFromEnd(false);
//        layoutManager.setReverseLayout(false);
//        layoutManager.setItemPrefetchEnabled(false);
//
//        recyclerView.setLayoutManager(layoutManager);
//
//        logRecords = new ArrayList<>();
//        adapter = new RecyclerViewAdapter(recyclerView, logRecords);
//        recyclerView.setAdapter(adapter);
//    }
//
//    private void loadMoreRecords() {
//        if (isLoading) return;
//        isLoading = true;
//        Stock stock = null;
//        try {
//            stock = YahooFinance.get("INTC");
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//
//
//
//
////        Network.router().getLog(parentingActivity.isRemote, numberPerLoad, loadedNumber,
////                new Network.NetworkCallbackOne<List<LogRecord>>() {
////                    @Override
////                    public void onSuccess(List<LogRecord> arg) {
////                        isLoading = false;
////                        if (arg.size() != 0) {
////                            loadedNumber += arg.size();
////                            logRecords.addAll(arg);
////                            adapter.notifyItemRangeInserted(loadedNumber - arg.size(), arg.size());
////                        } else if (loadedNumber == 0) {
////                            emptyView.setVisibility(View.VISIBLE);
////                        }
////                    }
////
////                    @Override
////                    public void onFailure(@Nullable Throwable throwable) {
////                        isLoading = false;
////                        // TODO: Process failure
////                    }
////                });
//    }
//}