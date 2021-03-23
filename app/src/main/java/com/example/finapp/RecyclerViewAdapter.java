//package com.example.finapp;
//
//import android.content.res.Resources;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.ImageView;
//import android.widget.TextView;
//
//import androidx.recyclerview.widget.RecyclerView;
//
//import java.text.SimpleDateFormat;
//import java.util.Date;
//import java.util.List;
//import java.util.Locale;
//
//public class RecyclerViewAdapter extends RecyclerAdapter {
//    private static final String dateFormatString = "HH:mm dd LLLL";
//    private Resources res;
//    private List<LogRecord> dataset;
//    private SimpleDateFormat dateFormat;
//
//    public RecyclerViewAdapter(RecyclerView recyclerView, List<LogRecord> dataset) {
//        super(recyclerView);
//        itemLayout = R.layout.recyclable_log_template;
//        res = recyclerView.getResources();
//
//        this.dataset = dataset;
//        dateFormat = new SimpleDateFormat(dateFormatString, Locale.getDefault());
//    }
//
//    @Override
//    void fillItemWithData(ViewGroup item, int position) {
//        LogRecord log = dataset.get(position);
//
//        String phoneNumber = log.getPhoneNumber().length() == 0
//                ? res.getString(R.string.log_unknown) : log.getPhoneNumber();
//        ((TextView) item.findViewById(R.id.recycler_phone)).setText(phoneNumber);
//
//        String startTimeText = dateFormat.format(new Date(log.getStartTimestamp()));
//        ((TextView) item.findViewById(R.id.recycler_date)).setText(startTimeText);
//
//        // Duration
//        String durationText = log.getSecondsDuration() == 0 ? res.getString(R.string.log_blocked)
//                : Toolbox.getShortStringFromSeconds(res, log.getSecondsDuration());
//        ((TextView) item.findViewById(R.id.recycler_duration)).setText(durationText);
//
//        // Icon
//        ImageView iconImage = item.findViewById(R.id.recycler_type_icon);
//
//        if (log.getType() == LogFragment.TYPE_INCOMING)
//            iconImage.setImageResource(R.drawable.ic_call_received);
//        else if (log.getType() == LogFragment.TYPE_OUTGOING)
//            iconImage.setImageResource(R.drawable.ic_call_made);
//        else if (log.getType() == LogFragment.TYPE_BLOCKED)
//            iconImage.setImageResource(R.drawable.ic_call_blocked);
//        else
//            iconImage.setImageResource(R.drawable.ic_close);
//
//        // Only for last element
//        if (position == getItemCount() - 1)
//            item.findViewById(R.id.recycler_divider).setVisibility(View.GONE);
//
//        fadeAddAnimate(item, position % LogFragment.numberPerLoad);
//    }
//
//    @Override
//    public int getItemCount() {
//        return dataset.size();
//    }
//}