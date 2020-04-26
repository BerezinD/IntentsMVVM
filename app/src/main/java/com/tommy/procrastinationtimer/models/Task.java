package com.tommy.procrastinationtimer.models;

import android.os.Parcel;
import android.os.Parcelable;

public class Task implements Parcelable {

    private String title;
    private Long time;

    public Task(String title, Long time) {
        this.title = title;
        this.time = time;
    }

    public Task() {
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Long getTime() {
        return time;
    }

    public void setTime(Long time) {
        this.time = time;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.title);
        dest.writeValue(this.time);
    }

    protected Task(Parcel in) {
        this.title = in.readString();
        this.time = (Long) in.readValue(Long.class.getClassLoader());
    }

    public static final Parcelable.Creator<Task> CREATOR = new Parcelable.Creator<Task>() {
        @Override
        public Task createFromParcel(Parcel source) {
            return new Task(source);
        }

        @Override
        public Task[] newArray(int size) {
            return new Task[size];
        }
    };
}
