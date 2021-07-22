package com.zzl.defineview.priorityqueue;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by zhenglin.zhu on 2021/7/19.
 */
public class UserBean implements Parcelable, IBaseBean, Comparable<UserBean> {

    public int age;
    public String name;

    public UserBean() {
        super();
    }

    public UserBean(int age, String name) {
        super();
        this.age = age;
        this.name = name;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.age);
        dest.writeString(this.name);
    }

    public void readFromParcel(Parcel source) {
        this.age = source.readInt();
        this.name = source.readString();
    }

    protected UserBean(Parcel in) {
        this.age = in.readInt();
        this.name = in.readString();
    }

    public static final Creator<UserBean> CREATOR = new Creator<UserBean>() {
        @Override
        public UserBean createFromParcel(Parcel source) {
            return new UserBean(source);
        }

        @Override
        public UserBean[] newArray(int size) {
            return new UserBean[size];
        }
    };

    @Override
    public String toString() {
        return "UserBean{" + "age=" + age + ", name='" + name + '\'' + '}';
    }

    @Override
    public int compareTo(UserBean o) {
        return this.age - o.age;
    }
}
