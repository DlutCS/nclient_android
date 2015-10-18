package org.dlutcs.nclient_android.util;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.google.gson.reflect.TypeToken;

import org.dlutcs.nclient_android.BuildConfig;
import org.dlutcs.nclient_android.model.CategoryList;
import org.dlutcs.nclient_android.model.News;
import org.dlutcs.nclient_android.model.NewsList;

import java.lang.reflect.Type;

/**
 *
 * Created by linwei on 15-10-6.
 */
public class RequestManager {

    public static final String DEV_API_HOST = "dev.py.senyu.me";
    public static final String API_HOST = "py.senyu.me";

    private static RequestManager Instance;

    private RequestQueue mRequestQueue;
    private Context mContext;

    private RequestManager(){}

    public static RequestManager getInstance() {
        if (Instance == null) {
            Instance = new RequestManager();
        }
        return Instance;
    }

    public RequestManager init(Context context) {
        this.mContext = context;
        return this;
    }

    public RequestQueue getRequestQueue() {
        if (mRequestQueue == null) {
            // getApplicationContext() is key, it keeps you from leaking the
            // Activity or BroadcastReceiver if someone passes one in.
            mRequestQueue = Volley.newRequestQueue(mContext);
        }
        return mRequestQueue;
    }

    public <T> void addToRequestQueue(Request<T> req) {
        getRequestQueue().add(req);
    }

    public static String url(boolean https, String path) {
        StringBuilder urlBuilder = new StringBuilder(https ? "https://" : "http://");
        if(BuildConfig.DEBUG) {
            urlBuilder.append(DEV_API_HOST);
        }else{
            urlBuilder.append(API_HOST);
        }
        urlBuilder.append("/api");
        if (!path.startsWith("/")) {
            urlBuilder.append("/");
        }
        urlBuilder.append(path);
        if(!path.endsWith("/")){
            urlBuilder.append("/");
        }
        return urlBuilder.toString();
    }

    public NewRequest<CategoryList> fetchCategorys(Response.Listener<CategoryList> listener,
                                                   Response.ErrorListener errorListener){
        String url = url(false, "/category/");
        Type type = new TypeToken<CategoryList>(){}.getType();
        NewRequest<CategoryList> request = new NewRequest<>(
                Request.Method.GET, url, type, listener, errorListener);
        return request;
    }

    public NewRequest<NewsList> fetchCategoryLatestNews(String categoryId,
                                                        int start, int count,
                                                 Response.Listener<NewsList> listener,
                                                 Response.ErrorListener errorListener){
        String url = url(false, String.format("/newslist/category/%1$s/latest/", categoryId));

        Type type = new TypeToken<NewsList>(){}.getType();
        NewRequest<NewsList> request = new NewRequest<>(
                Request.Method.GET, url, type, listener, errorListener);
        request.param("start", String.valueOf(start));
        request.param("count", String.valueOf(count));
        return request;
    }

    public NewRequest<NewsList> fetchLatestNews(int start, int count,
                                                        Response.Listener<NewsList> listener,
                                                        Response.ErrorListener errorListener){
        String url = url(false, "/newslist/latest/");

        Type type = new TypeToken<NewsList>(){}.getType();
        NewRequest<NewsList> request = new NewRequest<>(
                Request.Method.GET, url, type, listener, errorListener);
        request.param("start", String.valueOf(start));
        request.param("count", String.valueOf(count));
        return request;
    }

    public NewRequest<NewsList> fetchCategoryPopularNews(String categoryId,
                                                        int start, int count,
                                                        Response.Listener<NewsList> listener,
                                                        Response.ErrorListener errorListener){
        String url = url(false, String.format("/newslist/category/%1$s/popular/", categoryId));

        Type type = new TypeToken<NewsList>(){}.getType();
        NewRequest<NewsList> request = new NewRequest<>(
                Request.Method.GET, url, type, listener, errorListener);
        request.param("start", String.valueOf(start));
        request.param("count", String.valueOf(count));
        return request;
    }

    public NewRequest<News> fetchNewsDetail(String newsId,
                                                         Response.Listener<News> listener,
                                                         Response.ErrorListener errorListener){
        String url = url(false, String.format("/news/%1$s/", newsId));

        Type type = new TypeToken<News>(){}.getType();
        NewRequest<News> request = new NewRequest<>(
                Request.Method.GET, url, type, listener, errorListener);
        return request;
    }

}
