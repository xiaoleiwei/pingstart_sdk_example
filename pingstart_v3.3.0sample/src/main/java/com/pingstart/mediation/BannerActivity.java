package com.pingstart.mediation;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;

import com.pingstart.adsdk.listener.BannerListener;
import com.pingstart.adsdk.mediation.PingStartBanner;
import com.pingstart.adsdk.utils.LogUtils;

/**
 * Created by base on 2016/6/1.
 */
public class BannerActivity extends Activity {

    private static final String TAG = BannerActivity.class.getSimpleName();

    private PingStartBanner mBannerManager;
    private FrameLayout mBannerFrameLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LogUtils.i(TAG, "onCreate");

        setContentView(R.layout.activity_banner);

        findViewById(R.id.btn_banner_ad).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mBannerManager.destroy();
                mBannerManager.loadBanner();
            }
        });

        mBannerFrameLayout = (FrameLayout) findViewById(R.id.adver_layout);
        mBannerManager = new PingStartBanner(this, "5079", "1000220");
        mBannerManager.setAdListener(new BannerListener() {

            @Override
            public void onAdError(String error) {
                LogUtils.i(TAG, "  bannerErro");
            }

            @Override
            public void onAdLoaded(View view) {
                LogUtils.i(TAG, "Banner onAdLoaded");
                mBannerFrameLayout.removeAllViews();
                mBannerFrameLayout.addView(view);
            }

            @Override
            public void onAdClicked() {
                LogUtils.i(TAG, "bannerClick");
            }
        });
        mBannerManager.loadBanner();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mBannerManager != null) {
            mBannerManager.destroy();
            mBannerManager = null;
        }
    }
}
