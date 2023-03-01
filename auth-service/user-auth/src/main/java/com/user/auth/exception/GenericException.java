package com.user.auth.exception;

import lombok.Data;

@Data
public class GenericException extends RuntimeException{

    private String message;
    private int statusCode;

    public GenericException(String message,int statusCode){
        super(message);
        this.message=message;
        this.statusCode=statusCode;
    }
}
