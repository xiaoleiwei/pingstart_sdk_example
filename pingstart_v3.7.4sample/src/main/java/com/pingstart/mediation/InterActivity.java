package com.pingstart.mediation;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.pingstart.adsdk.listener.InterstitialListener;
import com.pingstart.adsdk.mediation.PingStartInterstitial;

/**
 * Created by base on 2016/6/1.
 */
public class InterActivity extends Activity {

    private static final String TAG = InterActivity.class.getSimpleName();

    private PingStartInterstitial mInter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i(TAG, "onCreate");
        setContentView(R.layout.activity_inter);

        showInterAD();

        findViewById(R.id.btn_inter_ad).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null == mInter) {
                    showInterAD();
                }
            }
        });
    }

    private void showInterAD() {
        mInter = new PingStartInterstitial(this, "1000221");
        //true/false:if supportVideo
        mInter.setSupportVideo(true);
        mInter.setAdListener(new InterstitialListener() {
            @Override
            public void onAdClosed() {
                if (mInter != null) {
                    mInter.destroy();
                    mInter = null;
                }
            }

            @Override
            public void onAdError(String error) {
                Log.i(TAG, "  interErro");
            }

            @Override
            public void onAdLoaded() {
                Log.i(TAG, "Inter onAdLoaded");
                mInter.showAd();
            }

            @Override
            public void onAdClicked() {
                Log.i(TAG, "interClick");
            }
        });
        mInter.loadAd();
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mInter != null) {
            mInter.destroy();
            mInter = null;
        }
    }
}
