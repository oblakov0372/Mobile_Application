package com.example.studytracker;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.TaskViewHolder> {

    private List<TaskEntity> taskList;
    private OnItemClickListener onItemClickListener;
    private OnItemLongClickListener onItemLongClickListener;

    public TaskAdapter(List<TaskEntity> taskList) {
        this.taskList = taskList;
    }

    @NonNull
    @Override
    public TaskViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_task, parent, false);
        return new TaskViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TaskViewHolder holder, int position) {
        TaskEntity task = taskList.get(position);
        holder.bind(task);
    }

    @Override
    public int getItemCount() {
        return taskList.size();
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.onItemClickListener = listener;
    }

    public void setOnItemLongClickListener(OnItemLongClickListener listener) {
        this.onItemLongClickListener = listener;
    }

    public interface OnItemClickListener {
        void onItemClick(TaskEntity task);
    }

    public interface OnItemLongClickListener {
        void onItemLongClick(TaskEntity task);
    }

    public void setTaskList(List<TaskEntity> newTaskList) {
        taskList.clear();
        taskList.addAll(newTaskList);
        notifyDataSetChanged();
    }

    class TaskViewHolder extends RecyclerView.ViewHolder {
        private TextView textViewTitle;
        private TextView textViewDescription;
        private TextView textViewDeadline;
        private TextView textViewSubject;

        public TaskViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewTitle = itemView.findViewById(R.id.textViewTitle);
            textViewDescription = itemView.findViewById(R.id.textViewDescription);
            textViewDeadline = itemView.findViewById(R.id.textViewDeadline);
            textViewSubject = itemView.findViewById(R.id.textViewSubject);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onItemClickListener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            onItemClickListener.onItemClick(taskList.get(position));
                        }
                    }
                }
            });
        }

        public void bind(TaskEntity task) {
            textViewTitle.setText(task.getTitle());
            textViewDescription.setText("Description: " + task.getDescription());
            textViewDeadline.setText("Deadline: " + task.getDeadline());
            textViewSubject.setText("Subject: " + task.getSubject());

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Откройте TaskDetailActivity и передайте идентификатор таска
                    Intent intent = new Intent(itemView.getContext(), TaskDetailActivity.class);
                    intent.putExtra("taskId", task.getId());
                    itemView.getContext().startActivity(intent);
                }
            });
        }


        private String formatDeadline(long deadline) {
            // Добавьте логику форматирования даты и времени по вашему желанию
            // Например, использование SimpleDateFormat или других методов форматирования
            return String.valueOf(deadline);
        }
    }
}
