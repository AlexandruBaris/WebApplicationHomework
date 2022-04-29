package com.homework.homeworkWebApp.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class AlreadyExistsException extends RuntimeException {
    public AlreadyExistsException(String s) {
        super(s);
    }
}
