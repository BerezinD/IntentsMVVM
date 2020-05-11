package com.tommy.procrastinationtimer.storage.preferences;

import android.content.Context;
import android.content.SharedPreferences;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.tommy.procrastinationtimer.models.Task;
import com.tommy.procrastinationtimer.storage.TaskStorage;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TaskStoragePrefImpl implements TaskStorage {

    private static final String PREFERENCE_FILE_KEY = "com.tommy.procrastinationtimer.storage.PREFERENCE_FILE_KEY";
    private static final String PREFERENCE_STORAGE_KEY = "com.tommy.procrastinationtimer.storage.PREFERENCE_STORAGE_KEY";
    private static TaskStorage instance;
    private SharedPreferences preferences;
    private ArrayList<Task> data;
    private Gson gson = new Gson();

    private TaskStoragePrefImpl(Context context) {
        this.preferences = context.getSharedPreferences(PREFERENCE_FILE_KEY, Context.MODE_PRIVATE);
        data = gson.fromJson(preferences.getString(PREFERENCE_STORAGE_KEY, null), new TypeToken<ArrayList<Task>>() {
        }.getType());
        if (data == null) {
            data = new ArrayList<>();
        }
    }

    public static TaskStorage getInstance(Context context) {
        if (instance == null) {
            instance = new TaskStoragePrefImpl(context);
        }
        return instance;
    }

    @Override
    public List<Task> getAll() {
        return data;
    }

    @Override
    public Task getById(long index) {
        return data.get((int) index);
    }

    @Override
    public void insert(Task task) {
        data.add(task);
        saveToPreferences();
    }

    @Override
    public void insertAll(Task... tasks) {
        data.addAll(Arrays.asList(tasks));
        saveToPreferences();
    }

    @Override
    public void delete(Task task) {
        data.remove(task);
        saveToPreferences();
    }

    @Override
    public void deleteAll() {
        data.clear();
        saveToPreferences();
    }

    private void saveToPreferences() {
        preferences.edit()
                .putString(PREFERENCE_STORAGE_KEY, gson.toJson(data))
                .apply();
    }
}
