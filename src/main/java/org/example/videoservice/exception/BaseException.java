package org.example.videoservice.exception;

import lombok.Getter;

@Getter
public class BaseException extends RuntimeException{
    private int statusCode;


    public BaseException(String message) {
        super(message);
        this.statusCode = statusCode;
    }



}

