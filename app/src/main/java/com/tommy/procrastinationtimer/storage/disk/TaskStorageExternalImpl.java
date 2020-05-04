package com.tommy.procrastinationtimer.storage.disk;

import android.content.Context;
import android.os.Environment;
import android.widget.Toast;
import com.google.gson.Gson;
import com.tommy.procrastinationtimer.storage.TaskStorage;

import java.io.File;

public class TaskStorageExternalImpl extends TaskStorageFile {

    private final static String FILE_NAME_EXT = "tasks_ext";
    private static TaskStorageExternalImpl instance;

    private TaskStorageExternalImpl(Context context) {
        gson = new Gson();
        storageFile = getStorageFile(context);
    }

    public static TaskStorage getInstance(Context context) {
        if (instance == null) {
            instance = new TaskStorageExternalImpl(context);
        }
        return instance;
    }

    private File getStorageFile(Context context) {
        if (!isExternalStorageWritable()) {
            Toast.makeText(context, "Cannot permit a file access", Toast.LENGTH_SHORT).show();
        }
        return new File(context.getExternalFilesDir(null), FILE_NAME_EXT);
    }

    /**
     * Checks if a volume containing external storage is available
     * for read and write.
     */
    private static boolean isExternalStorageWritable() {
        return Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState());
    }
}
