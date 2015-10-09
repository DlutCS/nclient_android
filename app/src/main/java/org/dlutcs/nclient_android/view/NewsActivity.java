package org.dlutcs.nclient_android.view;

import android.content.Intent;
import android.os.Bundle;

import org.dlutcs.nclient_android.activity.BaseActivity;
import org.dlutcs.nclient_android.model.News;

/**
 * Created by linwei on 15-10-7.
 */
public class NewsActivity extends BaseActivity{
    public static void startActivity(News news) {
        Intent intent = new Intent();
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }
}
