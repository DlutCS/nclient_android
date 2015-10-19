package org.dlutcs.nclient_android.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.android.volley.Response;
import com.android.volley.VolleyError;

import org.dlutcs.nclient_android.R;
import org.dlutcs.nclient_android.model.News;
import org.dlutcs.nclient_android.util.NewRequest;
import org.dlutcs.nclient_android.util.Toaster;
import org.dlutcs.nclient_android.util.Utils;
import org.dlutcs.nclient_android.view.FooterView;
import org.w3c.dom.Text;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by linwei on 15-10-18.
 */
public class NewsDetailActivity extends BaseActivity{

    private String mNewsId;


    @InjectView(R.id.title)
    TextView mTitle;
    @InjectView(R.id.from)
    TextView mFrom;
    @InjectView(R.id.time)
    TextView mTime;

    @InjectView(R.id.content_webview)
    WebView mContentWebVew;
    @InjectView(R.id.comment_list)
    ListView mCommentList;
    @InjectView(R.id.progress_bar)
    ProgressBar mProgressBar;

    private FooterView mFootView;

    public static void startActivity(Context context, String id) {
        Intent intent = new Intent(context, NewsDetailActivity.class);
        intent.putExtra("newsid", id);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_detail);
        ButterKnife.inject(this);
        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null){
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        mNewsId = getIntent().getStringExtra("newsid");
        mProgressBar.setVisibility(View.VISIBLE);
        fetchNews();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                finish();
                break;

        }
        return super.onOptionsItemSelected(item);
    }

    protected void fetchNews(){
        NewRequest<News> request = getRequestManager().fetchNewsDetail(mNewsId,
                new Response.Listener<News>() {
                    @Override
                    public void onResponse(News news) {
                        mProgressBar.setVisibility(View.GONE);
                        updateNews(news);

                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toaster.show(getApplication(), error.getMessage());
                        mProgressBar.setVisibility(View.GONE);
                    }
                });
        addRequest(request);
    }

    private void updateNews(final News news){
        mTitle.setText(news.title);
        mFrom.setText(news.author);
        mTime.setText(Utils.getTime(news.createTime));
        mContentWebVew.loadData(news.content,"text/html; charset=UTF-8", null);
    }
}
