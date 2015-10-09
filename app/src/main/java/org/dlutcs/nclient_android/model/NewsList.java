package org.dlutcs.nclient_android.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by linwei on 15-10-6.
 */
public class NewsList {

    @SerializedName("newslist")
    public ArrayList<News> newsList;

    public int start;
    public int count;

    public NewsList() {
        this.newsList = new ArrayList<>();
    }
}
