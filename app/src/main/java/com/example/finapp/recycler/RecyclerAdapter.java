package com.example.finapp.recycler;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import android.widget.Filter;
import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.finapp.R;
import com.example.finapp.StockRecord;
import com.example.finapp.ui.main.FavouriteFragment;
import com.example.finapp.ui.main.StocksFragment;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * This is an extension of Recycler view adapter.
 * It simplifies work with it by implementing inflation
 * of items and there storing. You should only write how
 * to fill item with content.
 * !!! You must set itemLayout variable to your items resource int !!!
 */
public abstract class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.BasicViewHolder> {
    @LayoutRes
    int itemLayout;
    LinkedList<StockRecord> stockList;
    private Context context;
    private boolean isFavourite = false;

    public RecyclerAdapter(RecyclerView recyclerView, boolean isFavourite) {
        context = recyclerView.getContext();
        this.isFavourite = isFavourite;
    }

    @NonNull
    @Override
    public BasicViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View item = SimpleInflater.inflate(parent, itemLayout, false);
        return new BasicViewHolder(item);
    }

    @Override
    public void onBindViewHolder(@NonNull final BasicViewHolder holder, final int position) {
        if (context == null) return;

        ViewGroup item = (ViewGroup) holder.itemView;
        fillItemWithData(item, position);
    }

    /**
     * Fill recycler item with your content here.
     *
     * @param item     Item to be filled.
     * @param position Index of item in recycler view.
     */
    abstract void fillItemWithData(ViewGroup item, int position);

    static class BasicViewHolder extends RecyclerView.ViewHolder {
        BasicViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }

    public interface OnRecyclerItemClick {
        void onClick(int index);
    }

    void fadeAddAnimate(View view, int position) {
        Animation animation = AnimationUtils.loadAnimation(view.getContext(), R.anim.fade_in);
        animation.setStartOffset(100 * position);
        view.startAnimation(animation);
    }

    ValueFilter valueFilter;

    @Override
    public long getItemId(int position) {
        return position;
    }

    public Filter getFilter() {
        if (valueFilter == null) {
            valueFilter = new ValueFilter();
        }
        return valueFilter;
    }

    private class ValueFilter extends Filter {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults results = new FilterResults();
            if (isFavourite)
                stockList = FavouriteFragment.adapter.dataset;
            else
                stockList = StocksFragment.adapter.dataset;

            if (constraint != null && constraint.length() > 0) {
                LinkedList<StockRecord> filterList = new LinkedList<StockRecord>();
                for (int i = 0; i < stockList.size(); i++) {
                    if ((stockList.get(i).getCompanyTicket().toLowerCase()).contains(constraint.toString().toLowerCase()) ||
                            (stockList.get(i).getCompanyName().toLowerCase()).contains(constraint.toString().toLowerCase())) {
                        filterList.add(stockList.get(i));
                    }
                }
                results.count = filterList.size();
                results.values = filterList;
            } else {
                results.count = stockList.size();
                results.values = stockList;
            }
            return results;

        }

        @Override
        protected void publishResults(CharSequence constraint,
                                      FilterResults results) {
            if (isFavourite)
                FavouriteFragment.adapter.filteredDataset = (LinkedList<StockRecord>) results.values;
            else StocksFragment.adapter.filteredDataset = (LinkedList<StockRecord>) results.values;
            notifyDataSetChanged();
        }

    }

}