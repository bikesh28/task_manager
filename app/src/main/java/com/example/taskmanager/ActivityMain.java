package com.example.taskmanager;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.time.LocalDate;
import java.util.ArrayList;

public class ActivityMain extends AppCompatActivity {
    private RecyclerView recyclerView;
    private ArrayList<Task> taskList;
    private int nextId = 0;
    private TaskAdapter taskAdapter;
    private FloatingActionButton floatingButton;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        floatingButton = findViewById(R.id.floatingButton);

        this.taskList = new ArrayList<>();
        //loadTask();

        floatingButton.setOnClickListener(v ->{
            showAddTaskDialog();
        });

        taskAdapter =new TaskAdapter(taskList, this);
        recyclerView.setAdapter(taskAdapter);
    }

    private void showAddTaskDialog(){
        View dialogView = LayoutInflater.from(this)
                .inflate(R.layout.add_task_layout,null);

        EditText titleInput =dialogView.findViewById(R.id.addTitle);
        EditText descriptionInput = dialogView.findViewById(R.id.addDescription);
        Spinner priorityInput = dialogView.findViewById(R.id.addPriority);

        DatePicker datePicker = dialogView.findViewById(R.id.addDueDate);
        int day = datePicker.getDayOfMonth();
        int month = datePicker.getMonth();
        int year = datePicker.getYear();


        new AlertDialog.Builder(this)
                .setTitle("Add Task")
                .setView(dialogView)
                .setNegativeButton("Cancle",(dialog, which) -> {
                    Toast.makeText(this, "Cancelled", Toast.LENGTH_SHORT).show();

                })
                .setPositiveButton("Add",(dialog, which) -> {
                    String title = (titleInput.getText()).toString().trim();
                    String description = (descriptionInput.getText().toString());
                    String selectedPriority = priorityInput.getSelectedItem().toString();
                    int priorityValue;
                    switch (selectedPriority){
                        case "High":
                             priorityValue = 1;
                             break;

                        case "Medium":
                            priorityValue = 0;
                            break;

                        case "Low":
                            priorityValue = 1;
                            break;
                        default:
                            priorityValue = 1;
                            break;
                    }

                    if(TextUtils.isEmpty(title) || TextUtils.isEmpty(description) ){
                        Toast.makeText(this, "Please fill all required fields", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    @SuppressLint({"NewApi", "LocalSuppress"})
                    Task taskItem = new Task(nextId++,title,description,priorityValue,LocalDate.of(year,month,day));
                    taskList.add(taskItem);
                    Toast.makeText(this, "Task Added", Toast.LENGTH_SHORT).show();
                    taskAdapter.notifyItemInserted(0);
                    recyclerView.scrollToPosition(0);

                }).show();

    }
    @SuppressLint("NewApi")
    public void loadTask(){
        taskList.clear();
        taskList.add(new Task(1,"Get hair done","I need to get my hair done",1, LocalDate.of(2020,01,01)));
    }
}
