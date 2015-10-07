package org.dlutcs.nclient_android.view;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
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
public class NewsListHeaderView extends FrameLayout {

    @InjectView(R.id.pager)
    ViewPager mViewPager;
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

    private void init(){
        LayoutInflater.from(getContext()).inflate(
                R.layout.view_news_list_header, this, true);
        ButterKnife.inject(this, this);
        mAutoScrollHandler = new AutoScrollHandler(mViewPager);
        mAutoScrollHandler.start();
        mAdapter = new NewsHeaderPagerAdapter();
        mViewPager.setAdapter(mAdapter);
    }

    public void addNews(ArrayList<News> newsList){
        mNewsList.clear();
        mNewsList.addAll(newsList);
        mAdapter.notifyDataSetChanged();
    }

    public void onPause(){
        mAutoScrollHandler.pause();
    }

    public void onResume(){
        mAutoScrollHandler.goOn();
    }

    private class NewsHeaderPagerAdapter extends PagerAdapter{

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            final News news = mNewsList.get(position);
            ImageView imageView = new ImageView(container.getContext());
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            ImageLoader.load(news.coverUrl).into(imageView);
            container.addView(imageView);
            imageView.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View view) {
                    NewsActivity.startActivity(news);
                }
            });
            return mNewsList.get(position);
        }

        @Override
        public int getCount() {
            return mNewsList.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return false;
        }
    }


}
