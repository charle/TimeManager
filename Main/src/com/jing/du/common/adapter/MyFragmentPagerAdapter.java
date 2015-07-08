package com.jing.du.common.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by charle-chen on 15/6/23.
 */
public class MyFragmentPagerAdapter extends FragmentPagerAdapter {

    private List<Fragment> myFragmentList = new ArrayList<Fragment>();

    public MyFragmentPagerAdapter(FragmentManager fm, List<Fragment> myFragmentList) {
        super(fm);
        this.myFragmentList = myFragmentList;
    }

    @Override
    public Fragment getItem(int i) {
        if (myFragmentList != null) {
            return myFragmentList.get(i);
        }
        return null;
    }

    @Override
    public int getCount() {
        if (myFragmentList != null) {
            return myFragmentList.size();
        }
        return 0;
    }

}
