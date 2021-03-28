package com.example.finapp.recycler;
import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import androidx.core.widget.NestedScrollView;
import com.example.finapp.Toolbox;

public class StockScrollView extends NestedScrollView {
    private Toolbox.Callback bottomCallback;
    private View bottomView;

    public void setBottomCallback(Toolbox.Callback bottomCallback) {
        this.bottomCallback = bottomCallback;
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        if (bottomView == null) bottomView = getChildAt(getChildCount() - 1);

        // If difference is zero, then the bottom has been reached
        int difference = (bottomView.getBottom() - getHeight() - getScrollY());
        if (difference == 0 && bottomCallback != null) bottomCallback.invoke();

        super.onScrollChanged(l, t, oldl, oldt);
    }

    public StockScrollView(Context context) {
        super(context);
    }
    public StockScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }
    public StockScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
}