package com.tommy.procrastinationtimer.services;

import android.content.Context;
import com.tommy.procrastinationtimer.models.Storage;
import com.tommy.procrastinationtimer.models.Task;
import com.tommy.procrastinationtimer.storage.TaskStorage;
import com.tommy.procrastinationtimer.storage.database.TaskStorageImpl;
import com.tommy.procrastinationtimer.storage.preferences.TaskStoragePrefImpl;

import java.util.List;

public class TaskRepositoryService {

    private static TaskRepositoryService instance;
    private TaskStorage storage;

    private TaskRepositoryService() {
    }

    public static TaskRepositoryService getInstance(Storage storageType, Context context) {
        if (instance == null) {
            instance = new TaskRepositoryService();
            instance.storage = getStorage(storageType, context);
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

    public void changeStorage(Storage storageType, Context context) {
        switch (storageType) {
            case SHARED_PREF:
                storage = TaskStoragePrefImpl.getInstance(context);
                break;
            case SQL:
                storage = TaskStorageImpl.getInstance(context);
                break;
        }
    }

    private static TaskStorage getStorage(Storage storage, Context context) {
        switch (storage) {
            case SHARED_PREF:
                return TaskStoragePrefImpl.getInstance(context);
            case SQL:
                return TaskStorageImpl.getInstance(context);
        }
        return TaskStorageImpl.getInstance(context);
    }
}
