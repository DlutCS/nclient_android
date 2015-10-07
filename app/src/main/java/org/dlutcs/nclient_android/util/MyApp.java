package org.dlutcs.nclient_android.util;

import android.app.Application;

/**
 * Created by linwei on 15-10-6.
 */
public class MyApp extends Application {

    private static MyApp sINSTANCE;


    @Override
    public void onCreate() {
        super.onCreate();
        sINSTANCE = MyApp.this;
        RequestManager.getInstance().init(this);
        ImageLoader.init(this);
    }

    public static MyApp getApp() {
        return sINSTANCE;
    }
}
