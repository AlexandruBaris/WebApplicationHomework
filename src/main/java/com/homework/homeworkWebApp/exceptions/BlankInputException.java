package com.homework.homeworkWebApp.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class BlankInputException extends RuntimeException{
    public BlankInputException(String s){
        super(s);
    }
}
