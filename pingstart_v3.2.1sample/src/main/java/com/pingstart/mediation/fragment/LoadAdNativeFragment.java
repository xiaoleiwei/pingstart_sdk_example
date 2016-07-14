package com.pingstart.mediation.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.pingstart.adsdk.listener.NativeListener;
import com.pingstart.adsdk.mediation.PingStartNative;
import com.pingstart.adsdk.model.BaseNativeAd;

import com.pingstart.mediation.R;
import com.pingstart.mediation.utils.DataUtils;

public class LoadAdNativeFragment extends Fragment implements NativeListener, OnClickListener {
    private View mAdViewNativeContainer;
    private RelativeLayout mLoadingLayout;
    private PingStartNative mNative;
    private Button mShowNative;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_native, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mAdViewNativeContainer = view.findViewById(R.id.native_container);
        mLoadingLayout = (RelativeLayout) view.findViewById(R.id.fresh_ad_show);
        mShowNative = (Button) view.findViewById(R.id.show_native);
        mShowNative.setOnClickListener(this);
        setViewVisible(View.INVISIBLE, View.VISIBLE);
    }

    @Override
    public void onClick(View v) {
        setViewVisible(View.VISIBLE, View.INVISIBLE);
        mNative = new PingStartNative(getActivity(), DataUtils.TEST_ADS_APPID, DataUtils.TEST_ADS_SLOTID_NATIVE);
        mNative.setAdListener(this);
        mNative.loadAd();
    }

    @Override
    public void onAdError(String s) {
        setViewVisible(View.INVISIBLE, View.VISIBLE);
        Log.d("1234", "load add error:" + s);
        Toast.makeText(getActivity(), "Native Erro" + s, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onAdLoaded(BaseNativeAd ad) {
        try {
            setViewVisible(View.INVISIBLE, View.INVISIBLE);
            String titleForAd = ad.getAdCallToAction();
            String titleForAdButton = ad.getAdCallToAction();
            String description = ad.getDescription();
            String title = ad.getTitle();
            ImageView nativeCoverImage = (ImageView) mAdViewNativeContainer.findViewById(R.id.native_coverImage);
            TextView nativeTitle = (TextView) mAdViewNativeContainer.findViewById(R.id.native_title);
            TextView nativeDescription = (TextView) mAdViewNativeContainer.findViewById(R.id.native_description);
            TextView nativeAdButton = (TextView) mAdViewNativeContainer.findViewById(R.id.native_titleForAdButton);
            TextView nativeAdflag = (TextView) mAdViewNativeContainer.findViewById(R.id.native_adflag);
            nativeAdflag.setText(getString(R.string.banner_adflag));
            if (!TextUtils.isEmpty(titleForAd) && !TextUtils.isEmpty(titleForAdButton)) {
                nativeAdButton.setText(titleForAdButton);
                nativeDescription.setText(description);
                nativeTitle.setText(title);
                ad.displayCoverImage(getActivity(), nativeCoverImage);
                mAdViewNativeContainer.setVisibility(View.VISIBLE);
                if (mNative != null) {
                    mNative.registerNativeView(mAdViewNativeContainer);
                }
            }
        } catch (Exception e) {
        }
    }

    @Override
    public void onAdClicked() {
//        Toast.makeText(getActivity(), "Native Clicked", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onDestroyView() {
        isParentNull();
        super.onDestroyView();
    }

    @Override
    public void onDestroy() {
        if (mNative != null) {
            mNative.destroy();
        }
        super.onDestroy();
    }

    private void setViewVisible(int mProgressvisible, int mButtonvisible) {
        mLoadingLayout.setVisibility(mProgressvisible);
        mShowNative.setVisibility(mButtonvisible);
    }

    private void isParentNull() {
        if (mAdViewNativeContainer.getParent() != null) {
            ViewGroup parent = (ViewGroup) mAdViewNativeContainer.getParent();
            if (parent != null) {
                parent.removeView(mAdViewNativeContainer);
            }
        }
    }
}