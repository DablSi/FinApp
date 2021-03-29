package com.example.finapp;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.core.widget.NestedScrollView;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;
import com.example.finapp.databinding.ActivityMainBinding;
import com.example.finapp.ui.main.FavouriteFragment;
import com.example.finapp.ui.main.SectionsPagerAdapter;
import com.example.finapp.ui.main.StocksFragment;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    public TabLayout tabLayout;
    ActivityMainBinding activityMainBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityMainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        CollapsingToolbarLayout toolBarLayout = findViewById(R.id.toolbar_layout);

        NestedScrollView scrollView = findViewById(R.id.nest_scrollview);
        scrollView.setFillViewport(true);

        setPager();

        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView = findViewById(R.id.search);
        // Assumes current activity is the searchable activity
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setIconifiedByDefault(false); // Do not iconify the widget; expand it by default

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                StocksFragment.adapter.getFilter().filter(newText);
                FavouriteFragment.adapter.getFilter().filter(newText);
                return false;
            }
        });
    }

    private void setPager() {
        List<Class> fragmentTypes = new ArrayList<Class>() {{
            add(StocksFragment.class);
            add(FavouriteFragment.class);
        }};

        FragmentPagerAdapter adapter =
                new SectionsPagerAdapter(getSupportFragmentManager(), fragmentTypes, this);
        // Получаем ViewPager и устанавливаем в него адаптер
        ViewPager viewPager = findViewById(R.id.view_pager);
        viewPager.setAdapter(adapter);

        // Передаём ViewPager в TabLayout
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
                    tabTextView.setPadding(4, 0, 0, 0);
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
                        //tabViewChild.getLayoutParams().width = ViewGroup.LayoutParams.WRAP_CONTENT;
                    }
                }
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                onTabSelected(tab);
            }
        });
    }

    public static int dpToPx(int dp, Context context) {
        float density = context.getResources().getDisplayMetrics().density;
        return Math.round((float) dp * density);
    }
}