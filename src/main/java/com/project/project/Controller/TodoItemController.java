package com.project.project.Controller;

import com.project.project.Enum.FilterOptions;
import com.project.project.Enum.OrderBy;
import com.project.project.Models.TodoItem;
import com.project.project.Request.SetStatusRequest;
import com.project.project.Response.MainResponse;
import com.project.project.Service.TodoItemService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;


@RestController
@RequestMapping("/todo-item")
public class TodoItemController {
    private TodoItemService todoItemService;

    @Autowired
    public TodoItemController(TodoItemService todoItemService) {
        this.todoItemService = todoItemService;
    }


    @PostMapping("/add-todo-item")
    public MainResponse<Object> addTodoItem(@ModelAttribute TodoItem todoItem, HttpServletRequest request) {
        ObjectId userId = (ObjectId) request.getAttribute("userId");
        return todoItemService.addTodoItem(userId,todoItem);
    }
    @PostMapping("/set-todo-item-completed")
    public MainResponse<Object> setTodoItemCompleted( HttpServletRequest request, SetStatusRequest todoId ) {
        ObjectId userId = (ObjectId) request.getAttribute("userId");

        return todoItemService.setTodoItemCompleted(userId,todoId.getTodoId());
    }
    @GetMapping("/get-todo-item/{todoListId}/{filterOption}")
    public MainResponse<Object> getTodoItem(@PathVariable(value="todoListId") ObjectId id,@PathVariable(value="filterOption")  FilterOptions filter, HttpServletRequest request) {

        return todoItemService.findTodoItem(id,filter);
    }

    @GetMapping("/get-todo-item-name/{todoListId}/{name}")
    public MainResponse<Object> getTodoItembyName(@PathVariable(value="todoListId") ObjectId id,@PathVariable(value="name")  String name, HttpServletRequest request) {

        return todoItemService.findTodoItemByName(id,name);
    }

    @GetMapping("/get-todo-item-order/{todoListId}/{OrderBy}")
    public MainResponse<Object> getTodoItemOrder(@PathVariable(value="todoListId") ObjectId id, @PathVariable(value="OrderBy") OrderBy orderby, HttpServletRequest request) {

        return todoItemService.orderByTodoItem(id,orderby);
    }


}
