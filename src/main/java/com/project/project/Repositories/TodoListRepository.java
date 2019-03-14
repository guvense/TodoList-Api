package com.project.project.Repositories;


import com.project.project.Enum.Status;
import com.project.project.Models.TodoList;
import org.bson.types.ObjectId;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;


public interface TodoListRepository extends MongoRepository<TodoList, ObjectId> {

    TodoList findBy_id(ObjectId _id);


}
