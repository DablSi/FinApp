package com.example.finapp.ui.main;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import com.example.finapp.R;

import java.util.List;
import java.util.Objects;

/**
 * A [FragmentPagerAdapter] that returns a fragment corresponding to
 * one of the sections/tabs/pages.
 */
public class SectionsPagerAdapter extends FragmentPagerAdapter {

    @StringRes
    private static final int[] TAB_TITLES = new int[]{R.string.tab_text_1, R.string.tab_text_2};
    private final Context mContext;

    private final List<Class> mFragmentTypes;

    public SectionsPagerAdapter(
            FragmentManager fm,
            List<Class> fragmentTypes,
            Context mContext) {
        super(fm);
        this.mContext = mContext;
        this.mFragmentTypes = fragmentTypes;
    }

    @Override
    public int getCount() {

        if (mFragmentTypes != null) {
            return mFragmentTypes.size();
        }

        return 0;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {

        Fragment fragment = null;

        if (mFragmentTypes != null &&
                position >= 0 &&
                position < mFragmentTypes.size()) {

            Class c = mFragmentTypes.get(position);

            try {
                fragment = (Fragment) Class.forName(c.getName()).newInstance();
            } catch (Exception ex) {
                // TODO: log the error
            }
        }

        return Objects.requireNonNull(fragment);
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return mContext.getResources().getString(TAB_TITLES[position]);
    }

}