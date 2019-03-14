package com.project.project.Controller;

import com.project.project.Models.TodoList;
import com.project.project.Response.MainResponse;
import com.project.project.Service.TodoListService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;


@RestController
@RequestMapping("/todo-list")
public class TodoController {

    private TodoListService todoListService;

    @Autowired
    public TodoController(TodoListService todoListService) {
        this.todoListService = todoListService;
    }

    @PostMapping("/add-todo-list")
    public MainResponse<Object> addTodoList(@ModelAttribute TodoList todoList, HttpServletRequest request) {
        ObjectId userId = (ObjectId) request.getAttribute("userId");
        return todoListService.addTodoUser(userId,todoList);
    }

    @GetMapping("/list-todo-list")
    public MainResponse<Object> listTodoList(HttpServletRequest request) {
        ObjectId userId = (ObjectId) request.getAttribute("userId");
        return todoListService.listTodoUser(userId);
    }

    @GetMapping("/get-todo-list/{todoListId}")
    public MainResponse<Object> getTodoList( @PathVariable(value="todoListId") ObjectId id, HttpServletRequest request) {

        return todoListService.getTodoList(id);
    }


    @DeleteMapping("/delete-todo-list/{todoListId}")
    public MainResponse<Object> deleteTodoList(@PathVariable(value="todoListId") ObjectId id, HttpServletRequest request) {
        ObjectId userId = (ObjectId) request.getAttribute("userId");

        return todoListService.deleteTodoUser(userId, id);
    }
}
