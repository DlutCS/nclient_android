package org.dlutcs.nclient_android.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.dlutcs.nclient_android.R;
import org.dlutcs.nclient_android.model.Category;
import org.dlutcs.nclient_android.view.CategoryScrollView;

import java.util.ArrayList;

import butterknife.ButterKnife;
import butterknife.InjectView;


public class MainFragment extends Fragment {

    @InjectView(R.id.category_scroll)
    CategoryScrollView mCategoryScrollView;

    public static MainFragment newInstance() {
        MainFragment fragment = new MainFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        ArrayList<Category> categories = new ArrayList<>();
        for (int i=0;i<10;i++){
            Category item = new Category();
            item.id = "1000";
            item.name = "热点";
            categories.add(item);
        }
        mCategoryScrollView.addCategorys(categories);
        mCategoryScrollView.setSelectIndex(0);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        ButterKnife.inject(this, view);
        return view;
    }

}
