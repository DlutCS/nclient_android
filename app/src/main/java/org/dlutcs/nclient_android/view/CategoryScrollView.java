package org.dlutcs.nclient_android.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;

import org.dlutcs.nclient_android.R;
import org.dlutcs.nclient_android.model.Category;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by linwei on 15-10-5.
 */
public class CategoryScrollView extends HorizontalScrollView {

    @InjectView(R.id.category_linear)
    LinearLayout mLinear;

    private List<CategoryView> mCategorysView = new ArrayList<>();

    public CategoryScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public CategoryScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init(){
        LayoutInflater.from(getContext()).inflate(R.layout.view_category_list, this, true);
        ButterKnife.inject(this, this);
        setOverScrollMode(View.OVER_SCROLL_NEVER);
        setHorizontalScrollBarEnabled(false);
    }

    public void addCategorys(List<Category> categories){
        mCategorysView.clear();
        mLinear.removeAllViews();
        for (int i=0;i < categories.size();i++){
            CategoryView view = new CategoryView(getContext(), categories.get(i));
            view.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View view) {
                    if( mCategorysView.indexOf(view) != -1){
                        setSelectIndex(mCategorysView.indexOf(view));
                    }
                }
            });
            mCategorysView.add(view);
            mLinear.addView(view);
        }
    }

    public void setSelectIndex(int index) {
        if(index >= 0 && index < mCategorysView.size()) {
            for (int i = 0; i < mCategorysView.size(); i++) {
                mCategorysView.get(i).setSelect(i == index);
            }
        }
    }
}
