package com.tommy.procrastinationtimer.storage.disk;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.tommy.procrastinationtimer.models.Task;
import com.tommy.procrastinationtimer.storage.TaskStorage;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public abstract class TaskStorageFile implements TaskStorage {

    private ArrayList<Task> data;
    File storageFile;
    Gson gson;

    @Override
    public List<Task> getAll() {
        try (BufferedReader br = new BufferedReader(new FileReader(storageFile))) {
            data = gson.fromJson(br.readLine(), new TypeToken<ArrayList<Task>>() {
            }.getType());
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (data == null) {
            data = new ArrayList<>();
        }
        return data;
    }

    @Override
    public Task getById(long index) {
        return data.get((int) index);
    }

    @Override
    public void insert(Task task) {
        data.add(task);
        saveToFile();
    }

    @Override
    public void insertAll(Task... tasks) {
        data.addAll(Arrays.asList(tasks));
        saveToFile();
    }

    @Override
    public void delete(Task task) {
        data.remove(task);
        saveToFile();
    }

    @Override
    public void deleteAll() {
        data.clear();
        saveToFile();
    }

    private void saveToFile() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(storageFile))) {
            bw.write(gson.toJson(data));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
