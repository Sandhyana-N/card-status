package com.demo.service.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@AllArgsConstructor
public class BusinessException extends Exception{

    private String errorMessage;
    private String errorCode;
    private boolean retryable;

    public BusinessException(String message){
        super(message);
    }
}
