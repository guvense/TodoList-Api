package com.project.project.Service;

import com.project.project.Enum.Status;
import com.project.project.Models.TodoList;
import com.project.project.Models.User;
import com.project.project.Repositories.TodoListRepository;
import com.project.project.Repositories.UserRepository;
import com.project.project.Response.MainResponse;
import com.project.project.Response.TodoListResponse;
import org.bson.types.ObjectId;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationOperation;
import org.springframework.data.mongodb.core.aggregation.LookupOperation;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;


import java.util.List;

@Service
public class TodoListService {

    private TodoListRepository todoListRepository;
    private UserRepository userRepository;

    @Autowired
    MongoTemplate mongoTemplate;
    @Autowired
    ModelMapper modelMapper;



    public TodoListService(TodoListRepository todoListRepository, UserRepository userRepository) {
        this.todoListRepository = todoListRepository;
        this.userRepository = userRepository;
    }

    public MainResponse<Object> addTodoUser(ObjectId userId, TodoList todoList){

        TodoList todo = mongoTemplate.findOne(
                Query.query(Criteria.where("name").is(todoList.getName())), TodoList.class);

        if (todo!=null)
            return new MainResponse<>("Invalid Todo Name");

        User userD1 = userRepository.findBy_id(userId);

        todoList.set_userId(userD1.get_id());

        todoListRepository.save(todoList);

        return listTodoUser(userId);
    }

    public MainResponse<Object> listTodoUser(ObjectId userId){

        Query query = new Query();
        query.addCriteria(Criteria.where("_userId").is(userId).andOperator(Criteria.where("status").
                        is(Status.ACTIVE)));

        query.with(new Sort(Sort.Direction.DESC, "createDate"));

        List<TodoList> todo = mongoTemplate.find(query,TodoList.class);

        java.lang.reflect.Type targetListType = new TypeToken<List<TodoListResponse>>() {}.getType();
        List<TodoListResponse> a = modelMapper.map(todo,targetListType);


        return new MainResponse<>(a,true);


    }

    public MainResponse<Object> getTodoList(ObjectId todoListId){


        LookupOperation lookupOperation = LookupOperation.newLookup().
                from("todoItem").
                localField("_id").
                foreignField("_todoListId").
                as("todoItemList");

        AggregationOperation match = Aggregation.match(Criteria.where("_id").is(todoListId));

        AggregationOperation unwind = Aggregation.unwind("todoItemList");

        AggregationOperation sort = Aggregation.sort(Sort.Direction.DESC, "todoItemList.creationDate");

        Aggregation aggregation = Aggregation.newAggregation(lookupOperation, match,    Aggregation.group("_id").first("name").as("name")
                        .first("_todoListId").as("_todoListId").

                                push("todoItemList").as("todoItemList"),unwind
               ,sort);

       List< TodoList> results = mongoTemplate.aggregate(aggregation, "todoList", TodoList.class).getMappedResults();


        java.lang.reflect.Type targetListType = new TypeToken<List<TodoListResponse>>() {}.getType();

        List<TodoListResponse> a = modelMapper.map(results,targetListType);

        return new MainResponse<>(a.get(0) ,true);

    }

    public MainResponse<Object> deleteTodoUser(ObjectId userId,ObjectId todoListId){

        TodoList todoList=todoListRepository.findBy_id(todoListId);
        if(!userId.toString().matches(todoList.get_userId().toString()))
            return new MainResponse<>("error");

        TodoList todoList1=todoListRepository.findBy_id(todoListId);
        todoList1.setStatus(Status.DELETED);
        todoListRepository.save(todoList1);


        return listTodoUser(userId);
    }


}
