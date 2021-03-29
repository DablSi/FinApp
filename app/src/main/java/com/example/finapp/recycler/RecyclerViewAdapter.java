package com.example.finapp.recycler;

import android.content.Context;
import android.content.res.Resources;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import com.example.finapp.Network;
import com.example.finapp.R;
import com.example.finapp.StockRecord;
import com.example.finapp.Toolbox;
import com.example.finapp.ui.main.StocksFragment;
import com.example.finapp.ui.main.FavouriteFragment;
import com.google.android.material.card.MaterialCardView;

import java.util.LinkedList;

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

        MaterialCardView cardView = item.findViewById(R.id.materialCardView);
        if (position % 2 != 0)
            cardView.setCardBackgroundColor(context.getResources().getColor(R.color.colorPrimary));

        // TextViews
        String companyTicket = log.getCompanyTicket();
        ((TextView) item.findViewById(R.id.ticket)).setText(companyTicket);

        String companyName = log.getCompanyName();
        ((TextView) item.findViewById(R.id.name)).setText(companyName);

        String price = log.getPrice();
        ((TextView) item.findViewById(R.id.price)).setText(price);

        String diff = log.getDifference();
        TextView diffView = (TextView) item.findViewById(R.id.diff);
        try {
            if (diff.charAt(0) == '-')
                diffView.setTextColor(context.getResources().getColor(R.color.negative));
            else {
                diff = "+" + diff;
                diffView.setTextColor(context.getResources().getColor(R.color.positive));
            }
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
        diffView.setText(diff);

        // Icon
        ImageView iconImage = item.findViewById(R.id.icon);
        Network.setImage(context, iconImage, log.getCompanyTicket());

        ImageButton imageViewIcon = item.findViewById(R.id.star);
        if (log.isFavorite())
            imageViewIcon.setColorFilter(context.getResources().getColor(R.color.star));
        imageViewIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    StockRecord newLog = new StockRecord(log);
                    newLog.setFavorite(!log.isFavorite());
                    int position = Toolbox.findStock(StocksFragment.adapter.dataset, companyTicket);
                    StocksFragment.adapter.dataset.set(position, newLog);
                    if (newLog.isFavorite()) {
                        imageViewIcon.setColorFilter(context.getResources().getColor(R.color.star));
                        FavouriteFragment.adapter.dataset.add(newLog);
                        int size = FavouriteFragment.adapter.dataset.size();
                        FavouriteFragment.adapter.notifyDataSetChanged();
                    } else {
                        imageViewIcon.setColorFilter(context.getResources().getColor(R.color.badStar));
                        int pos = Toolbox.findStock(FavouriteFragment.adapter.dataset, companyTicket);
                        FavouriteFragment.adapter.dataset.remove(pos);
                        FavouriteFragment.adapter.notifyDataSetChanged();
                    }
                    StocksFragment.adapter.notifyDataSetChanged();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        fadeAddAnimate(item, position % StocksFragment.numberPerLoad);
    }



    @Override
    public int getItemCount() {
        if (dataset == null) return 0;
        return dataset.size();
    }
}