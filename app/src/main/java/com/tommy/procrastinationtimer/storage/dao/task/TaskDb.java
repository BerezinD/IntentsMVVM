package com.tommy.procrastinationtimer.storage.dao.task;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class TaskDb {
    @PrimaryKey(autoGenerate = true)
    public long uid;

    @ColumnInfo(name = "title")
    public String title;

    @ColumnInfo(name = "time")
    public Long time;

    public TaskDb(String title, Long time) {
        this.title = title;
        this.time = time;
    }
}
