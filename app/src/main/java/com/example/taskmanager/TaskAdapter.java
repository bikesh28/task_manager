package com.example.taskmanager;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.TaskViewHolder> {
    private ArrayList<Task> taskList;
    private Context context;
    public TaskAdapter(ArrayList<Task> taskList, Context context){
        this.taskList = taskList;
        this.context = context;
    }

    @NonNull
    @Override
    public TaskViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.task_view,parent,false);
        return new TaskViewHolder(view);
    }



    @Override
    public int getItemCount() {
        return this.taskList.size();
    }
    @Override
    public void onBindViewHolder(@NonNull TaskViewHolder holder, int position) {
        Task taskItem =this.taskList.get(position);



        holder.titleTextView.setText(taskItem.getTitle());
        holder.priorityTextView.setText(taskItem.getPriorityString());
        holder.decsriptionTextView.setText(taskItem.getDescription());
        holder.dueDateTextVIew.setText(String.valueOf(taskItem.getDueDate()));
        holder.completeTaskButton.setChecked(taskItem.isCompleted());

        int priorityColor = getPriorityColor(taskItem.getPriority());
        holder.itemView.setBackgroundColor(priorityColor);

        holder.completeTaskButton.setOnCheckedChangeListener((buttonView, isChecked)  ->{
            taskItem.setCompleted(true);
            holder.itemView.setBackgroundColor(context.getResources().getColor(R.color.completed_task));
            holder.completeTaskButton.setClickable(false);
            Toast.makeText(context,"Task Completed", Toast.LENGTH_SHORT).show();
        });




    }private int getPriorityColor(int priority) {
        switch (priority) {
            case -1: // Low Priority
                return context.getResources().getColor(R.color.low_priority);
            case 0: // Medium Priority
                return context.getResources().getColor(R.color.medium_priority);
            case 1: // High Priority
                return context.getResources().getColor(R.color.high_priority);
            default:
                return context.getResources().getColor(R.color.medium_priority);
        }
    }

    static class TaskViewHolder extends RecyclerView.ViewHolder{
        public TextView titleTextView ;
        public TextView decsriptionTextView;
        public TextView priorityTextView;
        public TextView dueDateTextVIew;
        public CheckBox completeTaskButton;

        public TaskViewHolder(View itemView){
            super(itemView);
            this.decsriptionTextView = itemView.findViewById(R.id.descriptionTextView);
            this.dueDateTextVIew = itemView.findViewById(R.id.dueDateTextView);
            this.priorityTextView = itemView.findViewById(R.id.priorityTextView);
            this.titleTextView = itemView.findViewById(R.id.titleTextView);
            this.completeTaskButton = itemView.findViewById(R.id.taskCompleteCheckBox);

            itemView.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v) {
                    completeTaskButton.setChecked(true);

                }
            });

        }

    }
}
