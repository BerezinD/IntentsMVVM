package com.tommy.procrastinationtimer.storage.database;

import android.content.Context;
import androidx.room.Room;
import com.tommy.procrastinationtimer.models.Task;
import com.tommy.procrastinationtimer.services.Mapper;
import com.tommy.procrastinationtimer.storage.TaskStorage;
import com.tommy.procrastinationtimer.storage.dao.task.TaskDao;
import com.tommy.procrastinationtimer.storage.dao.task.TaskDb;
import com.tommy.procrastinationtimer.storage.mapper.fromDb.TaskFromDb;
import com.tommy.procrastinationtimer.storage.mapper.toDb.TaskToDb;

import java.util.List;

public class TaskStorageImpl implements TaskStorage {

    private static TaskStorage instance;
    private TaskDao dao;
    private Mapper<TaskDb, Task> mapperToTask = new TaskFromDb();
    private Mapper<Task, TaskDb> mapperToDb = new TaskToDb();

    private TaskStorageImpl(Context context) {
        this.dao = Room.databaseBuilder(
                context.getApplicationContext(),
                TaskDatabase.class,
                TaskDatabase.DB_FILE_NAME)
                .allowMainThreadQueries()
                .build()
                .getTaskDao();
    }

    public static TaskStorage getInstance(Context context) {
        if (instance == null) {
            instance = new TaskStorageImpl(context);
        }
        return instance;
    }

    @Override
    public List<Task> getAll() {
        return mapperToTask.map(dao.getAll());
    }

    @Override
    public Task getById(long id) {
        return mapperToTask.map(dao.getById(id));
    }

    @Override
    public void insert(Task task) {
        dao.insertAll(mapperToDb.map(task));
    }

    @Override
    public void insertAll(Task... tasks) {
        dao.insertAll(mapperToDb.map(tasks));
    }

    @Override
    public void delete(Task task) {
        dao.delete(mapperToDb.map(task));
    }

    @Override
    public void deleteAll() {
        dao.deleteAll();
    }
}
