package com.example.studytracker;

import android.content.Context;
import android.os.AsyncTask;

import java.util.List;

public class TaskRepository {
    private TaskDao taskDao;

    public TaskRepository(Context context) {
        AppDatabase database = MyApplication.getDatabase();
        taskDao = database.taskDao();
    }

    public List<TaskEntity> getAllTasks() {
        return taskDao.getAllTasks();
    }

    public TaskEntity getTaskById(int taskId) {
        return taskDao.getTaskById(taskId);
    }

    public void insertTask(TaskEntity task) {
        new InsertTaskAsyncTask(taskDao).execute(task);
    }

    public void updateTask(TaskEntity task) {
        new UpdateTaskAsyncTask(taskDao).execute(task);
    }

    public void deleteTask(TaskEntity task) {
        new DeleteTaskAsyncTask(taskDao).execute(task);
    }

    // Асинхронные задачи для выполнения операций в фоновом потоке
    private static class InsertTaskAsyncTask extends AsyncTask<TaskEntity, Void, Void> {
        private TaskDao taskDao;

        InsertTaskAsyncTask(TaskDao taskDao) {
            this.taskDao = taskDao;
        }

        @Override
        protected Void doInBackground(TaskEntity... tasks) {
            taskDao.insertTask(tasks[0]);
            return null;
        }
    }

    private static class UpdateTaskAsyncTask extends AsyncTask<TaskEntity, Void, Void> {
        private TaskDao taskDao;

        UpdateTaskAsyncTask(TaskDao taskDao) {
            this.taskDao = taskDao;
        }

        @Override
        protected Void doInBackground(TaskEntity... tasks) {
            taskDao.updateTask(tasks[0]);
            return null;
        }
    }

    private static class DeleteTaskAsyncTask extends AsyncTask<TaskEntity, Void, Void> {
        private TaskDao taskDao;

        DeleteTaskAsyncTask(TaskDao taskDao) {
            this.taskDao = taskDao;
        }

        @Override
        protected Void doInBackground(TaskEntity... tasks) {
            taskDao.deleteTask(tasks[0]);
            return null;
        }
    }

}
