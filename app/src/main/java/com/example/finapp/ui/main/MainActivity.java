package com.example.finapp.ui.main;

import android.app.SearchManager;
import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.core.content.ContextCompat;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;
import com.example.finapp.R;
import com.google.android.material.card.MaterialCardView;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    public TabLayout tabLayout;
    public static String query = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        NestedScrollView scrollView = findViewById(R.id.nest_scrollview);
        scrollView.setFillViewport(true);

        setupPager();
        setupSearch();
    }

    private void setupPager() {
        List<Class> fragmentTypes = new ArrayList<Class>() {{
            add(StocksFragment.class);
            add(FavouriteFragment.class);
        }};

        FragmentPagerAdapter adapter =
                new SectionsPagerAdapter(getSupportFragmentManager(), fragmentTypes, this);
        ViewPager viewPager = findViewById(R.id.view_pager);
        viewPager.setAdapter(adapter);

        tabLayout = findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);

        for (int i = 0; i < tabLayout.getTabCount(); i++) {
            TabLayout.Tab tab = tabLayout.getTabAt(i);
            if (tab != null) {
                TextView tabTextView = new TextView(this);
                tab.setCustomView(tabTextView);
                tabTextView.getLayoutParams().height = ViewGroup.LayoutParams.WRAP_CONTENT;
                tabTextView.setTypeface(Typeface.DEFAULT_BOLD);
                tabTextView.setText(tab.getText());
                tabTextView.setTextAlignment(View.TEXT_ALIGNMENT_GRAVITY);
                if (i == 0) {
                    tabTextView.setTextSize(28);
                    tabTextView.getLayoutParams().width = dpToPx(116, getApplicationContext());
                } else {
                    tabTextView.setTextSize(18);
                    tabTextView.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.colorSecondaryText));
                    tabTextView.getLayoutParams().width = dpToPx(130, getApplicationContext());
                    tabTextView.setPadding(4, 16, 0, 0);
                }
            }
        }
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                ViewGroup vg = (ViewGroup) tabLayout.getChildAt(0);
                ViewGroup vgTab = (ViewGroup) vg.getChildAt(tab.getPosition());
                int tabChildsCount = vgTab.getChildCount();
                for (int i = 0; i < tabChildsCount; i++) {
                    View tabViewChild = vgTab.getChildAt(i);
                    if (tabViewChild instanceof TextView) {
                        ((TextView) tabViewChild).setTextSize(28);
                        ((TextView) tabViewChild).setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.colorPrimaryText));
                        tabViewChild.setPadding(tabViewChild.getPaddingLeft(), 0, 0, 0);
                    }
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                ViewGroup vg = (ViewGroup) tabLayout.getChildAt(0);
                ViewGroup vgTab = (ViewGroup) vg.getChildAt(tab.getPosition());
                int tabChildsCount = vgTab.getChildCount();
                for (int i = 0; i < tabChildsCount; i++) {
                    View tabViewChild = vgTab.getChildAt(i);
                    if (tabViewChild instanceof TextView) {
                        ((TextView) tabViewChild).setTextSize(18);
                        ((TextView) tabViewChild).setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.colorSecondaryText));
                        tabViewChild.setPadding(tabViewChild.getPaddingLeft(), 16, 0, 0);
                    }
                }
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                onTabSelected(tab);
            }
        });
    }

    private void setupSearch() {
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView = findViewById(R.id.search);
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setIconifiedByDefault(false);
        MaterialCardView materialCardView = findViewById(R.id.search_card);

        searchView.setOnQueryTextFocusChangeListener((view, b) -> {
            if (b) materialCardView.setStrokeWidth(dpToPx(2, MainActivity.this));
            else materialCardView.setStrokeWidth(dpToPx(1, MainActivity.this));
        });

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String newText) {
                query = newText;
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                query = newText;
                StocksFragment.adapter.getFilter().filter(newText);
                FavouriteFragment.adapter.getFilter().filter(newText);
                return false;
            }
        });
    }

    public static int dpToPx(int dp, Context context) {
        float density = context.getResources().getDisplayMetrics().density;
        return Math.round((float) dp * density);
    }
}