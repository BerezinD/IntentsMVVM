package com.tommy.procrastinationtimer.viewmodels;

import android.content.Context;
import android.os.AsyncTask;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.tommy.procrastinationtimer.models.Storage;
import com.tommy.procrastinationtimer.models.Task;
import com.tommy.procrastinationtimer.services.TaskRepositoryService;

import java.util.List;

public class MainActivityViewModel extends ViewModel {

    private MutableLiveData<List<Task>> taskList;
    private TaskRepositoryService repositoryService;
    private MutableLiveData<Boolean> isUpdated = new MutableLiveData<>();

    public void init(Context context) {
        if (taskList != null) {
            return;
        }
        repositoryService = TaskRepositoryService.getInstance(context);
        taskList = new MutableLiveData<>();
        taskList.setValue(repositoryService.getTasks());
    }

    public LiveData<List<Task>> getTaskList() {
        return taskList;
    }

    public LiveData<Boolean> getIsUpdated() {
        return isUpdated;
    }

    /**
     * Pretend that we need to save a task to some server
     *
     * @param newTask is a task to add
     */
    public void addTask(final Task newTask) {
        new SaveToDataBase().execute(newTask);
    }

    public void changeSource(Storage newStorageType) {

    }

    private class SaveToDataBase extends AsyncTask<Task, Integer, Task> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            isUpdated.postValue(true);
        }

        @Override
        protected void onPostExecute(Task newTask) {
            super.onPostExecute(newTask);
            taskList.postValue(repositoryService.getTasks());
            isUpdated.postValue(false);
        }

        @Override
        protected Task doInBackground(Task... tasks) {
            try {
                Thread.sleep(2000);
                repositoryService.addTask(tasks[0]);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return tasks[0];
        }
    }
}
