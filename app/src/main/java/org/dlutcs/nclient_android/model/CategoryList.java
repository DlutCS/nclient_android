package org.dlutcs.nclient_android.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by linwei on 15-10-6.
 */
public class CategoryList {

    public ArrayList<Category> categories;

    public int count;

    public CategoryList() {
        this.categories = new ArrayList<>();
    }

}

