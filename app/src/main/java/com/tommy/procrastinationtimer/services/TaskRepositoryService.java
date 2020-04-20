package com.tommy.procrastinationtimer.services;

import androidx.lifecycle.MutableLiveData;
import com.tommy.procrastinationtimer.models.Task;

import java.util.ArrayList;
import java.util.List;

public class TaskRepositoryService {

    private static TaskRepositoryService instance;
    private ArrayList<Task> dataSet = new ArrayList<>();

    public static TaskRepositoryService getInstance() {
        if (instance == null) {
            instance = new TaskRepositoryService();
        }
        return instance;
    }

    public MutableLiveData<List<Task>> getTasks() {
        MutableLiveData<List<Task>> data = new MutableLiveData<>();
        data.setValue(dataSet);
        return data;
    }

    public void addTask(Task newTask) {
        dataSet.add(newTask);
    }
}
