package com.gymmanagement.utils;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GeneralResponse<T> {
    private boolean success;

    private String message;

    private T data;

    public GeneralResponse() {
    }

    public GeneralResponse(boolean success, String message, T data) {
        this.success = success;
        this.message = message;
        this.data = data;
    }

//    public boolean isSuccess() {
//        return success;
//    }
//
//    public void setSuccess(boolean success) {
//        this.success = success;
//    }
//
//    public String getMessage() {
//        return message;
//    }
//
//    public void setMessage(String message) {
//        this.message = message;
//    }
//
//    public T getData() {
//        return data;
//    }
//
//    public void setData(T data) {
//        this.data = data;
//    }
}
