package com.pingstart.mediation;

import android.app.Activity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;
import com.facebook.ads.MediaView;
import com.pingstart.adsdk.listener.NativeListener;
import com.pingstart.adsdk.mediation.PingStartNative;
import com.pingstart.adsdk.model.BaseNativeAd;
import com.pingstart.adsdk.utils.LogUtils;
import com.pingstart.adsdk.utils.VolleyUtil;
import com.pingstart.mobileads.FacebookNativeAd;

/**
 * Created by base on 2016/6/1.
 */
public class NativeActivity extends Activity {

    private static final String TAG = NativeActivity.class.getSimpleName();

    private Button mActionButton;
    private MediaView mFbNative;
    private ImageView mAdImageView;
    private TextView mTitleTextView, mContentTextView;

    private PingStartNative mNativeManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LogUtils.d(TAG, "onCreate");
        setContentView(R.layout.activity_native);

        mAdImageView = (ImageView) findViewById(R.id.ad_image);
        mTitleTextView = (TextView) findViewById(R.id.title);
        mContentTextView = (TextView) findViewById(R.id.content);
        mActionButton = (Button) findViewById(R.id.ad_btn);
        mFbNative = (MediaView) findViewById(R.id.fb_ad);
        findViewById(R.id.btn_native_ad).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mNativeManager.unregisterNativeView();
                mNativeManager.reLoadAd();
            }
        });

        mNativeManager = new PingStartNative(this, "5079", "1000223");
        mNativeManager.setAdListener(new NativeListener() {

            @Override
            public void onAdError(String error) {
                LogUtils.i(TAG, "  nativeErro");
            }

            @Override
            public void onAdLoaded(final BaseNativeAd ad) {
                LogUtils.d(TAG, "Native onAdLoaded");
                if (ad != null) {
                    if (!ad.getNetworkName().equalsIgnoreCase("facebook")) {
                        String coverImageUrl = ad.getCoverImageUrl();
                        if (!TextUtils.isEmpty(coverImageUrl)) {
                            RequestQueue queue = Volley.newRequestQueue(NativeActivity.this);
                            ImageLoader loader = new ImageLoader(queue, new VolleyUtil.BitmapLruCache());
                            loader.get(coverImageUrl, new ImageLoader.ImageListener() {
                                @Override
                                public void onResponse(ImageLoader.ImageContainer imageContainer, boolean b) {
                                    if (imageContainer != null && imageContainer.getBitmap() != null) {
                                        mFbNative.setVisibility(View.GONE);
                                        mAdImageView.setVisibility(View.VISIBLE);
                                        mAdImageView.setImageBitmap(imageContainer.getBitmap());
                                        mTitleTextView.setText(ad.getTitle());
                                        mContentTextView.setText(ad.getDescription());
                                        mActionButton.setText(ad.getAdCallToAction());
                                        if (mNativeManager != null) {
                                            mNativeManager.registerNativeView(findViewById(R.id.native_ad_layout));
                                        }
                                    }
                                }

                                @Override
                                public void onErrorResponse(VolleyError volleyError) {

                                }
                            });
                        }
                    } else {
                        mFbNative.setVisibility(View.VISIBLE);
                        mAdImageView.setVisibility(View.GONE);
                        FacebookNativeAd nativeAd = (FacebookNativeAd) ad;
                        mFbNative.setNativeAd(nativeAd.getNativeAd());
                        mTitleTextView.setText(nativeAd.getTitle());
                        mContentTextView.setText(nativeAd.getDescription());
                        mActionButton.setText(nativeAd.getAdCallToAction());
                        mNativeManager.registerNativeView(findViewById(R.id.native_ad_layout));
                    }
                }
            }

            @Override
            public void onAdClicked() {
                LogUtils.i(TAG, "nativeClick");
            }
        });
        mNativeManager.loadAd();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mNativeManager != null) {
            mNativeManager.destroy();
            mNativeManager = null;
        }
    }
}
