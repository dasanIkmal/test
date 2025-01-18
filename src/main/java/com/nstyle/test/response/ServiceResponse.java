package com.nstyle.test.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ServiceResponse<T> {
    private int statusCode;
    private String message;
    private T data;

    public ServiceResponse(int statusCode, String message, T data) {
        this.statusCode = statusCode;
        this.message = message;
        this.data = data;
    }

    public ServiceResponse(int statusCode, String message) {
        this.statusCode = statusCode;
        this.message = message;
    }
}
