package com.project.project.Response;

public class TokenResponse {
    private String token;

    public TokenResponse(String token) {
        this.token = token;
    }
    public TokenResponse() {

    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
