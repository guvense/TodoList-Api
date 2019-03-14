package com.project.project.Request;

import org.bson.types.ObjectId;

public class SetStatusRequest {
    private ObjectId todoId;

    public ObjectId getTodoId() {
        return todoId;
    }

    public void setTodoId(ObjectId todoId) {
        this.todoId = todoId;
    }
}
