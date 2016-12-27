package com.pingstart.mediation;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.pingstart.adsdk.PingStartSDK;
import com.pingstart.adsdk.listener.VideoListener;
import com.pingstart.adsdk.mediation.PingStartVideo;
import com.pingstart.adsdk.model.PingStartReward;


public class MainActivity extends Activity {
    private PingStartVideo mVideo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        PingStartSDK.initializeSdk(this, "5079");
        initView();
        showVideo();
    }

    private void initView() {
        findViewById(R.id.show_banner_ad).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, BannerActivity.class));
            }
        });

        findViewById(R.id.show_inter_ad).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, InterActivity.class));
            }
        });

        findViewById(R.id.show_native_ad).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, NativeActivity.class));
            }
        });

        findViewById(R.id.show_multi_ad).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, MultiActivity.class));
            }
        });

        findViewById(R.id.show_search_ad).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, HotWordActivity.class));
            }
        });

        findViewById(R.id.show_video_ad).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mVideo.isVideoAvailable()) {
                    mVideo.show();
                } else {
                    mVideo.reLoadVideo();
                }
            }
        });
    }


    private void showVideo() {
        mVideo = PingStartVideo.getInstance(this, "5079", "1001684");
        mVideo.loadAd();
        mVideo.setVideoListener(new VideoListener() {
            @Override
            public void onAdClosed() {
                Toast.makeText(MainActivity.this, "video closed", Toast.LENGTH_SHORT).show();
                mVideo.destroy();
            }

            @Override
            public void onVideoStarted() {

            }

            @Override
            public void onVideoLoaded() {
                Toast.makeText(MainActivity.this, "video loaded", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onVideoRewarded(PingStartReward reward) {

            }

            @Override
            public void onAdError(String error) {
                Toast.makeText(MainActivity.this, "video error :" + error, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onAdClicked() {

            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
