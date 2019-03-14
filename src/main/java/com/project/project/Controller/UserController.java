package com.project.project.Controller;


import com.project.project.Models.User;
import com.project.project.Request.UserRequest;
import com.project.project.Response.MainResponse;
import com.project.project.Service.TokenService;
import com.project.project.Service.UserService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.oauth2.resource.OAuth2ResourceServerProperties;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/user")
public class UserController {


    private UserService userService;

    @Autowired
    TokenService tokenService;

    @Autowired
    UserController(UserService userService){
        this.userService=userService;
    }

    @PostMapping("/sign-in")
    public MainResponse<Object> signInUser(@ModelAttribute UserRequest user) {
        return userService.signInUser(user);
    }

    @PostMapping("/sign-up")
    public MainResponse<Object> signUpUser(@ModelAttribute UserRequest user,HttpServletRequest request) {
        System.out.println(request.getRequestURI());
        return userService.saveUser(user);
    }

    @GetMapping("/get")
    public MainResponse<Object> getUser(HttpServletRequest request) {
        ObjectId userId = (ObjectId) request.getAttribute("userId");

        if(userId==null){
           String token=request.getHeader("Authorization");
           userId = new ObjectId(tokenService.getUserIdFromToken(token));
        }
        return userService.getUser(userId);
    }
}
