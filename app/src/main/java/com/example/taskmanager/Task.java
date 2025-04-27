package com.example.taskmanager;

import java.time.LocalDate;
import java.util.Date;

public class Task {
    private  int id;
    private String title;
    private String description;
    private int priority;
    private LocalDate dueDate;
    private boolean isCompleted;

    public Task(int id, String title, String description, int priority, LocalDate dueDate) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.priority = priority;
        this.dueDate = dueDate;
        this.isCompleted = true;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public int getPriority() {
        return priority;
    }

    public LocalDate getDueDate() {
        return (dueDate);
    }

    public boolean isCompleted() {
        return isCompleted;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }

    public void setCompleted(boolean completed) {
        isCompleted = completed;
    }
    public String getPriorityString(){
        String priority = "";
        if(this.getPriority()== 1) {
            priority=  "High";

        } else if (this.getPriority()== 0) {
            priority =   "Medium";

        } else if (this.getPriority()== -1) {
            priority =  "Low";
        }
        return priority;
    }

}
