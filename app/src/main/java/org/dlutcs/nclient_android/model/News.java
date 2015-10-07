package org.dlutcs.nclient_android.model;

/**
 * Created by linwei on 15-10-6.
 */

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * Created by linwei on 15-10-5.
 */
public class News implements Parcelable {

    public String id;
    public String title;
    @SerializedName("alias_title")
    public String aliasTitle;
    public String content;
    @SerializedName("create_time")
    public String createTime;
    @SerializedName("comment_count")
    public int commentCount;
    @SerializedName("read_count")
    public int readCount;
    @SerializedName("like_count")
    public int likeCount;
    @SerializedName("dislike_count")
    public int dislikeCount;
    @SerializedName("cover_url")
    public String coverUrl;
    @SerializedName("category_id")
    public String categoryId;
    @SerializedName("author_id")
    public String authorId;


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeString(this.title);
    }

    private News(Parcel in) {
        this.id = in.readString();
        this.title = in.readString();
    }

    public static Creator<News> CREATOR = new Creator<News>() {
        @Override
        public News createFromParcel(Parcel source) {
            return new News(source);
        }

        @Override
        public News[] newArray(int size) {
            return new News[size];
        }
    };
}
