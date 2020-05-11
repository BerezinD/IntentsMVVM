package com.tommy.procrastinationtimer.storage.mapper.toDb;

import com.tommy.procrastinationtimer.models.Task;
import com.tommy.procrastinationtimer.services.Mapper;
import com.tommy.procrastinationtimer.storage.dao.task.TaskDb;

import java.util.ArrayList;
import java.util.List;

public class TaskToDb implements Mapper<Task, TaskDb> {
    @Override
    public TaskDb map(Task from) {
        return new TaskDb(from.getTitle(), from.getTime());
    }

    @Override
    public List<TaskDb> map(List<Task> from) {
        List<TaskDb> result = new ArrayList<>();
        for (Task task : from) {
            result.add(new TaskDb(task.getTitle(), task.getTime()));
        }
        return result;
    }

    @Override
    public TaskDb[] map(Task... from) {
        TaskDb[] result = new TaskDb[from.length];
        for (int i = 0; i < from.length; i++) {
            result[i] = new TaskDb(from[i].getTitle(), from[i].getTime());
        }
        return result;
    }
}
