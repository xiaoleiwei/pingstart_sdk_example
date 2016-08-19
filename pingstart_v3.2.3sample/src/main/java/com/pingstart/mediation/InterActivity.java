package com.pingstart.mediation;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

import com.pingstart.adsdk.listener.InterstitialListener;
import com.pingstart.adsdk.mediation.PingStartInterstitial;
import com.pingstart.adsdk.utils.LogUtils;

/**
 * Created by base on 2016/6/1.
 */
public class InterActivity extends Activity {

    private static final String TAG = InterActivity.class.getSimpleName();

    private PingStartInterstitial mInterManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LogUtils.i(TAG, "onCreate");
        setContentView(R.layout.activity_inter);

        showInterAD();

        findViewById(R.id.btn_inter_ad).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null == mInterManager) {
                    showInterAD();
                }
            }
        });
    }

    private void showInterAD() {
        mInterManager = new PingStartInterstitial(this, "5079", "1000221");
        mInterManager.setAdListener(new InterstitialListener() {
            @Override
            public void onAdClosed() {
                if (mInterManager != null) {
                    mInterManager.destroy();
                    mInterManager = null;
                }
            }

            @Override
            public void onAdError(String error) {
                LogUtils.i(TAG, "  interErro");
            }

            @Override
            public void onAdLoaded() {
                LogUtils.i(TAG, "Inter onAdLoaded");
                mInterManager.showAd();
            }

            @Override
            public void onAdClicked() {
                LogUtils.i(TAG, "interClick");
            }
        });
        mInterManager.loadAd();
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mInterManager != null) {
            mInterManager.destroy();
            mInterManager = null;
        }
    }
}
