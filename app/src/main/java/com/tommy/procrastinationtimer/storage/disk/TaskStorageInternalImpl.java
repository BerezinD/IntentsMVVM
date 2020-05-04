package com.tommy.procrastinationtimer.storage.disk;

import android.content.Context;
import com.google.gson.Gson;
import com.tommy.procrastinationtimer.storage.TaskStorage;

import java.io.File;

public class TaskStorageInternalImpl extends TaskStorageFile {

    private static final String FILE_NAME = "tasks";
    private static TaskStorageInternalImpl instance;

    private TaskStorageInternalImpl(Context context) {
        gson = new Gson();
        storageFile = new File(context.getFilesDir(), FILE_NAME);
        getAll();
    }

    public static TaskStorage getInstance(Context context) {
        if (instance == null) {
            instance = new TaskStorageInternalImpl(context);
        }
        return instance;
    }
}
