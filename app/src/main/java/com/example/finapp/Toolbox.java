package com.example.finapp;

import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.LinkedList;

/**
 * This class contains all kinds of tools
 * that could be useful during development.
 */
public class Toolbox {
    /**
     * Finds the last child of given parent.
     * This child is ether last inflated or simply last in layout.
     *
     * @param parent {@link ViewGroup} which's child to find
     * @return Last child {@link View} or null if parent has no children.
     */

    private static final String LOG_TAG = "CAREPHONE";
    private static final boolean DOLOG = true;

    private static final String PREFS_IS_FIRST_LAUNCH = "is_first_launch";

    @Nullable
    public static View getLastChild(@NonNull ViewGroup parent) {
        for (int i = parent.getChildCount() - 1; i >= 0; i--) {
            View child = parent.getChildAt(i);

            if (child != null) return child;
        }
        return null;
    }

    public static int findStock(LinkedList<StockRecord> linkedList, String ticker) {
        for (int i = 0; i < linkedList.size(); i++) {
            if (linkedList.get(i).getCompanyTicket().equals(ticker))
                return i;
        }
        return -1;
    }

    // Several general callbacks

    public interface Callback {
        void invoke();
    }
    public interface CallbackOne<T> {
        void invoke(T arg);
    }
    public interface CallbackState {
        void invoke();
        void fail();
    }
    public interface CallbackStateOne<T> {
        void invoke(T arg);
        void fail();
    }
}