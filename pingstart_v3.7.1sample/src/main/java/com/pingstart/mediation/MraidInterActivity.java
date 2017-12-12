package com.pingstart.mediation;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.pingstart.adsdk.MRAIDInterstitial;
import com.pingstart.adsdk.listener.MRAIDInterstitialListener;

public class MraidInterActivity extends AppCompatActivity {

    private MRAIDInterstitial mMraidInter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mraid_inter);
        findViewById(R.id.btn_mraid_inter).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadInter();
            }
        });
        loadInter();
    }

    private void loadInter() {
        if (mMraidInter == null) {
            mMraidInter = new MRAIDInterstitial(this, "57");
        } else {
            mMraidInter.destroy();
        }
        mMraidInter.setMraidAdListener(new MRAIDInterstitialListener() {
            @Override
            public void mraidInterstitialLoaded() {
                mMraidInter.show();
            }

            @Override
            public void mraidInterstitialClose() {
                mMraidInter.destroy();
            }

            @Override
            public void mraidInterstitialClicked() {
                showToast("mraidInterstitialClicked");
            }

            @Override
            public void mraidInterstitialError(String s) {
                showToast("mraidInterstitialError:" + s);
            }
        });
        mMraidInter.loadAd();
    }

    private void showToast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mMraidInter != null) {
            mMraidInter.destroy();
            mMraidInter = null;
        }
    }
}
