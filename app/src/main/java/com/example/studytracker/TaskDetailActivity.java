package com.example.studytracker;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;

public class TaskDetailActivity extends AppCompatActivity {

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

        Intent intent = getIntent();
        int taskId = intent.getIntExtra("taskId", -1);

        if (taskId != -1) {
            taskRepository.getTaskById(taskId).observe(this, new Observer<TaskEntity>() {
                @Override
                public void onChanged(TaskEntity task) {
                    if (task != null) {
                        currentTask = task;
                        editTextTitle.setText(task.getTitle());
                        editTextDescription.setText(task.getDescription());
                        editTextDeadline.setText(String.valueOf(task.getDeadline())); // Убедитесь, что формат верный
                        editTextSubject.setText(task.getSubject());
                    }
                }
            });
        }

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
        if (currentTask != null) {
            taskRepository.deleteTask(currentTask);
        }
        finish();
    }

    private void editTask() {
        if (validateInputs()) {
            String updatedTitle = editTextTitle.getText().toString().trim();
            String updatedDescription = editTextDescription.getText().toString().trim();
            String updatedDeadline = editTextDeadline.getText().toString().trim();
            String updatedSubject = editTextSubject.getText().toString().trim();

            currentTask.setTitle(updatedTitle);
            currentTask.setDescription(updatedDescription);
            currentTask.setDeadline(updatedDeadline); // Убедитесь, что формат верный
            currentTask.setSubject(updatedSubject);

            taskRepository.updateTask(currentTask);
            finish();
        }
    }

    private boolean validateInputs() {
        boolean isValid = true;

        if (editTextTitle.getText().toString().trim().isEmpty()) {
            editTextTitle.setError("This field is required");
            isValid = false;
        }

        if (editTextDescription.getText().toString().trim().isEmpty()) {
            editTextDescription.setError("This field is required");
            isValid = false;
        }

        if (editTextDeadline.getText().toString().trim().isEmpty()) {
            editTextDeadline.setError("This field is required");
            isValid = false;
        } else if (!editTextDeadline.getText().toString().trim().matches("\\d{4}-\\d{2}-\\d{2}")) {
            editTextDeadline.setError("The date must be in the format yyyy-mm-dd");
            isValid = false;
        }

        if (editTextSubject.getText().toString().trim().isEmpty()) {
            editTextSubject.setError("This field is required");
            isValid = false;
        }

        return isValid;
    }
}
