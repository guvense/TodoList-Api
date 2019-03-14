package com.project.project.Request;

import com.project.project.Enum.TodoItemStatus;
import org.bson.types.ObjectId;

import java.util.Date;

public class TodoItemRequest {

    private ObjectId _todoListId;

    private String name;

    private String description;

    private long deadline;

    private TodoItemStatus todoItemStatus;

    private ObjectId _dependencyTodoItemId;

}
