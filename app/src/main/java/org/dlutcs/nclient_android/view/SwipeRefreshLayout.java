package org.dlutcs.nclient_android.view;

/**
 * Created by linwei on 15-10-6.
 */

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.ViewConfiguration;

import org.dlutcs.nclient_android.R;


/**
 * Created by GoogolMo on 8/3/14.
 */
public class SwipeRefreshLayout extends android.support.v4.widget.SwipeRefreshLayout {

    private int mTouchSlop;
    private float mPrevX;

    public SwipeRefreshLayout(Context context) {
        super(context);
        init(context);
    }

    public SwipeRefreshLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    private void init(Context context) {
        mTouchSlop = ViewConfiguration.get(context)
                .getScaledTouchSlop();
        setColorSchemeResources(R.color.app_blue, android.R.color.transparent,
                R.color.app_blue, android.R.color.transparent);
    }

    @Override
    public boolean canChildScrollUp() {
//        return true;
        return super.canChildScrollUp();
    }

    // adapted from http://stackoverflow.com/questions/23989910/horizontalscrollview-inside-swiperefreshlayout
    @Override
    public boolean onInterceptTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mPrevX = MotionEvent.obtain(event)
                        .getX();
                break;

            case MotionEvent.ACTION_MOVE:
                final float eventX = event.getX();
                float xDiff = Math.abs(eventX - mPrevX);

                if (xDiff > mTouchSlop) {
                    return false;
                }
        }
        return super.onInterceptTouchEvent(event);
    }

}
