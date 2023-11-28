package com.example.studytracker;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class TasksFragment extends Fragment {

    private TaskRepository taskRepository;
    private TaskAdapter taskAdapter;
    private AlertDialog alertDialog;

    public TasksFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tasks, container, false);

        // Инициализация репозитория и RecyclerView
        taskRepository = new TaskRepository(requireContext());

        RecyclerView recyclerViewTasks = view.findViewById(R.id.recyclerViewTasks);
        recyclerViewTasks.setLayoutManager(new LinearLayoutManager(requireContext()));

        // Инициализация адаптера и установка его в RecyclerView
        taskAdapter = new TaskAdapter(new ArrayList<>());
        recyclerViewTasks.setAdapter(taskAdapter);

        // Наблюдение за данными и обновление адаптера при изменениях
        taskRepository.getAllTasks().observe(getViewLifecycleOwner(), new Observer<List<TaskEntity>>() {
            @Override
            public void onChanged(List<TaskEntity> taskEntities) {
                taskAdapter.setTaskList(taskEntities);
            }
        });

        // Обработчики событий для элементов списка
        taskAdapter.setOnItemClickListener(new TaskAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(TaskEntity task) {
                // Переход к TaskDetailActivity для редактирования задачи
                Intent intent = new Intent(getActivity(), TaskDetailActivity.class);
                intent.putExtra("taskId", task.getId());
                startActivity(intent);
            }
        });

        taskAdapter.setOnItemLongClickListener(new TaskAdapter.OnItemLongClickListener() {
            @Override
            public void onItemLongClick(TaskEntity task) {
                Toast.makeText(requireContext(), "Long Clicked: " + task.getTitle(), Toast.LENGTH_SHORT).show();
            }
        });

        Button btnAddTask = view.findViewById(R.id.btnAddTask);
        btnAddTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAddTaskDialog();
            }
        });

        return view;
    }

    private void showAddTaskDialog() {
        // Отображение диалогового окна для добавления задачи
        LayoutInflater inflater = requireActivity().getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_add_task, null);

        EditText editTextTitle = dialogView.findViewById(R.id.editTextTitle);
        EditText editTextDescription = dialogView.findViewById(R.id.editTextDescription);
        EditText editTextDeadline = dialogView.findViewById(R.id.editTextDeadline);
        EditText editTextSubject = dialogView.findViewById(R.id.editTextSubject);

        Button btnAdd = dialogView.findViewById(R.id.btnAdd);
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validateInputs(editTextTitle, editTextDescription, editTextDeadline, editTextSubject)) {
                    String title = editTextTitle.getText().toString().trim();
                    String description = editTextDescription.getText().toString().trim();
                    String deadline = editTextDeadline.getText().toString().trim();
                    String subject = editTextSubject.getText().toString().trim();

                    TaskEntity newTask = new TaskEntity();
                    newTask.setTitle(title);
                    newTask.setDescription(description);
                    newTask.setDeadline(deadline);
                    newTask.setSubject(subject);

                    taskRepository.insertTask(newTask);
                    alertDialog.dismiss();
                }
            }
        });

        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        builder.setView(dialogView);
        alertDialog = builder.create();
        alertDialog.show();
    }

    private boolean validateInputs(EditText title, EditText description, EditText deadline, EditText subject) {
        boolean isValid = true;

        // Проверка поля "Заголовок"
        if (title.getText().toString().trim().isEmpty()) {
            title.setError("This field is required");
            isValid = false;
        }

        // Проверка поля "Описание"
        if (description.getText().toString().trim().isEmpty()) {
            description.setError("This field is required");
            isValid = false;
        }

        // Проверка поля "Крайний срок"
        if (deadline.getText().toString().trim().isEmpty()) {
            deadline.setError("This field is required");
            isValid = false;
        } else if (!deadline.getText().toString().trim().matches("\\d{4}-\\d{2}-\\d{2}")) {
            deadline.setError("The date must be in the format yyyy-mm-dd");
            isValid = false;
        }

        // Проверка поля "Предмет"
        if (subject.getText().toString().trim().isEmpty()) {
            subject.setError("This field is required");
            isValid = false;
        }

        return isValid;
    }
}
