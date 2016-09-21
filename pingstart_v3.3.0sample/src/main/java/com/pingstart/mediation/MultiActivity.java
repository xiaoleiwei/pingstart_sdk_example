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

import com.facebook.ads.MediaView;

import com.pingstart.adsdk.listener.MultipleListener;
import com.pingstart.adsdk.mediation.PingStartMultiple;
import com.pingstart.adsdk.utils.LogUtils;
import com.pingstart.adsdk.model.BaseNativeAd;

import com.pingstart.mobileads.FacebookNativeAd;

import java.util.List;

/**
 * Created by base on 2016/5/27.
 */
public class MultiActivity extends Activity {

    private static final String TAG = MultiActivity.class.getSimpleName();

    private Context mContext;

    private RelativeLayout mAdsLayout;
    private LinearLayout mAdsContainer;
    private PingStartMultiple mNativeAdsManager;

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
        mNativeAdsManager = new PingStartMultiple(this, "5079", "1000953", 10);
        mNativeAdsManager.setListener(new MultipleListener() {
            @Override
            public void onAdLoaded(List<BaseNativeAd> ads) {
                for (int i = 0; i < ads.size(); i++) {
                    BaseNativeAd ad = ads.get(i);
                    if (ad != null) {
                        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                        mAdsLayout = (RelativeLayout) inflater.inflate(R.layout.multi_native_ad_layout, null);
                        TextView titleView = (TextView) mAdsLayout.findViewById(R.id.native_title);
                        TextView contentView = (TextView) mAdsLayout.findViewById(R.id.native_description);
                        TextView actionView = (TextView) mAdsLayout.findViewById(R.id.native_titleForAdButton);
                        ImageView imageView = (ImageView) mAdsLayout.findViewById(R.id.native_coverImage);
                        MediaView mediaView = (MediaView) mAdsLayout.findViewById(R.id.fb_native);
                        if (ad.getNetworkName().equalsIgnoreCase("facebook")) {
                            imageView.setVisibility(View.GONE);
                            mediaView.setVisibility(View.VISIBLE);
                            FacebookNativeAd nativeAd = (FacebookNativeAd) ad;
                            mediaView.setNativeAd(nativeAd.getNativeAd());
                        } else {
                            imageView.setVisibility(View.VISIBLE);
                            mediaView.setVisibility(View.GONE);
                            ad.displayCoverImage(MultiActivity.this, imageView);
                        }
                        titleView.setText(ad.getTitle());
                        contentView.setText(ad.getDescription());
                        actionView.setText(ad.getAdCallToAction());
                        if (mNativeAdsManager != null) {
                            mNativeAdsManager.registerNativeView(ad, actionView);
                        }
                        mAdsContainer.addView(mAdsLayout);
                    }
                }
            }

            @Override
            public void onAdError(String error) {
                LogUtils.d(TAG, "onAdError :" + error);
            }

            @Override
            public void onAdClicked() {
                Toast.makeText(mContext, "onAdClicked", Toast.LENGTH_SHORT).show();
            }
        });
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
