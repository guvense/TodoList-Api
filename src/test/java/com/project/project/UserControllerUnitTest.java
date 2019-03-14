package com.project.project;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.project.Controller.UserController;
import com.project.project.Models.User;
import com.project.project.Request.UserRequest;
import com.project.project.Response.MainResponse;
import com.project.project.Response.TokenResponse;
import com.project.project.Service.UserService;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.context.WebApplicationContext;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;


@SpringBootTest
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@RunWith(SpringRunner.class)
public class UserControllerUnitTest {
    MockMvc mockMvc;

    @Autowired
    protected WebApplicationContext wac;

    @Autowired
    UserController userController;

    @Autowired
    UserService userService;




    private String token;


    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }}


    @Test
    public void ATestUserControllerSignUp() throws Exception {
        this.mockMvc = standaloneSetup(this.userController).build();


        MvcResult mvcResult= this.mockMvc.perform(post("/user/sign-up").contentType(MediaType.APPLICATION_FORM_URLENCODED).
                param("username","adam").param("password","12345")).andDo(print()).
                andExpect(status().isOk()).
                andExpect(jsonPath("$.data").isNotEmpty()).andReturn();

       String response = mvcResult.getResponse().getContentAsString();

        JSONObject jsonObj = new JSONObject(response);
        token =  jsonObj.getJSONObject("data").get("token").toString();

    }
    @Test
    public void BTestUserControllerSignIn() throws Exception {
        this.mockMvc = standaloneSetup(this.userController).build();


        MvcResult mvcResult= this.mockMvc.perform(post("/user/sign-in").contentType(MediaType.APPLICATION_FORM_URLENCODED).
                param("username","adam").param("password","12345")).andDo(print()).
                andExpect(status().isOk()).
                andExpect(jsonPath("$.data").isNotEmpty()).andReturn();

        String response = mvcResult.getResponse().getContentAsString();

        JSONObject jsonObj = new JSONObject(response);
        token =  jsonObj.getJSONObject("data").get("token").toString();

    }

    @Test
    public void CTestUserControllerGet() throws Exception {
        this.mockMvc = standaloneSetup(this.userController).build();

        MvcResult mvcResult= this.mockMvc.perform(post("/user/sign-up").contentType(MediaType.APPLICATION_FORM_URLENCODED).
                param("username","get").param("password","12345")).andDo(print()).
                andExpect(status().isOk()).
                andExpect(jsonPath("$.data").isNotEmpty()).andReturn();

        String response = mvcResult.getResponse().getContentAsString();

        JSONObject jsonObj = new JSONObject(response);
        String tokenGet =  jsonObj.getJSONObject("data").get("token").toString();

        MvcResult m= this.mockMvc.perform(MockMvcRequestBuilders.get("/user/get").contentType(MediaType.APPLICATION_FORM_URLENCODED).
                header("Authorization",tokenGet)).andDo(print()).andReturn();
                //andExpect(status().isOk()).andReturn();
               // andExpect(jsonPath("$.data").isNotEmpty()).andReturn();
        String refsponse = m.getResponse().getContentAsString();




    }


}

