package com.project.project.Service;

import com.project.project.Enum.FilterOptions;
import com.project.project.Enum.OrderBy;
import com.project.project.Enum.Status;
import com.project.project.Enum.TodoItemStatus;
import com.project.project.Models.TodoItem;
import com.project.project.Models.TodoList;
import com.project.project.Models.User;
import com.project.project.Repositories.TodoItemRepository;
import com.project.project.Repositories.TodoListRepository;
import com.project.project.Repositories.UserRepository;
import com.project.project.Response.MainResponse;
import com.project.project.Response.TodoItemResponse;
import jdk.nashorn.internal.objects.annotations.Where;
import org.bson.Document;
import org.bson.types.ObjectId;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationOperation;
import org.springframework.data.mongodb.core.aggregation.AggregationOperationContext;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class TodoItemService {

    private TodoListRepository todoListRepository;
    private UserRepository userRepository;
    private TodoItemRepository todoItemRepository;
    private TodoListService todoListService;

    @Autowired
    MongoTemplate mongoTemplate;
    @Autowired
    ModelMapper modelMapper;

    public TodoItemService(TodoListRepository todoListRepository, UserRepository userRepository, TodoItemRepository todoItemRepository, TodoListService todoListService) {
        this.todoListRepository = todoListRepository;
        this.userRepository = userRepository;
        this.todoItemRepository = todoItemRepository;
        this.todoListService=todoListService;
    }

    public MainResponse<Object> addTodoItem(ObjectId userId, TodoItem todoItem) {

        TodoList todo = mongoTemplate.findOne(
                Query.query(Criteria.where("_id").is(todoItem.get_todoListId())), TodoList.class);

        if (!userId.equals(todo.get_userId()))
            return new MainResponse<>("Invalid Todo Name");
        TodoItem todoItem1= todoItemRepository.save(todoItem);
        return todoListService.getTodoList(todoItem1.get_todoListId());

    }

    public MainResponse<Object> setTodoItemCompleted(ObjectId userId, ObjectId todoItemId) {

       TodoItem todo=mongoTemplate.findOne(Query.query(Criteria.where("_id").is(todoItemId)),TodoItem.class);

       if(todo.get_dependencyTodoItemId()!=null){
           TodoItem depend=mongoTemplate.findOne( Query.query(Criteria.where("_id").is(todo.get_dependencyTodoItemId())),TodoItem.class);

           if(depend.getTodoItemStatus()==TodoItemStatus.INCOMPLETE)
               return new MainResponse<>("Dependency Error");
       }

       todo.setTodoItemStatus(TodoItemStatus.COMPLETED);
       TodoItemResponse result = modelMapper.map(todoItemRepository.save(todo),TodoItemResponse.class);
        return new MainResponse<>(result ,true);


    }

    public MainResponse<Object> findTodoItem(ObjectId todoListId, FilterOptions filterOptions) {

        Query a=new Query();

        if(filterOptions==FilterOptions.COMPLETE){

            a= Query.query(Criteria.where("_todoListId").is(todoListId).andOperator(Criteria.where("todoItemStatus").is(TodoItemStatus.COMPLETED)));

        }
        else if(filterOptions==FilterOptions.INCOMPLETE){

            a= Query.query(Criteria.where("_todoListId").is(todoListId).andOperator(Criteria.where("todoItemStatus").is(TodoItemStatus.INCOMPLETE)));
        }
        else if(filterOptions==FilterOptions.EXPIRED){

            a= Query.query(Criteria.where("_todoListId").is(todoListId).andOperator(Criteria.where("deadline").lt(new Date())));
        }

        List<TodoItem> todoItems=mongoTemplate.find(a,TodoItem.class);
        java.lang.reflect.Type targetListType = new TypeToken<List<TodoItemResponse>>() {}.getType();

        List<TodoItemResponse> results = modelMapper.map(todoItems,targetListType);
        return new MainResponse<>(results ,true);

    }

    public MainResponse<Object> findTodoItemByName(ObjectId todoListId, String name) {

       List<TodoItem> todoItems=mongoTemplate.find( Query.query(Criteria.where("_todoListId").is(todoListId).andOperator(Criteria.where("name").
               is(name))),TodoItem.class);
        java.lang.reflect.Type targetListType = new TypeToken<List<TodoItemResponse>>() {}.getType();

        List<TodoItemResponse> results = modelMapper.map(todoItems,targetListType);
        return new MainResponse<>(results ,true);
    }

    public MainResponse<Object> orderByTodoItem(ObjectId todoListId, OrderBy orderby) {

        String orderByField="";
        Sort.Direction direction=Sort.Direction.DESC;

        switch (orderby){
            case NAME:
                orderByField="name";
                direction=Sort.Direction.ASC;
                break;
            case STATUS:
                orderByField="todoItemStatus";
                break;
            case DEADLINE:
                orderByField="deadline";
                break;
            case CREATIONDATE:
                orderByField="creationDate";
                break;
                default:
                    return new MainResponse<>("Invalid Enum Type");

        }

        List< TodoItem> todoItems =  todoItemRepository.findAll(Sort.by(direction, orderByField));

        java.lang.reflect.Type targetListType = new TypeToken<List<TodoItemResponse>>() {}.getType();

        List<TodoItemResponse> results = modelMapper.map(todoItems,targetListType);

        return new MainResponse<>(results ,true);
    }
}
