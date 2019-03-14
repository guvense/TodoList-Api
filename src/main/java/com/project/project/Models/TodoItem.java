package com.project.project.Models;


import com.project.project.Enum.TodoItemStatus;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.format.annotation.DateTimeFormat;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

@Document
public class TodoItem {

    @Id
    private ObjectId _id;

    private ObjectId _todoListId;

    private String name;

    private String description;

    @DateTimeFormat(pattern = "yyyy/MM/dd")
    private Date deadline;

    private TodoItemStatus todoItemStatus;

    private ObjectId _dependencyTodoItemId;

    @DateTimeFormat(pattern = "YYYY-mm-ddTHH:MM:ssZ")
    private Date creationDate;

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public TodoItem(ObjectId _id, ObjectId _todoListId, String name, String description, Date deadline, ObjectId _dependencyTodoItemId) {
        this._id = _id;
        this._todoListId = _todoListId;
        this.name = name;
        this.description = description;
        this.deadline = deadline;
        this.todoItemStatus = TodoItemStatus.INCOMPLETE;
        this._dependencyTodoItemId=_dependencyTodoItemId;
        this.creationDate= new Date();
    }

    public ObjectId get_id() {
        return _id;
    }

    public void set_id(ObjectId _id) {
        this._id = _id;
    }

    public ObjectId get_todoListId() {
        return _todoListId;
    }

    public void set_todoListId(ObjectId _todoListId) {
        this._todoListId = _todoListId;
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

    public TodoItemStatus getTodoItemStatus() {
        return todoItemStatus;
    }

    public void setTodoItemStatus(TodoItemStatus todoItemStatus) {
        this.todoItemStatus = todoItemStatus;
    }

    public ObjectId get_dependencyTodoItemId() {
        return _dependencyTodoItemId;
    }

    public void set_dependencyTodoItemId(ObjectId _dependencyTodoItemId) {
        this._dependencyTodoItemId = _dependencyTodoItemId;
    }
}
