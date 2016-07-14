package com.pingstart.mediation.fragment;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.pingstart.adsdk.listener.SearchAdsListener;
import com.pingstart.adsdk.mediation.PingStartSearch;
import com.pingstart.adsdk.model.SearchAds;

import com.pingstart.adsdk.utils.LogUtils;
import com.pingstart.adsdk.utils.VolleyUtil;
import com.pingstart.mediation.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by badguy on 16-6-29.
 */
public class LoadAdSearchAdsFragment extends Fragment implements AdapterView.OnItemClickListener, View.OnClickListener {

    private static final int RANDOM_SIZE = 9;

    private ViewGroup mLytSearch;
    private GridView mLytHotWord;
    private Button mBtnHotWord;

    private List<SearchAds> mHotWords;
    private List<Integer> mIndex = new ArrayList<>();
    private PingStartSearch mPingStartWord;
    private boolean hasImage = true;

    private Context mContext;

    private int[] mBackgroundColors = new int[]{
            Color.LTGRAY, Color.BLUE,
            Color.GRAY, Color.MAGENTA,
            Color.RED, Color.YELLOW,
            Color.CYAN, Color.DKGRAY,
            Color.GREEN
    };

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = getActivity();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_hot_word, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mLytHotWord = (GridView) view.findViewById(R.id.lyt_hotword);
        mLytHotWord.setOnItemClickListener(this);

        mBtnHotWord = (Button) view.findViewById(R.id.btn_hotword);
        mBtnHotWord.setOnClickListener(this);

        mLytSearch = (ViewGroup) view.findViewById(R.id.lyt_search);
        mLytSearch.setVisibility(View.GONE);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        LogUtils.d("Search", "click position:" + position + " index = " + mIndex.get(position));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_hotword:
                loadHotWord();
                break;
        }
    }

    private void loadHotWord() {
        mPingStartWord = new PingStartSearch(mContext, "5079", "1000596");
        mPingStartWord.setAdListener(new SearchAdsListener() {
            @Override
            public void onAdFailed(String error) {
                Log.e("Search", "load hot word error:" + error);
            }

            @Override
            public void onAdLoaded(List<SearchAds> hotWords) {
                mLytSearch.setVisibility(View.VISIBLE);
                mBtnHotWord.setVisibility(View.GONE);

                mHotWords = hotWords;
                if (mIndex != null) {
                    mIndex.clear();
                }
                generateRandom();
                hasImage();
                mLytHotWord.setAdapter(new HotWordAdapter());
            }

            @Override
            public void onAdClicked() {

            }
        });
        mPingStartWord.loadAd();
    }

    private void generateRandom() {
        Random random = new Random();
        for (int i = 0; i < RANDOM_SIZE; i++) {
            int index = random.nextInt(mHotWords.size());
            Log.d("Search", "random index is :" + index);
            mIndex.add(index);
        }
    }

    private void hasImage() {
        boolean[] flags = new boolean[RANDOM_SIZE];

        for (int i = 0; i < RANDOM_SIZE; i++) {
            SearchAds hotWord = mHotWords.get(mIndex.get(i));
            if (TextUtils.isEmpty(hotWord.getUrlImage())) {
                flags[i] = false;
            } else {
                flags[i] = true;
            }
        }

        for (boolean flag : flags) {
            Log.d("Search", "flags :" + flag);
            if (!flag) {
                hasImage = false;
            }
        }
    }

    private class HotWordAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return 9;
        }

        @Override
        public Object getItem(int position) {
            return mHotWords.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder;
            if (convertView == null) {
                LayoutInflater inflater = LayoutInflater.from(mContext);
                if (hasImage) {
                    convertView = inflater.inflate(R.layout.item_hot_word_with_image, parent, false);
                } else {
                    convertView = inflater.inflate(R.layout.item_hot_word_without_image, parent, false);
                }
                holder = new ViewHolder(convertView);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }

            SearchAds word = mHotWords.get(mIndex.get(position));
            if (hasImage) {
                String imgUrl = word.getUrlImage();
                VolleyUtil.loadImage(mContext, holder.imageView, imgUrl);
                holder.txtWithImage.setText(word.getTitle());
            } else {
                Random random = new Random();
                holder.txtWithoutImage.setBackgroundColor(mBackgroundColors[random.nextInt(RANDOM_SIZE)]);
                holder.txtWithoutImage.setText(word.getTitle());
            }
            //注册点击
            mPingStartWord.registerSearchAdsClickListener(mContext, convertView, word);
            //注册展示
            mPingStartWord.registerSearchAdsImpression(mContext, word);
            return convertView;
        }

        class ViewHolder {
            ImageView imageView;
            TextView txtWithImage;
            TextView txtWithoutImage;

            public ViewHolder(View view) {
                if (hasImage) {
                    imageView = (ImageView) view.findViewById(R.id.img_hotword);
                    txtWithImage = (TextView) view.findViewById(R.id.txt_hotword);
                } else {
                    txtWithoutImage = (TextView) view.findViewById(R.id.txt_hotword_title);
                }
            }
        }
    }
}
