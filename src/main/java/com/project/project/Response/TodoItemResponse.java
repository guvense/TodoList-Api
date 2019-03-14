package com.project.project.Response;

import com.project.project.Enum.TodoItemStatus;

import java.time.LocalDate;
import java.util.Date;

public class TodoItemResponse {

    private String name;
    private String description;
    private Date deadline;
    private String _id;
    private TodoItemStatus todoItemStatus;
    private LocalDate creationDate;

    public LocalDate getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDate creationDate) {
        this.creationDate = creationDate;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getDeadline() {
        return deadline;
    }

    public void setDeadline(Date deadline) {
        this.deadline = deadline;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public TodoItemStatus getTodoItemStatus() {
        return todoItemStatus;
    }

    public void setTodoItemStatus(TodoItemStatus todoItemStatus) {
        this.todoItemStatus = todoItemStatus;
    }
}
