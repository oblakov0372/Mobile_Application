package com.example.studytracker;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface TaskDao {
    @Query("SELECT * FROM tasks")
    LiveData<List<TaskEntity>> getAllTasks();

    @Query("SELECT * FROM tasks WHERE id = :taskId")
    LiveData<TaskEntity> getTaskById(int taskId);

    @Insert
    void insertTask(TaskEntity task);

    @Update
    void updateTask(TaskEntity task);

    @Delete
    void deleteTask(TaskEntity task);
}

