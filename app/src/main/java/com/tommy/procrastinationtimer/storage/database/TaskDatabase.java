package com.tommy.procrastinationtimer.storage.database;

import androidx.room.Database;
import androidx.room.RoomDatabase;
import com.tommy.procrastinationtimer.storage.dao.task.TaskDao;
import com.tommy.procrastinationtimer.storage.dao.task.TaskDb;

@Database(entities = {TaskDb.class}, version = TaskDatabase.DB_VERSION)
public abstract class TaskDatabase extends RoomDatabase {
    public static final int DB_VERSION = 1;
    public static final String DB_FILE_NAME = "tasks.db";

    public abstract TaskDao getTaskDao();
}
