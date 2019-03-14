package com.project.project.Models;

import com.project.project.Enum.Status;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;
import java.util.List;

@Document
public class TodoList {

    @Id
    private ObjectId _id;
    private ObjectId _userId;
    private String name;
    private Status status;
    private Date createDate;
    private List<TodoItem> todoItemList;

    public List<TodoItem> getTodoItemList() {
        return todoItemList;
    }

    public void setTodoItemList(List<TodoItem> todoItemList) {
        this.todoItemList = todoItemList;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public TodoList(ObjectId _id, ObjectId _userId, String name) {
        this._id = _id;
        this._userId = _userId;
        this.name = name;
        this.status=Status.ACTIVE;
        this.createDate=new Date();

    }

    public ObjectId get_id() {
        return _id;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public void set_id(ObjectId _id) {
        this._id = _id;
    }

    public ObjectId get_userId() {
        return _userId;
    }

    public void set_userId(ObjectId _userId) {
        this._userId = _userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


}
