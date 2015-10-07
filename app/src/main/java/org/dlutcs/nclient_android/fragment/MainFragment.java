package org.dlutcs.nclient_android.fragment;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.android.volley.Response;
import com.android.volley.VolleyError;

import org.dlutcs.nclient_android.R;
import org.dlutcs.nclient_android.adapter.NewsPagerAdapter;
import org.dlutcs.nclient_android.model.CategoryList;
import org.dlutcs.nclient_android.util.NewRequest;
import org.dlutcs.nclient_android.view.CategoryScrollStrip;

import butterknife.ButterKnife;
import butterknife.InjectView;


public class MainFragment extends BaseFragment {

    @InjectView(R.id.category_scroll)
    CategoryScrollStrip mCategoryScrollStrip;
    @InjectView(R.id.main_pager)
    ViewPager mViewPager;
    @InjectView(R.id.progress_bar)
    ProgressBar mProgressBar;
    private NewsPagerAdapter mViewPagerAdapter;


    public static MainFragment newInstance() {
        return new MainFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mCategoryScrollStrip.setViewPager(mViewPager);
        mViewPagerAdapter = new NewsPagerAdapter(getFragmentManager());
        mViewPager.setAdapter(mViewPagerAdapter);
        fetchCategorys();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        ButterKnife.inject(this, view);
        return view;
    }

    private void fetchCategorys(){
        mProgressBar.setVisibility(View.VISIBLE);
        NewRequest<CategoryList> request = getRequestManager().fetchCategorys(
                new Response.Listener<CategoryList>() {
                    @Override
                    public void onResponse(CategoryList categoryList) {
                        if(categoryList.categories.size() > 0) {
                            mCategoryScrollStrip.addCategorys(categoryList.categories);
                            mViewPagerAdapter.addCategories(categoryList.categories);
                            mCategoryScrollStrip.setSelectIndex(0);
                            mProgressBar.setVisibility(View.GONE);
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });
        request.setTag(getActivity());
        addRequest(request);
    }



}
