package com.example.studytracker;

import android.content.Context;

import androidx.lifecycle.LiveData;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class TaskRepository {
    private TaskDao taskDao;
    private LiveData<List<TaskEntity>> allTasks;
    private ExecutorService executorService;

    public TaskRepository(Context context) {
        AppDatabase database = MyApplication.getDatabase();
        taskDao = database.taskDao();
        allTasks = (LiveData<List<TaskEntity>>) taskDao.getAllTasks();
        executorService = Executors.newSingleThreadExecutor();
    }

    public LiveData<List<TaskEntity>> getAllTasks() {
        return allTasks;
    }

    public LiveData<TaskEntity> getTaskById(int taskId) {
        return taskDao.getTaskById(taskId);
    }

    public void insertTask(TaskEntity task) {
        executorService.execute(() -> taskDao.insertTask(task));
    }

    public void updateTask(TaskEntity task) {
        executorService.execute(() -> taskDao.updateTask(task));
    }

    public void deleteTask(TaskEntity task) {
        executorService.execute(() -> taskDao.deleteTask(task));
    }
}
