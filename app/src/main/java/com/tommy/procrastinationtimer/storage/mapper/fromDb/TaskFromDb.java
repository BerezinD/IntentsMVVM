package com.tommy.procrastinationtimer.storage.mapper.fromDb;

import com.tommy.procrastinationtimer.models.Task;
import com.tommy.procrastinationtimer.services.Mapper;
import com.tommy.procrastinationtimer.storage.dao.task.TaskDb;

import java.util.ArrayList;
import java.util.List;

public class TaskFromDb implements Mapper<TaskDb, Task> {
    @Override
    public Task map(TaskDb from) {
        return new Task(from.title, from.time);
    }

    @Override
    public List<Task> map(List<TaskDb> from) {
        List<Task> result = new ArrayList<>();
        for (TaskDb task : from) {
            result.add(new Task(task.title, task.time));
        }
        return result;
    }

    @Override
    public Task[] map(TaskDb... from) {
        Task[] result = new Task[from.length];
        for (int i = 0; i < from.length; i++) {
            result[i] = new Task(from[i].title, from[i].time);
        }
        return result;
    }
}
