package com.muhlenberg.dining;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.muhlenberg.dining.fragments.FridayFragment;
import com.muhlenberg.dining.fragments.MondayFragment;
import com.muhlenberg.dining.fragments.SaturdayFragment;
import com.muhlenberg.dining.fragments.SundayFragment;
import com.muhlenberg.dining.fragments.ThursdayFragment;
import com.muhlenberg.dining.fragments.TuesdayFragment;
import com.muhlenberg.dining.fragments.WednesdayFragment;
 
public class TabsPagerAdapter extends FragmentPagerAdapter {
 
    public TabsPagerAdapter(FragmentManager fm) {
        super(fm);
    }
 
    @Override
    public Fragment getItem(int index) {
 
        switch (index) {
        case 0:
            return new MondayFragment();
        case 1:
            return new TuesdayFragment();
        case 2:
            return new WednesdayFragment();
        case 3:
            return new ThursdayFragment();
        case 4:
            return new FridayFragment();
        case 5:
            return new SaturdayFragment();
        case 6:
            return new SundayFragment();
        }
 
        return null;
    }

    /**
     * returns number of tabs
     */
    @Override
    public int getCount() {
        return 7;
    }
}