package com.project.project.Service;


import com.project.project.Models.User;
import com.project.project.Repositories.UserRepository;
import com.project.project.Request.UserRequest;
import com.project.project.Response.MainResponse;
import com.project.project.Response.TokenResponse;
import com.project.project.Response.UserResponse;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;



@Service
public class UserService {

    private UserRepository userRepository;
    private TokenService tokenService;

    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public UserService(UserRepository userRepository, TokenService tokenService) {
        this.userRepository = userRepository;
        this.tokenService = tokenService;
    }

    public MainResponse<Object> getUser(ObjectId objectId){
        User user=userRepository.findBy_id(objectId);
        UserResponse response=new UserResponse();
        response.setUserId(user.get_id().toString());
        response.setUsername(user.getUserName());
        return new MainResponse<>(response,true);
    }

    public MainResponse<Object> saveUser(UserRequest userRequest){


        if(userRepository.findByUserName(userRequest.getUsername())!=null){
            return new MainResponse<>("Invalid Username");
        }
        User user=new User(userRequest.getUsername(),bCryptPasswordEncoder.encode(userRequest.getPassword()));

        User savedUser= userRepository.save(user);
        return new MainResponse<>(new TokenResponse(tokenService.createToken(savedUser.get_id())),true);

    }

    public MainResponse<Object> signInUser(UserRequest userRequest){

        User user=userRepository.findByUserName(userRequest.getUsername());
        if(user!=null && bCryptPasswordEncoder.matches(userRequest.getPassword(),user.getPasswordHash())){
            return new MainResponse<>(new TokenResponse(tokenService.createToken(user.get_id())),true);
        }
        else
            return new MainResponse<>("Authentication Failed");

    }


}
