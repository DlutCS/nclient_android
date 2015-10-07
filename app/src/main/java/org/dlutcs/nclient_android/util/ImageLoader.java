package org.dlutcs.nclient_android.util;

import android.annotation.TargetApi;
import android.app.ActivityManager;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;

import com.squareup.picasso.LruCache;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.RequestCreator;

import org.dlutcs.nclient_android.BuildConfig;
import org.dlutcs.nclient_android.R;

import java.io.File;

/**
 * Created by GoogolMo on 6/30/14.
 */
public class ImageLoader {

    static Picasso sInstance;

    public static Picasso getInstance() {
        if (sInstance == null) {
            throw new NullPointerException("please call ImageLoader.init first");
        }
        return sInstance;
    }

    public static void init(Context context) {
        sInstance = new Picasso.Builder(context)
                .memoryCache(new LruCache(calculateMemoryCacheSize(context)))
                .loggingEnabled(BuildConfig.DEBUG)
                .build();
    }

    public static RequestCreator load(String path) {
        return sInstance.load(path)
                .placeholder(R.drawable.default_cover_background)
                .config(Bitmap.Config.RGB_565);
    }

    public static RequestCreator load(Uri uri) {
        return sInstance.load(uri)
                .placeholder(R.drawable.default_cover_background)
                .config(Bitmap.Config.RGB_565);
    }

    public static RequestCreator load(File file) {
        return sInstance.load(file)
                .placeholder(R.drawable.default_cover_background)
                .config(Bitmap.Config.RGB_565);
    }

    public static RequestCreator load(int resource) {
        return sInstance.load(resource)
                .placeholder(R.drawable.default_cover_background)
                .config(Bitmap.Config.RGB_565);
    }

    public static RequestCreator cover(String path) {
        return load(path);
    }

    public static RequestCreator cover(File file) {
        return load(file);
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    private static class ActivityManagerHoneycomb {
        static int getLargeMemoryClass(ActivityManager activityManager) {
            return activityManager.getLargeMemoryClass();
        }
    }

    static int calculateMemoryCacheSize(Context context) {
        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        boolean largeHeap = (context.getApplicationInfo().flags & ApplicationInfo.FLAG_LARGE_HEAP) != 0;
        int memoryClass = am.getMemoryClass();
        if (largeHeap && Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            memoryClass = ActivityManagerHoneycomb.getLargeMemoryClass(am);
        }
        // Target ~15% of the available heap.
        return 1024 * 1024 * memoryClass / 7;
    }

}
