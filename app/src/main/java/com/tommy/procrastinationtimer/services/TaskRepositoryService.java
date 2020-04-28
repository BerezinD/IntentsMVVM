package com.tommy.procrastinationtimer.services;

import android.content.Context;
import androidx.room.Room;
import com.tommy.procrastinationtimer.models.Task;
import com.tommy.procrastinationtimer.storage.TaskStorage;
import com.tommy.procrastinationtimer.storage.TaskStorageImpl;
import com.tommy.procrastinationtimer.storage.database.TaskDatabase;

import java.util.List;

public class TaskRepositoryService {

    private static TaskRepositoryService instance;
    private TaskStorage storage;
    private TaskDatabase database;

    private TaskRepositoryService() {
    }

    public static TaskRepositoryService getInstance(Context context) {
        if (instance == null) {
            instance = new TaskRepositoryService();
            instance.database = Room.databaseBuilder(
                    context.getApplicationContext(),
                    TaskDatabase.class,
                    TaskDatabase.DB_FILE_NAME)
                    .allowMainThreadQueries()
                    .build();
            instance.storage = new TaskStorageImpl(instance.database.getTaskDao());
        }
        return instance;
    }

    public List<Task> getTasks() {
        return storage.getAll();
    }

    public void addTask(Task newTask) {
        storage.insert(newTask);
    }

    public void deleteTask(Task task) {
        storage.delete(task);
    }

    public void deleteAll() {
        storage.deleteAll();
    }
}
