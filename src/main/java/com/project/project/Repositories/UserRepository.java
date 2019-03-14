package com.project.project.Repositories;

import com.project.project.Models.User;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository<User,ObjectId> {

    User findBy_id(ObjectId _id);
    User findByUserName(String username);



}
