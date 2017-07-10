package com.pingstart.mediation;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.pingstart.adsdk.PingStartSDK;


public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        PingStartSDK.initializeSdk(this, "5079");
        initView();
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

//        findViewById(R.id.show_search_ad).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                startActivity(new Intent(MainActivity.this, HotWordActivity.class));
//            }
//        });
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
