package org.dlutcs.nclient_android.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ListView;

import com.android.volley.Response;
import com.android.volley.VolleyError;

import org.dlutcs.nclient_android.R;
import org.dlutcs.nclient_android.adapter.NewsListAdapter;
import org.dlutcs.nclient_android.model.Category;
import org.dlutcs.nclient_android.model.News;
import org.dlutcs.nclient_android.model.NewsList;
import org.dlutcs.nclient_android.util.NewRequest;
import org.dlutcs.nclient_android.util.Toaster;
import org.dlutcs.nclient_android.view.FooterView;
import org.dlutcs.nclient_android.view.NewsListHeaderView;
import org.dlutcs.nclient_android.view.SwipeRefreshLayout;


import java.util.ArrayList;

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
    private NewsListAdapter mAdapter;
    private FooterView mFooterView;

    private boolean canLoad = false;
    private int lastItemIndex;

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
                fetchNews(0);
            }
        });
        mListView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                if (scrollState == AbsListView.OnScrollListener.SCROLL_STATE_IDLE
                        && lastItemIndex >= mAdapter.getCount() - 1 && canLoad) {
                    mFooterView.showProgress();
                    fetchNews(lastItemIndex);
                }
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount,
                                 int totalItemCount) {
                lastItemIndex = firstVisibleItem + visibleItemCount - 1;
            }
        });
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mHeaderView = new NewsListHeaderView(getContext());
        mListView.addHeaderView(mHeaderView);
        mFooterView = new FooterView(getContext());
        mListView.addFooterView(mFooterView);
        mAdapter = new NewsListAdapter(getActivity());
        mListView.setAdapter(mAdapter);
        fetchNews(0);
        mFooterView.showProgress();
    }

    protected void fetchNews(final int start){
        canLoad = false;
        NewRequest<NewsList> request = getRequestManager().fetchCategoryLatestNews(mCategory.id,
                start, 10, new Response.Listener<NewsList>() {
                    @Override
                    public void onResponse(NewsList result) {
                        if(!isAdded()){
                            return;
                        }
                        if(start == 0){
                            mAdapter.clear();
                            if(result.newsList.size() > 4) {
                                mHeaderView.addNews(new ArrayList<>(result.newsList.subList(0, 4)));
                            }else{
                                mHeaderView.addNews(result.newsList);
                            }
                        }
                        mAdapter.addAll(result.newsList);

                        mSwipe.setRefreshing(false);
                        if(result.count > 0){
                            mFooterView.showNone();
                            canLoad = true;
                        }else{
                            if (mAdapter.getCount() == 0) {
                                mFooterView.showText(R.string.error_result_empty);
                            } else {
                                mFooterView.showNone();
                            }
                            canLoad = false;
                        }

                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        if(!isAdded()){
                            return;
                        }
                        mSwipe.setRefreshing(false);
                        mFooterView.showText(
                                error.getMessage(),
                                new FooterView.CallBack() {
                                    @Override
                                    public void callBack(View view) {
                                        fetchNews(start);
                                        mFooterView.showProgress();
                                    }
                                }
                        );
                    }
                }

        );
        request.setTag(getActivity());
        addRequest(request);
    }

    @Override
    public void onResume() {
        mHeaderView.onResume();
        super.onResume();

    }

    @Override
    public void onPause() {
        mHeaderView.onPause();
        super.onPause();
    }
}
