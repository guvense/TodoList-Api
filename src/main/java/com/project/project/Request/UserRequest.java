package com.project.project.Request;

public class UserRequest {
    private String username;
    private String password;

    public String getUsername() {
        return username;
    }

    public UserRequest(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
