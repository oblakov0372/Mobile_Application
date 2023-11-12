package com.example.studytracker;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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
        taskAdapter = new TaskAdapter(taskRepository.getAllTasks());
        recyclerViewTasks.setAdapter(taskAdapter);

        // Добавьте обработчик для нажатия на элемент списка (если нужно)
        taskAdapter.setOnItemClickListener(new TaskAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(TaskEntity task) {
                // Обработка нажатия на элемент списка
                Toast.makeText(requireContext(), "Clicked: " + task.getTitle(), Toast.LENGTH_SHORT).show();
            }
        });

        // Добавьте обработчик для долгого нажатия на элемент списка (если нужно)
        taskAdapter.setOnItemLongClickListener(new TaskAdapter.OnItemLongClickListener() {
            @Override
            public void onItemLongClick(TaskEntity task) {
                // Обработка долгого нажатия на элемент списка
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

        // Возвращаем созданный вид
        return view;
    }
    private void showAddTaskDialog() {
        // Инициализируйте макет для диалогового окна
        LayoutInflater inflater = requireActivity().getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_add_task, null);

        // Получите поля ввода из диалогового окна
        EditText editTextTitle = dialogView.findViewById(R.id.editTextTitle);
        EditText editTextDescription = dialogView.findViewById(R.id.editTextDescription);
        EditText editTextDeadline = dialogView.findViewById(R.id.editTextDeadline);
        EditText editTextSubject = dialogView.findViewById(R.id.editTextSubject);

        // Добавьте обработчик для кнопки "Add Task"
        Button btnAdd = dialogView.findViewById(R.id.btnAdd);
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Получите данные из полей ввода
                String title = editTextTitle.getText().toString().trim();
                String description = editTextDescription.getText().toString().trim();
                String deadline = editTextDeadline.getText().toString().trim();
                String subject = editTextSubject.getText().toString().trim();

                // Создайте новый таск и добавьте его в репозиторий
                TaskEntity newTask = new TaskEntity();
                newTask.setTitle(title);
                newTask.setDescription(description);
                newTask.setDeadline(deadline);
                newTask.setSubject(subject);
                // Добавьте другие данные, если нужно

                taskRepository.insertTask(newTask);
                // Обновите список задач перед уведомлением адаптера
                taskAdapter.setTaskList(taskRepository.getAllTasks());
                // Закройте диалоговое окно
                alertDialog.dismiss();
            }
        });

        // Создайте и отобразите диалоговое окно
        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        builder.setView(dialogView);
        alertDialog = builder.create();
        alertDialog.show();
    }
}