package org.dlutcs.nclient_android.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.dlutcs.nclient_android.R;
import org.dlutcs.nclient_android.model.Category;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by linwei on 15-10-5.
 */
public class CategoryView extends RelativeLayout {
    @InjectView(R.id.text)
    TextView mText;
    @InjectView(R.id.cursor)
    ImageView mCursor;

    private Category mCategory;

    public CategoryView(Context context, Category category) {
        super(context);
        this.mCategory = category;
        init();
    }

    public CategoryView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CategoryView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    private void init(){
        LayoutInflater.from(getContext()).inflate(R.layout.view_category, this, true);
        ButterKnife.inject(this, this);
        mText.setText(mCategory.name);
    }

    public void setSelect(boolean isSelect){
        if(isSelect){
            mText.setTextColor(getResources().getColor(R.color.category_selected));
            mCursor.setImageResource(R.color.category_selected);
        }else{
            mText.setTextColor(getResources().getColor(R.color.category_normal));
            mCursor.setImageResource(R.color.white);

        }
    }

}
