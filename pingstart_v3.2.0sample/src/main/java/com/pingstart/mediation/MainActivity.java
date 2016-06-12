package com.pingstart.mediation;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.pingstart.mediation.adapter.TabsViewPagerAdapter;
import com.pingstart.mediation.utils.DataUtils;

public class MainActivity extends FragmentActivity implements OnClickListener, OnPageChangeListener {
    private ViewPager mViewPager;
    private TextView mBanner;
    private TextView mInterStitial;
    private TextView mNative;
    private ImageView ImageLineTab;
    private int mWidth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
        initDatas();
        initTabLine();
    }

    private void initTabLine() {
        Display display = getWindow().getWindowManager().getDefaultDisplay();
        DisplayMetrics outMetrics = new DisplayMetrics();
        display.getMetrics(outMetrics);
        mWidth = outMetrics.widthPixels / 3;
        LayoutParams layoutParams = ImageLineTab.getLayoutParams();
        layoutParams.width = mWidth;
        ImageLineTab.setLayoutParams(layoutParams);
    }

    private void initDatas() {
        mViewPager.setAdapter(new TabsViewPagerAdapter(getSupportFragmentManager()));
        mViewPager.setOnPageChangeListener(this);
    }

    private void initView() {
        mBanner = (TextView) findViewById(R.id.banner_ad);
        mInterStitial = (TextView) findViewById(R.id.interstitial_ad);
        mNative = (TextView) findViewById(R.id.native_ad);
        mViewPager = (ViewPager) findViewById(R.id.viewpager);
        ImageLineTab = (ImageView) findViewById(R.id.image_line_tab);
        mBanner.setOnClickListener(this);
        mInterStitial.setOnClickListener(this);
        mNative.setOnClickListener(this);
    }

    @Override
    public void onPageScrollStateChanged(int position) {

    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPx) {
        LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) ImageLineTab.getLayoutParams();
        layoutParams.leftMargin = (int) ((positionOffset + position) * mWidth);
        ImageLineTab.setLayoutParams(layoutParams);
    }

    @Override
    public void onPageSelected(int position) {
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.banner_ad:
                mViewPager.setCurrentItem(DataUtils.AD_BANNER_FIRST, false);
                break;
            case R.id.interstitial_ad:
                mViewPager.setCurrentItem(DataUtils.AD_INTERSTITIAL_SECOND, false);
                break;
            case R.id.native_ad:
                mViewPager.setCurrentItem(DataUtils.AD_NATIVE_THIRD, false);
                break;
            default:
                break;
        }
    }

}
