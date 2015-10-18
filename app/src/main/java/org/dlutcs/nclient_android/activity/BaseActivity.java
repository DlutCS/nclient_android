package org.dlutcs.nclient_android.activity;

import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.WindowManager;

import com.readystatesoftware.systembartint.SystemBarTintManager;

import org.dlutcs.nclient_android.R;
import org.dlutcs.nclient_android.util.NewRequest;
import org.dlutcs.nclient_android.util.RequestManager;

/**
 * Created by linwei on 15-10-5.
 */
public class BaseActivity extends AppCompatActivity {


    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void initStatusWindow() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
            SystemBarTintManager tintManager = new SystemBarTintManager(this);
            tintManager.setStatusBarTintResource(R.color.app_blue);
            tintManager.setStatusBarTintEnabled(true);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.initStatusWindow();
    }

    public RequestManager getRequestManager() {
        return RequestManager.getInstance();
    }

    public void addRequest(NewRequest request) {
        getRequestManager().addToRequestQueue(request);
    }

}
