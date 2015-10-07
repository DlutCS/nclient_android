package org.dlutcs.nclient_android.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by linwei on 15-10-5.
 */
public class Category implements Parcelable {

    public String id;
    public String name;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeString(this.name);
    }

    private Category(Parcel in) {
        this.id = in.readString();
        this.name = in.readString();
    }

    public static Creator<Category> CREATOR = new Creator<Category>() {
        @Override
        public Category createFromParcel(Parcel source) {
            return new Category(source);
        }

        @Override
        public Category[] newArray(int size) {
            return new Category[size];
        }
    };
}
