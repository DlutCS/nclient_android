package org.dlutcs.nclient_android.util;

import android.content.Context;
import android.util.DisplayMetrics;
import android.view.WindowManager;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

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

    public static final SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss",
            Locale.CHINA);
    public static final SimpleDateFormat sdf3 = new SimpleDateFormat("yyyy年MM月dd日 HH:mm",
            Locale.CHINA);

    public static final long SECOND_IN_MS = 1000;
    public static final long MINUTE_IN_MS = 60 * SECOND_IN_MS;
    public static final long HOUR_IN_MS = 60 * MINUTE_IN_MS;
    public static final long DAY_IN_MS = 24 * HOUR_IN_MS;
    public static final long MONTH_IN_MS = 30 * DAY_IN_MS;
    public static final long YEAR_IN_MS = 12 * MONTH_IN_MS;

    private static String getTime(long time, SimpleDateFormat format) {
        long timeSpace = System.currentTimeMillis() - time;
        if (timeSpace < 10 * SECOND_IN_MS) {
            return "刚刚";
        } else if (timeSpace < MINUTE_IN_MS) {
            return ((int) (timeSpace / SECOND_IN_MS)) + "秒前";
        } else if (timeSpace < HOUR_IN_MS) {
            return ((int) (timeSpace / MINUTE_IN_MS)) + "分钟前";
        } else if (timeSpace < DAY_IN_MS) {
            return ((int) (timeSpace / HOUR_IN_MS)) + "小时前";
        } else {
            return format.format(new Date(time));
        }
    }

    public static String getTime(String serverTime){
        try {
            Date d = format.parse(serverTime);
            return getTime(d.getTime(), sdf3);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return "未知时间";
    }

}
