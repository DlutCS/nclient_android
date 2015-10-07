package org.dlutcs.nclient_android.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import org.dlutcs.nclient_android.R;
import org.dlutcs.nclient_android.model.Category;
import org.dlutcs.nclient_android.util.Toaster;
import org.dlutcs.nclient_android.view.NewsListHeaderView;
import org.dlutcs.nclient_android.view.SwipeRefreshLayout;


import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by linwei on 15-10-6.
 */
public class CategoryNewsFragment extends BaseFragment{

    @InjectView(R.id.swipe_refresh_layout)
    SwipeRefreshLayout mSwipe;
    @InjectView(R.id.listView)
    ListView mListView;

    private Category mCategory = null;
    private NewsListHeaderView mHeaderView;

    public static Fragment newInstance(Category category) {
        CategoryNewsFragment fragment = new CategoryNewsFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable("category", category);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (null != getArguments()) {
            mCategory = getArguments().getParcelable("category");
        }
        if(mCategory == null){
            Toaster.show(getActivity(), R.string.category_empty);
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_category_news, container, false);
        ButterKnife.inject(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mSwipe.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

            }
        });
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mHeaderView = new NewsListHeaderView(getContext());
        mListView.addHeaderView(mHeaderView);
    }

}
