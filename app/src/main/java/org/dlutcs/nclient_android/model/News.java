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
    @SerializedName("content_short")
    public String contentShort;
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
    @SerializedName("author")
    public String author;


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeString(this.title);
        dest.writeString(this.aliasTitle);
        dest.writeString(this.content);
        dest.writeString(this.contentShort);
        dest.writeString(this.createTime);
        dest.writeInt(this.commentCount);
        dest.writeInt(readCount);
        dest.writeInt(likeCount);
        dest.writeInt(dislikeCount);
        dest.writeString(coverUrl);
        dest.writeString(categoryId);
        dest.writeString(authorId);

    }

    private News(Parcel in) {
        this.id = in.readString();
        this.title = in.readString();
        this.aliasTitle = in.readString();
        this.content = in.readString();
        this.contentShort = in.readString();
        this.createTime = in.readString();
        this.commentCount = in.readInt();
        this.readCount = in.readInt();
        this.likeCount = in.readInt();
        this.dislikeCount = in.readInt();
        this.coverUrl = in.readString();
        this.categoryId = in.readString();
        this.authorId = in.readString();
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
