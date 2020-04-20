package com.tommy.procrastinationtimer.models;

public class Task {

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
}
