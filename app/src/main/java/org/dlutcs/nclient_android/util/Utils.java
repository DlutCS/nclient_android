package org.dlutcs.nclient_android.util;

import android.content.Context;
import android.util.DisplayMetrics;
import android.view.WindowManager;

/**
 * Created by linwei on 15-10-6.
 */
public class Utils {

    public static int getDisplayWidth(Context context) {
        if (null == context) {
            return 0;
        }
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics metrics = new DisplayMetrics();
        wm.getDefaultDisplay()
                .getMetrics(metrics);
        return metrics.widthPixels;
    }

    public static int getDisplayHeight(Context context) {
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics metrics = new DisplayMetrics();
        wm.getDefaultDisplay()
                .getMetrics(metrics);
        return metrics.heightPixels;
    }
}
