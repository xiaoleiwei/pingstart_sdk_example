package com.pingstart.mediation;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.pingstart.adsdk.AdNativeManager;
import com.pingstart.adsdk.model.Ad;
import com.pingstart.adsdk.utils.LogUtils;

import java.util.ArrayList;

/**
 * Created by base on 2016/5/27.
 */
public class MultiActivity extends Activity {

    private static final String TAG = MultiActivity.class.getSimpleName();

    private Context mContext;

    private RelativeLayout mAdsLayout;
    private LinearLayout mAdsContainer;
    private AdNativeManager mNativeAdsManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LogUtils.d(TAG, "onCreate");

        setContentView(R.layout.activity_multi);

        mContext = this;
        findViewById(R.id.btn_multi_ad).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                mNativeAdsManager.unregisterNativeView();
                mAdsContainer.removeAllViews();
                mNativeAdsManager.reLoadAd();
            }
        });

        mAdsContainer = (LinearLayout) findViewById(R.id.ads_container);
        mNativeAdsManager = new AdNativeManager(this, "5079", "1000223", 6);
        mNativeAdsManager.setListener(new AdNativeManager.AdsListener() {
            @Override
            public void onAdError(String error) {
                Toast.makeText(mContext, "onAdError", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onAdLoaded(ArrayList<Ad> arrayList) {
                LogUtils.d(TAG, "Multi onAdLoaded");
                //广告的两种加载方式：
                //1.通过容器addView的方式加载广告布局
                //2.通过listView adapter的方式动态加载广告，有开发者验证这种方式会存在问题，比如有的图片显示比较小。

                //an ad list has loaded, then do your own work
                for (int i = 0; i < arrayList.size(); i++) {
                    Ad ad = arrayList.get(i);
                    if (ad != null) {
                        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                        mAdsLayout = (RelativeLayout) inflater.inflate(R.layout.multi_native_ad_layout, null);
                        ImageView imageView = (ImageView) mAdsLayout.findViewById(R.id.native_coverImage);
                        TextView titleView = (TextView) mAdsLayout.findViewById(R.id.native_title);
                        TextView contentView = (TextView) mAdsLayout.findViewById(R.id.native_description);
                        TextView actionView = (TextView) mAdsLayout.findViewById(R.id.native_titleForAdButton);
                        ad.displayCoverImage(MultiActivity.this, imageView);
                        titleView.setText(ad.getTitle());
                        contentView.setText(ad.getDescription());
                        actionView.setText(ad.getAdCallToAction());
                        mNativeAdsManager.registerNativeView(ad, actionView);
                        mAdsContainer.addView(mAdsLayout);
                    }
                }
            }

            @Override
            public void onAdClicked() {
                Toast.makeText(mContext, "onAdClicked", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onAdOpened() {
                Toast.makeText(mContext, "onAdOpened", Toast.LENGTH_SHORT).show();
            }
        });
//        mNativeAdsManager.setAdOrder(true);
        mNativeAdsManager.loadAd();
    }

    @Override
    protected void onResume() {
        super.onResume();
        LogUtils.d(TAG, "onResume");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        LogUtils.d(TAG, "onDestroy");
        if (mNativeAdsManager != null) {
            mNativeAdsManager.destroy();
            mNativeAdsManager = null;
        }
    }
}
