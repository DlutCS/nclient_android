package org.dlutcs.nclient_android.util;

import android.util.Log;

import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Response;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.HttpHeaderParser;
import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;

import java.io.StringReader;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.util.HashMap;

/**
 * Created by linwei on 15-10-6.
 *
 */
public class NewRequest<T> extends OkRequest<T>{

    private Type mType;
    private static Gson gsonInstance;

    public Gson getGson(){
        if(gsonInstance == null){
            gsonInstance = new Gson();
        }
        return gsonInstance;
    }


    public NewRequest(int method, String url, Type type, Response.Listener<T> listener,
                      Response.ErrorListener errorListener) {
        super(method, url, listener, errorListener);
        this.mType = type;
    }

    @Override
    @SuppressWarnings("unchecked")
    protected Response<T> parseNetworkResponse(NetworkResponse networkResponse) {
        try {

            byte[] data = networkResponse.data;
            String json = new String(data, HttpHeaderParser.parseCharset(networkResponse.headers));
            if (VolleyLog.DEBUG) {
                VolleyLog.d("response:%s", json);
            }
            if (mType != null) {
                return Response.success((T) getGson().fromJson(
                        new JsonReader(new StringReader(json)), mType),
                        HttpHeaderParser.parseCacheHeaders(networkResponse)
                );
            } else {
                return (Response<T>) Response.success(json,
                        HttpHeaderParser.parseCacheHeaders(networkResponse));
            }
        } catch (UnsupportedEncodingException e) {
            Log.e("message", e.getMessage());
            return Response.error(new ParseError(e));
        } catch (OutOfMemoryError e) {
            e.printStackTrace();
            return Response.error(new ParseError(e));
        }
    }

}
