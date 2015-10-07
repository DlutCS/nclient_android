package org.dlutcs.nclient_android.util;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v4.view.ViewPager;

/**
 * Auto scroll handler for ViewPager
 * Created by Linwei on 2015/6/15.
 */
public class AutoScrollHandler extends Handler {

    private static final String TAG = "AutoScrollHandler";
    private static final int SCROLL_WHAT = 1000;

    private ViewPager mViewPager = null;
    private long mInterval = 5000L;
    /**whether you have called start()*/
    private boolean mIsAutoScrolling = false;

    public AutoScrollHandler(){
        super(Looper.getMainLooper());
    }

    public AutoScrollHandler(ViewPager viewPager) {
        super(Looper.getMainLooper());
        this.mViewPager = viewPager;
    }

    public void setViewPager(ViewPager viewPager){
        mViewPager = viewPager;
    }

    /**
     * set auto scroll interval, default 5000ms
     * @param interval AutoScroll interval
     */
    public void setInterval(long interval) {
        this.mInterval = interval;
    }

    @Override
    public void handleMessage(Message msg) {
        super.handleMessage(msg);
        switch (msg.what) {
            case SCROLL_WHAT:
                scrollOnce();
                sendScrollMessage();
                break;
        }
    }

    private void scrollOnce() {
        if(mViewPager != null) {
            int size = mViewPager.getAdapter().getCount();
            if (size > 0) {
                int newPosition;
                newPosition = (mViewPager.getCurrentItem() + 1) % size;
                mViewPager.setCurrentItem(newPosition, true);
            }
        }
    }

    private void sendScrollMessage() {
        // remove messages before, keeps one message is running at most
        this.removeMessages(SCROLL_WHAT);
        this.sendEmptyMessageDelayed(SCROLL_WHAT, mInterval);
    }

    /**
     * start auto scroll
     */
    public void start() {
        mIsAutoScrolling = true;
        sendScrollMessage();
    }

    /**
     * stop auto scroll
     */
    public void stop() {
        mIsAutoScrolling = false;
        this.removeMessages(SCROLL_WHAT);
    }

    /**
     * close timer but maintain scroll state, this will work after start scrolling
     */
    public void pause(){
        if(mIsAutoScrolling){
            this.removeMessages(SCROLL_WHAT);
        }
    }

    /**
     * begin timer, usually call after pause()
     */
    public void goOn(){
        if(mIsAutoScrolling){
            this.sendScrollMessage();
        }
    }
}
