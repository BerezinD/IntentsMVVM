package com.tommy.procrastinationtimer.services;

import com.tommy.procrastinationtimer.models.Task;

import java.util.ArrayList;
import java.util.List;

public class TaskRepositoryService {

    private static TaskRepositoryService instance;
    private ArrayList<Task> dataSet;

    private TaskRepositoryService() {
        this.dataSet = new ArrayList<>();
    }

    public static TaskRepositoryService getInstance() {
        if (instance == null) {
            instance = new TaskRepositoryService();
        }
        return instance;
    }

    public List<Task> getTasks() {
        return dataSet;
    }

    public void addTask(Task newTask) {
        dataSet.add(newTask);
    }
}
