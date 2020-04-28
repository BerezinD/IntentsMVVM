package com.tommy.procrastinationtimer.storage;

import com.tommy.procrastinationtimer.models.Task;
import com.tommy.procrastinationtimer.services.Mapper;
import com.tommy.procrastinationtimer.storage.dao.task.TaskDao;
import com.tommy.procrastinationtimer.storage.dao.task.TaskDb;
import com.tommy.procrastinationtimer.storage.mapper.fromDb.TaskFromDb;
import com.tommy.procrastinationtimer.storage.mapper.toDb.TaskToDb;

import java.util.List;

public class TaskStorageImpl implements TaskStorage {

    private TaskDao dao;
    private Mapper<TaskDb, Task> mapperToTask = new TaskFromDb();
    private Mapper<Task, TaskDb> mapperToDb = new TaskToDb();

    public TaskStorageImpl(TaskDao dao) {
        this.dao = dao;
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
