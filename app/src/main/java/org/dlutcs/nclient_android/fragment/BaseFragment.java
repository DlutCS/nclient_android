package org.dlutcs.nclient_android.fragment;

import android.support.v4.app.Fragment;

import com.android.volley.Request;

import org.dlutcs.nclient_android.util.NewRequest;
import org.dlutcs.nclient_android.util.RequestManager;

/**
 * Created by linwei on 15-10-6.
 */
public class BaseFragment extends Fragment{


    public RequestManager getRequestManager() {
        return RequestManager.getInstance();
    }

    public void addRequest(NewRequest request) {
        getRequestManager().addToRequestQueue(request);
    }

}
