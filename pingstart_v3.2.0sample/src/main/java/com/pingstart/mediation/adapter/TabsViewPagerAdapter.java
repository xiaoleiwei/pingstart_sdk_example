package com.pingstart.mediation.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.pingstart.mediation.fragment.LoadAdBannerFragment;
import com.pingstart.mediation.fragment.LoadAdInterFragment;
import com.pingstart.mediation.fragment.LoadAdNativeFragment;
import com.pingstart.mediation.utils.DataUtils;

public class TabsViewPagerAdapter extends FragmentPagerAdapter {

    public TabsViewPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment = null;
        switch (position) {
            case DataUtils.AD_BANNER_FIRST:
                fragment = new LoadAdBannerFragment();
                break;
            case DataUtils.AD_INTERSTITIAL_SECOND:
                fragment = new LoadAdInterFragment();
                break;
            case DataUtils.AD_NATIVE_THIRD:
                fragment = new LoadAdNativeFragment();
                break;
            default:
                break;
        }
        return fragment;
    }

    @Override
    public int getCount() {
        return DataUtils.AD_FRAGMENT_COUNT;
    }

}
