package com.example.finapp.recycler;

import android.content.Context;
import android.content.res.Resources;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import com.example.finapp.Network;
import com.example.finapp.R;
import com.example.finapp.StockRecord;
import com.example.finapp.ui.main.FragmentOne;

import java.text.SimpleDateFormat;
import java.util.LinkedList;
import java.util.List;

public class RecyclerViewAdapter extends RecyclerAdapter {
    private Resources res;
    public LinkedList<StockRecord> dataset;
    private Context context;

    public RecyclerViewAdapter(Context context, RecyclerView recyclerView, LinkedList<StockRecord> dataset) {
        super(recyclerView);
        itemLayout = R.layout.fragment_stock;
        res = recyclerView.getResources();
        this.context = context;
        this.dataset = dataset;
    }

    @Override
    void fillItemWithData(ViewGroup item, int position) {
        StockRecord log = dataset.get(position);

        // TextViews
        String companyTicket = log.getCompanyTicket();
        ((TextView) item.findViewById(R.id.ticket)).setText(companyTicket);

        String companyName = log.getCompanyName();
        ((TextView) item.findViewById(R.id.name)).setText(companyName);

        String price = log.getPrice();
        ((TextView) item.findViewById(R.id.price)).setText(price);

        String diff = log.getDifference();
        ((TextView) item.findViewById(R.id.diff)).setText(diff);

        // Icon
        ImageView iconImage = item.findViewById(R.id.icon);
        Network.setImage(context, iconImage, log.getCompanyTicket());

        fadeAddAnimate(item, position % FragmentOne.numberPerLoad);
    }

    @Override
    public int getItemCount() {
        if (dataset == null) return 0;
        return dataset.size();
    }
}