package com.pingstart.mediation;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.pingstart.adsdk.MRAIDBanner;
import com.pingstart.adsdk.listener.MRAIDBannerListener;

public class MraidBannerActivity extends AppCompatActivity {

    private ViewGroup mBannerContainer;
    private MRAIDBanner mMraidBanner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mraid_banner);
        mBannerContainer = (ViewGroup) findViewById(R.id.banner_container);
        findViewById(R.id.btn_mraid_banner).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadBanner();
            }
        });

    }


    private void loadBanner() {
        mBannerContainer.removeAllViews();
        if (mMraidBanner == null) {
            mMraidBanner = new MRAIDBanner(this, "56");
        } else {
            mMraidBanner.destroy();
        }
        mMraidBanner.setMraidAdListener(new MRAIDBannerListener() {
            @Override
            public void mraidBannerLoaded(View view) {
                mBannerContainer.removeAllViews();
                mBannerContainer.addView(view);
            }

            @Override
            public void mraidBannerClicked() {
                showToast("mraidBannerClicked");
            }

            @Override
            public void mraidBannerError(String s) {
                showToast("mraidBannerError:" + s);
            }
        });
        mMraidBanner.loadAd();
    }


    private void showToast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mMraidBanner.destroy();
        mMraidBanner = null;
    }
}
