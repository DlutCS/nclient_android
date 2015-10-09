package org.dlutcs.nclient_android.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.TextView;


import org.dlutcs.nclient_android.R;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by GoogolMo on 6/30/14.
 */
public class FooterView extends FrameLayout {
    @InjectView(R.id.progress)
    ProgressBar mProgress;
    @InjectView(R.id.text)
    TextView mText;

    public FooterView(Context context) {
        super(context);
        init(context);
    }

    public FooterView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public FooterView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context);
    }

    private void init(Context context) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(
                Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.view_footer, this, true);
        ButterKnife.inject(this, view);

    }

    public void setTexColor(int colorId) {
        mText.setTextColor(colorId);
    }

    public void showProgress() {
        mProgress.setVisibility(View.VISIBLE);
        mText.setVisibility(View.GONE);
        setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        setBackgroundColor(getResources().getColor(android.R.color.transparent));
    }

    public void showText(CharSequence text, final CallBack callBack) {
        mProgress.setVisibility(View.GONE);
        mText.setVisibility(View.VISIBLE);
        mText.setText(text);
        if (callBack != null) {
            setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    callBack.callBack(v);
                }
            });
//            setBackgroundResource(R.drawable.selectable_background_frodo);
        } else {
            setBackgroundColor(getResources().getColor(android.R.color.transparent));
            setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });
        }
    }

    public void showText(int text, final CallBack callBack) {
        mProgress.setVisibility(View.GONE);
        mText.setVisibility(View.VISIBLE);
        mText.setText(text);
        if (callBack != null) {
//            setBackgroundResource(R.drawable.selectable_background_frodo);
            setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    callBack.callBack(v);
                }
            });
        } else {
            setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });
            setBackgroundColor(getResources().getColor(android.R.color.transparent));
        }

    }

    public void showText(CharSequence text) {
        showText(text, null);
    }

    public void showText(int resource) {
        showText(resource, null);
    }

    public void showNone() {
        mProgress.setVisibility(View.GONE);
        mText.setVisibility(View.GONE);
        setMinimumHeight(0);
        setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        setBackgroundColor(getResources().getColor(android.R.color.transparent));
    }

    public void hide() {
        showNone();
        ViewGroup.LayoutParams layoutParams = getLayoutParams();
        if (layoutParams != null) {
            layoutParams.height = 0;
            setLayoutParams(layoutParams);
        }

    }

    public TextView getTextView() {
        return mText;
    }

    public ProgressBar getProgressBar() {
        return mProgress;
    }

    public interface CallBack {
        void callBack(View view);
    }
}
