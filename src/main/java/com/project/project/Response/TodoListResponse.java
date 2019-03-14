package com.project.project.Response;

import java.util.Date;
import java.util.List;

public class TodoListResponse {
    public  String name;
    public String _id;
    public Date createDate;
    public List<TodoItemResponse> todoItemList;

    public List<TodoItemResponse> getTodoItemList() {
        return todoItemList;
    }

    public void setTodoItemList(List<TodoItemResponse> todoItemList) {
        this.todoItemList = todoItemList;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
