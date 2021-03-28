package com.example.finapp;

import android.content.Context;
import android.util.Log;
import android.widget.ImageView;
import com.example.finapp.recycler.RecyclerViewAdapter;
import com.squareup.picasso.Picasso;
import yahoofinance.Stock;
import yahoofinance.YahooFinance;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.LinkedList;
import java.util.List;

public class Network {
    private static final String[] symbols = new String[]{"INTC", "BABA", "TSLA", "AIR.PA", "YHOO", "QS", "SPRT", "RLX", "TKAT", "BNTC", "YVR", "JFIN", "ODT", "UPST", "CAN", "AMTX", "GME", "BTC-USD", "ORMP", "IONS", "ZEAL.CO", "BIDU", "SBRA", "MARA", "VOLV-B.ST", "SPFR", "MIOTA-USD", "NUAN", "ETH-USD", "KPN.AS", "APPN", "IO", "HIMX", "BTC-EUR", "BRQS", "VWAGY", "RUN", "TWST", "XRP-USD", "MYSZ", "IMTE", "TIGR", "OCG", "ZEAL", "JD", "A17U.SI", "DNMR", "FUTU", "MSFT", "DFIFF", "ATOS", "RIOT", "0175.HK", "TKC", "STON", "BTC-GBP", "HCMC", "CLPS", "C31.SI", "DOCU", "UVXY", "DT", "SOS", "DOYU", "TRIP", "REGN", "LKNCY", "ZI", "PAH3.DE", "AMC", "RTY=F", "PING", "DADA", "RACE", "AVEO", "RACE.MI", "AZN", "DNORD.CO", "AMRS", "VIAC", "BOX", "APPS", "VGT", "BOXL", "^GSPC", "SHIP", "3690.HK", "ETH-EUR", "BILI", "ICBP.JK", "DIXON.NS", "CSCO", "SPWR", "JBLU", "PBW", "^VIX", "VWAPY", "ADBE", "^HSI", "UNP", "AUDUSD=X", "NMTR", "WKEY", "LIZI", "SNAP", "ORSTED.CO", "VXX", "NOVO-B.CO", "EBON", "2LY.F", "FTCV", "^IXIC", "W", "BTC-CAD", "AYRO", "PDD", "MAERSK-B.CO", "1024.HK", "XRP-EUR", "RUB=X", "AC.TO", "WATT", "NEL.OL", "ROG.SW", "M", "ADA-USD", "CLSK", "NPA", "FTFT", "BYND", "VOW3.DE", "MRVL", "TZA", "PLL", "HITI.V", "SPY", "DMTK", "BNB-USD", "CIIC", "C38U.SI", "TCEHY", "MSTR", "ASML.AS", "BBBY", "WWR", "DVAX", "AVGR", "GBPUSD=X", "RIG", "C6L.SI", "HITIF", "BAYN.DE", "CURI", "DQ", "CVAC", "^OMX", "PM", "AMAT", "SAP.DE", "APXT", "ETH-CAD", "SQQQ", "TOT", "NQ=F", "NCTY", "SRAC", "WISH", "TME", "ACB", "EURUSD=X", "CAT", "0388.HK", "ARB.L", "0700.HK", "LINK-USD", "RBLX", "SMH", "ITP", "IQQH.DE", "LULU", "GBPHKD=X", "TSNPD"};
    private static int last = 0;
    static String url = "https://finnhub.io/api/logo?symbol=";

    public static void updateStocksList() {
        if (RecyclerViewAdapter.dataset == null) RecyclerViewAdapter.dataset = new LinkedList<>();

        Runnable runnable = new Runnable() {
            public void run() {
                for (String s : symbols) {
                    try {
                        RecyclerViewAdapter.dataset.add(new StockRecord(YahooFinance.get(s)));
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
                .into(imageView);
    }

    public static void loadMoreStocks(int numberPerLoad) {
        if (RecyclerViewAdapter.dataset == null) RecyclerViewAdapter.dataset = new LinkedList<>();

        Runnable runnable = new Runnable() {
            public void run() {
                for (int i = last; i < (last + numberPerLoad); i++) {
                    if (i >= symbols.length) break;
                    try {
                        RecyclerViewAdapter.dataset.add(new StockRecord(YahooFinance.get(symbols[i])));
                    } catch (IOException e) {
                        Log.e("Network", symbols[i] + " is missing");
                    }
                }
                last += numberPerLoad;
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
}
