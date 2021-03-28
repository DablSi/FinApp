package com.example.finapp;

import android.content.Context;
import android.util.Log;
import android.widget.ImageView;
import com.example.finapp.recycler.RecyclerViewAdapter;
import com.squareup.picasso.Callback;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;
import yahoofinance.Stock;
import yahoofinance.YahooFinance;

import java.io.*;
import java.math.BigDecimal;
import java.util.LinkedList;
import java.util.List;

public class Network {
    private static final String[] symbols = new String[]{"INTC", "BABA", "TSLA", "AIR.PA", "QS", "SPRT", "RLX", "TKAT", "BNTC", "YVR", "JFIN", "ODT", "UPST", "CAN", "AMTX", "GME", "BTC-USD", "ORMP", "IONS", "ZEAL.CO", "BIDU", "SBRA", "MARA", "VOLV-B.ST", "SPFR", "MIOTA-USD", "NUAN", "ETH-USD", "KPN.AS", "APPN", "IO", "HIMX", "BTC-EUR", "BRQS", "VWAGY", "RUN", "TWST", "XRP-USD", "MYSZ", "IMTE", "TIGR", "OCG", "ZEAL", "JD", "A17U.SI", "DNMR", "FUTU", "MSFT", "DFIFF", "ATOS", "RIOT", "0175.HK", "TKC", "STON", "BTC-GBP", "HCMC", "CLPS", "C31.SI", "DOCU", "UVXY", "DT", "SOS", "DOYU", "TRIP", "REGN", "LKNCY", "ZI", "PAH3.DE", "AMC", "RTY=F", "PING", "DADA", "RACE", "AVEO", "RACE.MI", "AZN", "DNORD.CO", "AMRS", "VIAC", "BOX", "APPS", "BOXL", "^GSPC", "SHIP", "3690.HK", "ETH-EUR", "BILI", "ICBP.JK", "DIXON.NS", "CSCO", "SPWR", "JBLU", "PBW", "^VIX", "VWAPY", "ADBE", "^HSI", "UNP", "AUDUSD=X", "NMTR", "WKEY", "LIZI", "SNAP", "ORSTED.CO", "VXX", "NOVO-B.CO", "EBON", "2LY.F", "FTCV", "^IXIC", "W", "BTC-CAD", "AYRO", "PDD", "MAERSK-B.CO", "1024.HK", "XRP-EUR", "RUB=X", "AC.TO", "WATT", "NEL.OL", "ROG.SW", "M", "ADA-USD", "CLSK", "NPA", "FTFT", "BYND", "VOW3.DE", "MRVL", "TZA", "PLL", "HITI.V", "SPY", "DMTK", "BNB-USD", "CIIC", "C38U.SI", "TCEHY", "MSTR", "ASML.AS", "BBBY", "WWR", "DVAX", "AVGR", "GBPUSD=X", "RIG", "C6L.SI", "HITIF", "BAYN.DE", "CURI", "DQ", "CVAC", "^OMX", "PM", "AMAT", "SAP.DE", "APXT", "ETH-CAD", "SQQQ", "TOT", "NQ=F", "NCTY", "SRAC", "WISH", "TME", "ACB", "EURUSD=X", "CAT", "0388.HK", "ARB.L", "0700.HK", "LINK-USD", "RBLX", "SMH", "ITP", "IQQH.DE", "LULU", "GBPHKD=X", "TSNPD"};
    static String url = "https://finnhub.io/api/logo?symbol=";
    private static File cacheDir;
    private static File cacheFavDir;

    public static void updateStocksList(LinkedList<StockRecord> dataset) {
        if (dataset == null) dataset = new LinkedList<>();

        LinkedList<StockRecord> finalDataset = dataset;
        Runnable runnable = new Runnable() {
            public void run() {
                for (String s : symbols) {
                    try {
                        finalDataset.add(new StockRecord(YahooFinance.get(s), false));
                    } catch (IOException e) {
                        Log.e("Network", s + " is missing");
                    }
                }
            }
        };
        Thread thread = new Thread(runnable);
        thread.start();
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void setImage(Context context, ImageView imageView, String ticker) {
        Picasso.with(context)
                .load(url + ticker)
                .networkPolicy(NetworkPolicy.OFFLINE)
                .into(imageView, new Callback() {
                    @Override
                    public void onSuccess() {
                        Log.v("Caching", "Downloaded from cache successfully");
                    }

                    @Override
                    public void onError() {
                        //Try again online if cache failed
                        Picasso.with(context)
                                .load(url + ticker)
                                .into(imageView, new Callback() {
                                    @Override
                                    public void onSuccess() {
                                        Log.v("Caching", "Downloaded from internet successfully");
                                    }

                                    @Override
                                    public void onError() {
                                        Log.v("Picasso", "Could not fetch image");
                                    }
                                });
                    }
                });
    }

    public static boolean loadMoreStocks(int numberPerLoad, LinkedList<StockRecord> dataset) {
        if (dataset == null) dataset = new LinkedList<>();

        LinkedList<StockRecord> finalDataset = dataset;
        final boolean[] out = {true};
        Runnable runnable = new Runnable() {
            public void run() {
                int size = finalDataset.size();
                for (int i = size; i < (size + numberPerLoad); i++) {
                    if (i >= symbols.length) break;
                    try {
                        finalDataset.add(new StockRecord(YahooFinance.get(symbols[i]), false));
                    } catch (IOException e) {
                        Log.e("Network", symbols[i] + " is missing");
                    }
                }
                if (finalDataset.size() == size) out[0] = false;
            }
        };
        Thread thread = new Thread(runnable);
        thread.start();
        try {

            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return out[0];
    }

    public static void writeToCache(LinkedList<StockRecord> dataset, boolean fav) {
        try {
            File dir;
            if (fav) dir = cacheFavDir;
            else dir = cacheDir;

            ObjectOutputStream oo = new ObjectOutputStream(new FileOutputStream(dir));
            oo.writeObject(dataset);
            oo.close();
        } catch (Exception ioe) {
            ioe.printStackTrace();
        }
    }

    public static void readFromCache(Context context, RecyclerViewAdapter adapter, boolean fav) {
        try {
            cacheDir = new File(context.getFilesDir().getPath().toString() + "/save.ser");
            cacheFavDir = new File(context.getFilesDir().getPath().toString() + "/saveFav.ser");
            if (!cacheDir.exists()) {
                cacheDir.createNewFile();
                cacheFavDir.createNewFile();
            }

            File dir;
            if (fav) dir = cacheFavDir;
            else dir = cacheDir;

            ObjectInputStream oi = new ObjectInputStream(new FileInputStream(dir));
            Object cached = oi.readObject();
            adapter.dataset = (LinkedList<StockRecord>) cached;
            oi.close();
        } catch (Exception exc) {
            exc.printStackTrace();
        }
    }
}
