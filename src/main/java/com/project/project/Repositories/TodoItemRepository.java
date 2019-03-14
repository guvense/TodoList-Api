package com.project.project.Repositories;

import com.project.project.Models.TodoItem;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface TodoItemRepository extends MongoRepository<TodoItem, ObjectId> {

    TodoItem findBy_id(ObjectId _id);
}
