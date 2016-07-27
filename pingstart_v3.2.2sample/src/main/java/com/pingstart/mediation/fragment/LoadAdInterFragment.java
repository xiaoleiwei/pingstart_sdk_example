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

import com.pingstart.adsdk.listener.InterstitialListener;
import com.pingstart.adsdk.mediation.PingStartInterstitial;

import com.pingstart.mediation.R;
import com.pingstart.mediation.utils.DataUtils;

public class LoadAdInterFragment extends Fragment implements OnClickListener, InterstitialListener {
    private Button mShowInterstitial;
    private PingStartInterstitial mInterstitial;
    private RelativeLayout mLoadingLayout;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View mInterView = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_interstitial, container, false);
        mLoadingLayout = (RelativeLayout) mInterView.findViewById(R.id.fresh_ad_show);
        mShowInterstitial = (Button) mInterView.findViewById(R.id.show_interstitial);
        mShowInterstitial.setOnClickListener(this);
        setViewVisible(View.INVISIBLE, View.VISIBLE);
        return mInterView;
    }

    @Override
    public void onResume() {
        super.onResume();
        setViewVisible(View.INVISIBLE, View.VISIBLE);
    }

    @Override
    public void onClick(View v) {
        setViewVisible(View.VISIBLE, View.INVISIBLE);
        if (mInterstitial == null) {
            mInterstitial = new PingStartInterstitial(getActivity(), DataUtils.TEST_ADS_APPID, DataUtils.TEST_ADS_SLOTID_INTERSTITIAL);
            mInterstitial.setAdListener(this);
            mInterstitial.loadAd();
        } else {
            mInterstitial.loadAd();
        }
    }

    private void setViewVisible(int mProgressvisible, int mRefreshVisible) {
        mLoadingLayout.setVisibility(mProgressvisible);
        mShowInterstitial.setVisibility(mRefreshVisible);
    }

    @Override
    public void onAdError(String s) {
        if (getActivity() != null && !getActivity().isFinishing()) {
            Toast.makeText(getActivity(), "Interstitial Erro" + s, Toast.LENGTH_SHORT).show();
            setViewVisible(View.INVISIBLE, View.VISIBLE);
        }
    }

    @Override
    public void onAdLoaded() {
        if (getActivity() != null && !getActivity().isFinishing()) {
            setViewVisible(View.INVISIBLE, View.INVISIBLE);
            if (mInterstitial != null) {
                mInterstitial.showAd();
            }
        }
    }


    @Override
    public void onAdClicked() {
        Toast.makeText(getActivity(), "Interstitial Clicked", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onAdClosed() {
        Toast.makeText(getActivity(), "Interstitial Closed", Toast.LENGTH_SHORT).show();
        if (mInterstitial != null) {
            mInterstitial.destroy();
        }
        setViewVisible(View.INVISIBLE, View.VISIBLE);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void onDestroy() {
        if (mInterstitial != null) {
            mInterstitial.destroy();
        }
        super.onDestroy();
    }
}