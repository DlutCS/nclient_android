package org.dlutcs.nclient_android.view;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;

import org.dlutcs.nclient_android.R;
import org.dlutcs.nclient_android.util.Utils;
import org.dlutcs.nclient_android.model.Category;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by linwei on 15-10-5.
 */
public class CategoryScrollStrip extends HorizontalScrollView implements
        ViewPager.OnPageChangeListener {

    @InjectView(R.id.category_linear)
    LinearLayout mLinear;

    private ViewPager mViewPager;

    private List<CategoryView> mCategorysView = new ArrayList<>();

    public CategoryScrollStrip(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public CategoryScrollStrip(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init(){
        LayoutInflater.from(getContext()).inflate(R.layout.view_category_list, this, true);
        ButterKnife.inject(this, this);
        setOverScrollMode(View.OVER_SCROLL_NEVER);
        setHorizontalScrollBarEnabled(false);
    }

    @Override
    protected void onScrollChanged(int x, int y, int oldX, int oldY){
        super.onScrollChanged(x, y, oldX, oldY);
    }

    public void addCategorys(List<Category> categories){
        mCategorysView.clear();
        mLinear.removeAllViews();
        for (int i=0;i < categories.size();i++){
            CategoryView view = new CategoryView(getContext(), categories.get(i));
            view.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View view) {
                    int index = mCategorysView.indexOf(view);
                    if( index != -1){
                        setSelectIndex(index);
                        mViewPager.setCurrentItem(index);
                    }
                }
            });
            mCategorysView.add(view);
            mLinear.addView(view);
        }
    }

    public void setSelectIndex(int index) {
        if(index >= 0 && index < mCategorysView.size()) {
            // move to show
            CategoryView categoryView = mCategorysView.get(index);
            int width = Utils.getDisplayWidth(getContext());
            if(categoryView.getRight() > getScrollX() + width){
                smoothScrollBy(categoryView.getRight() - (getScrollX() + width), 0);
            }else if(categoryView.getLeft() < getScrollX()){
                smoothScrollBy(categoryView.getLeft() - getScrollX(), 0);
            }
            // display selected
            for (int i = 0; i < mCategorysView.size(); i++) {
                mCategorysView.get(i).setSelect(i == index);
            }
        }
    }

    public void setViewPager(ViewPager viewPager) {
        this.mViewPager = viewPager;
        mViewPager.setOnPageChangeListener(this);
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
//        Log.i("CategoryScrollStrip", "offset=" + positionOffset);
    }

    @Override
    public void onPageSelected(int position) {
        setSelectIndex(position);
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
}
