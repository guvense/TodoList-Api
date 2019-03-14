package com.project.project.Response;

public class MainResponse <T> {
    private T Data;
    private    boolean Success;
    private String ErrorMessage;

    public T getData() {
        return Data;
    }

    public void setData(T data) {
        Data = data;
    }

    public MainResponse(String errorMessage) {
        ErrorMessage = errorMessage;
        Success=false;
        Data=null;
    }

    public String getErrorMessage() {
        return ErrorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        ErrorMessage = errorMessage;
    }

    public MainResponse() {
        ErrorMessage = null;
        Success=true;
        Data=null;
    }

    public boolean isSuccess() {
        return Success;
    }

    public void setSuccess(boolean succes) {
        Success = succes;
    }

    public MainResponse(T data, boolean succes) {
        Data = data;
        Success = succes;
    }
}
