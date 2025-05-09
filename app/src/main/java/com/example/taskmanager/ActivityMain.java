package com.example.taskmanager;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
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
    private TextView emptyTextView;
    private Button filterAll;
    private Button filterCompleted;
    private Button filterIncomplete;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        emptyTextView = findViewById(R.id.emptyTextView);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        floatingButton = findViewById(R.id.floatingButton);
        filterAll = findViewById(R.id.filterAll);
        filterCompleted = findViewById(R.id.filterCompleted);
        filterIncomplete = findViewById(R.id.filterIncomplete);

        this.taskList = new ArrayList<>();
        loadTask();
        updateUI();
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        floatingButton.setOnClickListener(v ->{
            showAddTaskDialog();
        });


        taskAdapter =new TaskAdapter(taskList, this);
        recyclerView.setAdapter(taskAdapter);

        filterAll.setOnClickListener( v-> {
            taskAdapter.updateList(taskList);
        });
        filterIncomplete.setOnClickListener(v->{
            ArrayList<Task> incompleteTask = new ArrayList<>();
            for(Task task : taskList){
                if(!task.isCompleted()){
                    incompleteTask.add(task);
                }
            }
            taskAdapter.updateList(incompleteTask);
        });
        filterCompleted.setOnClickListener(v->{
            ArrayList<Task> completedTask = new ArrayList<>();
            for(Task task: taskList){
                if(task.isCompleted()){
                    completedTask.add(task);
                }
            }
            taskAdapter.updateList(completedTask);
        });

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu,menu);

        return true;
    }@Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_add) {
            showAddTaskDialog();
            return true;
        }

        if (item.getItemId() == R.id.action_refresh){

        }
        return super.onOptionsItemSelected(item);
    }


    private void updateUI() {
        if (taskList.isEmpty()) {
            emptyTextView.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.GONE);
        } else {
            emptyTextView.setVisibility(View.GONE);
            recyclerView.setVisibility(View.VISIBLE);
        }
    }


    private void showAddTaskDialog(){
        View dialogView = LayoutInflater.from(this)
                .inflate(R.layout.add_task_layout,null);

        EditText titleInput =dialogView.findViewById(R.id.addTitle);
        EditText descriptionInput = dialogView.findViewById(R.id.addDescription);
        Spinner priorityInput = dialogView.findViewById(R.id.addPriority);

        DatePicker datePicker = dialogView.findViewById(R.id.addDueDate);



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
                    int day = datePicker.getDayOfMonth();
                    int month = datePicker.getMonth();
                    int year = datePicker.getYear();
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
                            priorityValue = 0;
                            break;
                    }

                    if(TextUtils.isEmpty(title) || TextUtils.isEmpty(description) ){
                        Toast.makeText(this, "Please fill all required fields", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    @SuppressLint({"NewApi", "LocalSuppress"})
                    Task taskItem = new Task(nextId++,title,description,priorityValue,LocalDate.of(year,month,day));
                    taskList.add(taskItem);
                    updateUI();
                    Toast.makeText(this, "Task Added", Toast.LENGTH_SHORT).show();
                    taskAdapter.notifyItemInserted(taskList.size()-1);
                    recyclerView.scrollToPosition(0);

                }).show();

    }
    @SuppressLint("NewApi")
    public void loadTask(){
        taskList.clear();
        taskList.add(new Task(nextId++,"Get hair done","I need to get my hair done",1, LocalDate.of(2020,01,01)));
    }
}
