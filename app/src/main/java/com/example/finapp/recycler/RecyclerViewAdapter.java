package com.example.finapp.recycler;

import android.content.res.Resources;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;
import com.example.finapp.R;
import com.example.finapp.StockRecord;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

public class RecyclerViewAdapter extends RecyclerAdapter {
    private static final String dateFormatString = "HH:mm dd LLLL";
    private Resources res;
    private List<StockRecord> dataset;
    private SimpleDateFormat dateFormat;

    public RecyclerViewAdapter(RecyclerView recyclerView, List<StockRecord> dataset) {
        super(recyclerView);
        itemLayout = R.layout.fragment_stock;
        res = recyclerView.getResources();

        this.dataset = dataset;
        dateFormat = new SimpleDateFormat(dateFormatString, Locale.getDefault());
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

        // Only for last element
//        if (position == getItemCount() - 1)
//            item.findViewById(R.id.recycler_divider).setVisibility(View.GONE);

//        fadeAddAnimate(item, position % StockFragment.numberPerLoad);
    }

    @Override
    public int getItemCount() {
        return dataset.size();
    }
}