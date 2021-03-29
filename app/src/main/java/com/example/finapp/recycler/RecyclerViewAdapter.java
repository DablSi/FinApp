package com.example.finapp.recycler;

import android.content.Context;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import com.example.finapp.R;
import com.example.finapp.ui.main.FavouriteFragment;
import com.example.finapp.ui.main.MainActivity;
import com.example.finapp.ui.main.StocksFragment;
import com.example.finapp.utils.Network;
import com.example.finapp.utils.Toolbox;
import com.google.android.material.card.MaterialCardView;

import java.util.LinkedList;

public class RecyclerViewAdapter extends RecyclerAdapter {
    public LinkedList<StockRecord> dataset;
    public LinkedList<StockRecord> filteredDataset;
    private final Context context;

    public RecyclerViewAdapter(Context context, RecyclerView recyclerView, LinkedList<StockRecord> dataset, boolean isFavourite) {
        super(recyclerView, isFavourite);
        itemLayout = R.layout.fragment_stock;
        this.context = context;
        this.dataset = dataset;
    }

    @Override
    void fillItemWithData(ViewGroup item, int position) {
        StockRecord log = filteredDataset.get(position);

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
        TextView diffView = item.findViewById(R.id.diff);
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
        imageViewIcon.setOnClickListener(view -> {
            try {
                StockRecord newLog = new StockRecord(log);
                newLog.setFavorite(!log.isFavorite());
                int position1 = Toolbox.findStock(StocksFragment.adapter.dataset, companyTicket);
                StocksFragment.adapter.dataset.set(position1, newLog);
                if (newLog.isFavorite()) {
                    imageViewIcon.setColorFilter(context.getResources().getColor(R.color.star));
                    FavouriteFragment.adapter.dataset.add(newLog);
                } else {
                    imageViewIcon.setColorFilter(context.getResources().getColor(R.color.badStar));
                    int pos = Toolbox.findStock(FavouriteFragment.adapter.dataset, companyTicket);
                    FavouriteFragment.adapter.dataset.remove(pos);
                }
                FavouriteFragment.adapter.filteredDataset = FavouriteFragment.adapter.dataset;
                StocksFragment.adapter.getFilter().filter(MainActivity.query);
                FavouriteFragment.adapter.getFilter().filter(MainActivity.query);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        fadeAddAnimate(item, position % StocksFragment.numberPerLoad);
    }

    @Override
    public int getItemCount() {
        if (filteredDataset == null) return 0;
        return filteredDataset.size();
    }
}