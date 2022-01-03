package com.example.book_app_store.Adapters;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.book_app_store.Fragments.My_Ads_fragment;
import com.example.book_app_store.Fragments.My_Favourite_Ads_fragment;

public class Fragment_Loads_Adapter extends FragmentPagerAdapter {
    public Fragment_Loads_Adapter(@NonNull FragmentManager fm) {
        super(fm);
    }

    public Fragment_Loads_Adapter(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0:
                return new My_Ads_fragment();
            case 1:
                return new My_Favourite_Ads_fragment();
            default:
                return new My_Ads_fragment();
        }

    }

    @Override
    public int getCount() {
        return 2;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {

        String title = "";
        if (position == 0) {
            title = "My Ads";
        }
        if (position == 1) {
            title = "Favourite Ads";
        }

        return title;
    }
}
