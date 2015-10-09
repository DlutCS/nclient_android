package org.dlutcs.nclient_android.view;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.dlutcs.nclient_android.R;
import org.dlutcs.nclient_android.model.News;
import org.dlutcs.nclient_android.util.AutoScrollHandler;
import org.dlutcs.nclient_android.util.ImageLoader;

import java.util.ArrayList;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by linwei on 15-10-6.
 */
public class NewsListHeaderView extends FrameLayout implements ViewPager.OnPageChangeListener {

    @InjectView(R.id.pager)
    HackViewPager mViewPager;
    @InjectView(R.id.dots_view)
    LinearLayout mDotsView;
    @InjectView(R.id.news_title)
    TextView mNewsTitle;

    private ArrayList<News> mNewsList = new ArrayList<>();
    private AutoScrollHandler mAutoScrollHandler;
    private NewsHeaderPagerAdapter mAdapter;

    public NewsListHeaderView(Context context) {
        super(context);
        init();
    }

    private void init() {
        LayoutInflater.from(getContext())
                .inflate(
                        R.layout.view_news_list_header, this, true);
        ButterKnife.inject(this, this);
        mAutoScrollHandler = new AutoScrollHandler(mViewPager);
        mAutoScrollHandler.start();
        mAdapter = new NewsHeaderPagerAdapter();
        mViewPager.setAdapter(mAdapter);
        mViewPager.setOnPageChangeListener(this);
    }

    public void addNews(ArrayList<News> newsList) {
        mNewsList.clear();
        mNewsList.addAll(newsList);
        mAdapter.notifyDataSetChanged();
        if(mNewsList.size() > 0){
            mViewPager.setCurrentItem(0);
            selectIndex(0);
        }
    }

    public void onPause() {
        mAutoScrollHandler.pause();
    }

    public void onResume() {
        mAutoScrollHandler.goOn();
    }

    public void selectIndex(int index) {
        mDotsView.removeAllViews();
        int dotsCount = mNewsList.size();
        int dotSize = getResources().getDimensionPixelSize(R.dimen.dot_size);
        int dotMargin = getResources().getDimensionPixelSize(R.dimen.dot_margin);
        for (int i = 0; i < dotsCount; i++) {
            ImageView imageView = new ImageView(getContext());
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(dotSize, dotSize);
            //最后一个点不需设置右边距
            if (i != dotsCount - 1) {
                params.rightMargin = dotMargin;
            }
            imageView.setLayoutParams(params);
            imageView.setImageResource(R.drawable.promotion_dot_selector);
            if (i == index) {
                imageView.setSelected(true);
            }
            mDotsView.addView(imageView);
        }
        mNewsTitle.setText(mNewsList.get(index).title);
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        selectIndex(position);
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    private class NewsHeaderPagerAdapter extends PagerAdapter {

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            final News news = mNewsList.get(position);
            View view = LayoutInflater.from(getContext())
                    .inflate(R.layout.view_pager_header,
                            container, false);
            ImageView imageView = (ImageView) view.findViewById(R.id.image_pager);
            ImageLoader.load(news.coverUrl)
                    .into(imageView);
            imageView.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View view) {
                    NewsActivity.startActivity(news);
                }
            });
            // must use addView()
            container.addView(view);
            return view;
        }

        @Override
        public int getCount() {
            return mNewsList.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }
    }


}
