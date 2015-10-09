package org.dlutcs.nclient_android.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.view.ViewGroup;

import org.dlutcs.nclient_android.fragment.CategoryNewsFragment;
import org.dlutcs.nclient_android.model.Category;

import java.util.ArrayList;

/**
 * Created by linwei on 15-10-6.
 */
public class NewsPagerAdapter extends FragmentPagerAdapter {

    private ArrayList<Category> mCategories = new ArrayList<>();

    public NewsPagerAdapter(FragmentManager fm) {
        super(fm);

    }

    public void addCategories(ArrayList<Category> categories){
        mCategories.clear();
        mCategories.addAll(categories);
        this.notifyDataSetChanged();
    }

    @Override
    public Fragment getItem(int position) {
        return CategoryNewsFragment.newInstance(mCategories.get(position));
    }

    @Override
    public int getCount() {
        return mCategories.size();
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        return super.instantiateItem(container, position);
    }
}

