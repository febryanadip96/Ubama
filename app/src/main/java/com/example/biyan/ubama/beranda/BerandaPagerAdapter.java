package com.example.biyan.ubama.beranda;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * Created by Biyan on 11/16/2017.
 */
public class BerandaPagerAdapter extends FragmentPagerAdapter {

    public BerandaPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        if (position == 0) {
            return BerandaFragment.newInstance();
        } else if (position == 1) {
            return FeedFragment.newInstance();
        } else {
            return FavoritFragment.newInstance();
        }
    }

    @Override
    public int getCount() {
        return 3;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        if (position == 0) {
            return "Beranda";
        } else if (position == 1) {
            return "Feed";
        } else {
            return "Favorit";
        }
    }
}
