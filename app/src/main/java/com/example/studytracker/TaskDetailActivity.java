package com.example.studytracker;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

public class TaskDetailActivity extends AppCompatActivity {

    private TaskAdapter taskAdapter;
    private TaskRepository taskRepository;
    private TaskEntity currentTask;
    private EditText editTextTitle;
    private EditText editTextDescription;
    private EditText editTextDeadline;
    private EditText editTextSubject;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_task_detail);
        editTextTitle = findViewById(R.id.editTextTitle);
        editTextDescription = findViewById(R.id.editTextDescription);
        editTextDeadline = findViewById(R.id.editTextDeadline);
        editTextSubject = findViewById(R.id.editTextSubject);
        Button btnBack = findViewById(R.id.btnBack);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        taskRepository = new TaskRepository(this);

        // Получите данные таска из Intent
        Intent intent = getIntent();
        int taskId = intent.getIntExtra("taskId", -1);

        // Получите данные таска из репозитория
        currentTask = taskRepository.getTaskById(taskId);
        if (currentTask != null) {
            editTextTitle.setText(currentTask.getTitle());
            editTextDescription.setText(currentTask.getDescription());
            editTextDeadline.setText(String.valueOf(currentTask.getDeadline()));
            editTextSubject.setText(currentTask.getSubject());
        }
        // Инициализируйте элементы интерфейса и отобразите данные таска

        Button btnDeleteTask = findViewById(R.id.btnDeleteTask);
        btnDeleteTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteTask();
            }
        });

        Button btnEditTask = findViewById(R.id.btnEditTask);
        btnEditTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editTask();
            }
        });
    }

    private void deleteTask() {
        // Вызовите метод для удаления таска из репозитория
        taskRepository.deleteTask(currentTask);

        // Закройте активность и вернитесь к предыдущей
        finish();
    }

    private void editTask() {
        String updatedTitle = editTextTitle.getText().toString();
        String updatedDescription = editTextDescription.getText().toString();

        // Получите deadline как long (это простой пример, вам нужно реализовать логику преобразования из текста в long)
        String updatedDeadline = editTextDeadline.getText().toString();

        String updatedSubject = editTextSubject.getText().toString();

        // Обновите данные таска
        currentTask.setTitle(updatedTitle);
        currentTask.setDescription(updatedDescription);
        currentTask.setDeadline(updatedDeadline);
        currentTask.setSubject(updatedSubject);

        // Вызовите метод для редактирования таска в репозитории
        taskRepository.updateTask(currentTask);

        // Закройте активность и вернитесь к предыдущей
        finish();
    }

}
