package com.example.taskmanager;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.time.LocalDate;
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

        int priorityColor = getPriorityColor(taskItem.getPriority(),context);
        holder.itemView.setBackgroundColor(priorityColor);

        holder.completeTaskButton.setOnCheckedChangeListener((buttonView, isChecked)  ->{
            taskItem.setCompleted(true);
            holder.itemView.setBackgroundColor(context.getResources().getColor(R.color.completed_task));
            holder.completeTaskButton.setClickable(false);
            Toast.makeText(context,"Task Completed", Toast.LENGTH_SHORT).show();
        });
        holder.itemView.setOnLongClickListener(v -> {
            // Open the edit dialog
            openEditDialog(taskItem, position);
            return true;
        });
    }
    private void openEditDialog(Task taskItem,int position ){
        View editTaskView = LayoutInflater.from(context).inflate(R.layout.edit_task,null);
        EditText editTitle = editTaskView.findViewById(R.id.editTitle);
        EditText editDescription = editTaskView.findViewById(R.id.editDescription);
        Spinner editPriority =  editTaskView.findViewById(R.id.editPriority);
        DatePicker editDueDate = editTaskView.findViewById(R.id.editDueDate);

        editTitle.setText(taskItem.getTitle());
        editDescription.setText(taskItem.getDescription());
        editPriority.setSelection(taskItem.getPriority());

        LocalDate date = taskItem.getDueDate();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            int year = date.getYear();
            int month = date.getMonthValue() -1 ;
            int day = date.getDayOfMonth();
            editDueDate.updateDate(year,month,day);
        }


        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setView(editTaskView);
        builder.setTitle("Edit Task");
        builder.setPositiveButton("Submit",(dialog, which) -> {
            taskItem.setTitle(editTitle.getText().toString());
            taskItem.setDescription(editDescription.getText().toString());
            String selectedPriority = editPriority.getSelectedItem().toString();

            int priorityValue;
            switch (selectedPriority){
                case "High":
                    priorityValue = 1;
                    break;

                case "Medium":
                    priorityValue = 0;
                    break;

                case "Low":
                    priorityValue = -1;
                    break;
                default:
                    priorityValue = taskItem.getPriority();
                    break;
            }
            taskItem.setPriority(priorityValue);


            notifyItemChanged(position);
        });
        builder.setNegativeButton("Cancel", (dialog, which) -> dialog.dismiss());

        builder.show();
    }
    private int getPriorityColor(int priority, Context context) {
        switch (priority) {
            case -1: // Low Priority
                return ContextCompat.getColor(context,R.color.low_priority);
            case 0: // Medium Priority
                return ContextCompat.getColor(context,R.color.medium_priority);
            case 1: // High Priority
                return ContextCompat.getColor(context,R.color.high_priority);
            default:
                return ContextCompat.getColor(context,R.color.medium_priority);
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
            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {

                    return true;
                }
            });

        }

    }
}
