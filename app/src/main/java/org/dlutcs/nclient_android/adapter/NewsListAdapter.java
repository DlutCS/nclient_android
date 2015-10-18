package org.dlutcs.nclient_android.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import org.dlutcs.nclient_android.R;
import org.dlutcs.nclient_android.activity.NewsDetailActivity;
import org.dlutcs.nclient_android.model.News;
import org.dlutcs.nclient_android.util.ImageLoader;
import org.dlutcs.nclient_android.util.NewRequest;
import org.dlutcs.nclient_android.util.Utils;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by linwei on 15-10-7.
 */
public class NewsListAdapter extends BaseArrayAdapter<News>{

    public NewsListAdapter(Context context) {
        super(context);
    }

    @Override
    public View getView(final News item, LayoutInflater inflater, int position, View convertView,
                        ViewGroup parent) {
        ViewHolder holder;
        if(convertView == null){
            convertView = inflater.inflate(R.layout.view_item_news, parent, false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        }else{
            holder = (ViewHolder)convertView.getTag();
        }
        holder.title.setText(item.title);
        holder.contentShort.setText(item.contentShort);
        holder.createTime.setText(Utils.getTime(item.createTime));
        ImageLoader.load(item.coverUrl).into(holder.cover);
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NewsDetailActivity.startActivity(getContext(), item.id);
            }
        });
        return convertView;
    }

    static class ViewHolder{
        public ViewHolder(View convertView) {
            ButterKnife.inject(this, convertView);
        }

        @InjectView(R.id.title)
        TextView title;
        @InjectView(R.id.content_short)
        TextView contentShort;
        @InjectView(R.id.cover)
        ImageView cover;
        @InjectView(R.id.create_time)
        TextView createTime;
    }
}
