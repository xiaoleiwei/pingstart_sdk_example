package com.pingstart.mediation.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.pingstart.adsdk.listener.BannerListener;
import com.pingstart.adsdk.mediation.PingStartBanner;

import com.pingstart.mediation.R;
import com.pingstart.mediation.utils.DataUtils;

public class LoadAdBannerFragment extends Fragment implements BannerListener, OnClickListener {
    private PingStartBanner mPingStartBanner;
    private View mLoadAds;
    private Button mRefreshButton;
    private RelativeLayout mLoadingLayout;
    private RelativeLayout mAdViewBannerContainer;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_banner, container, false);
        mRefreshButton = (Button) view.findViewById(R.id.refreshButton);
        mAdViewBannerContainer = (RelativeLayout) view.findViewById(R.id.adViewContainer);
        mLoadingLayout = (RelativeLayout) view.findViewById(R.id.fresh_ad_show);
        mRefreshButton.setOnClickListener(this);
        setViewVisible(View.INVISIBLE, View.VISIBLE);
        return view;
    }

    private void setViewVisible(int progress_visible, int button_visible) {
        mLoadingLayout.setVisibility(progress_visible);
        mRefreshButton.setVisibility(button_visible);
    }

    @Override
    public void onClick(View v) {
        setViewVisible(View.VISIBLE, View.INVISIBLE);
        if (mPingStartBanner == null) {
            mPingStartBanner = new PingStartBanner(getActivity(), DataUtils.TEST_ADS_APPID, DataUtils.TEST_ADS_SLOTID_BANNER);
            mPingStartBanner.setAdListener(this);
            mPingStartBanner.loadBanner();
        } else {
            mPingStartBanner.destroy();
            mPingStartBanner.loadBanner();
        }
    }

    @Override
    public void onDestroyView() {
        mAdViewBannerContainer.removeView(mLoadAds);
        super.onDestroyView();
    }

    @Override
    public void onDestroy() {
        if (mPingStartBanner != null) {
            mPingStartBanner.destroy();
            mPingStartBanner = null;
        }
        super.onDestroy();
    }

    @Override
    public void onAdLoaded(View view) {
        if (view != null) {
            mAdViewBannerContainer.removeAllViews();
            mLoadAds = view;
            mAdViewBannerContainer.addView(mLoadAds);
            setViewVisible(View.INVISIBLE, View.INVISIBLE);
        }
    }

    @Override
    public void onAdError(String s) {
        setViewVisible(View.INVISIBLE, View.VISIBLE);
        Toast.makeText(getActivity(), "Banner Erro" + s, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onAdClicked() {
        Toast.makeText(getActivity(), "Banner Clicked", Toast.LENGTH_SHORT).show();
    }
}