package com.tommy.procrastinationtimer.storage;

import com.tommy.procrastinationtimer.models.Task;

import java.util.List;

public interface TaskStorage {

    List<Task> getAll();

    Task getById(long id);

    void insert(Task task);

    void insertAll(Task... tasks);

    void delete(Task task);

    void deleteAll();
}
