package com.homework.homeworkWebApp.controller;

import com.homework.homeworkWebApp.exceptions.AlreadyExistsException;
import com.homework.homeworkWebApp.exceptions.NotFoundException;
import com.homework.homeworkWebApp.model.dto.ErrorDto;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;


@RestControllerAdvice
public class GlobalControllerAdvice {

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<?> handleDepartmentNotFoundException(NotFoundException e) {
        ErrorDto dto = ErrorDto.builder()
                .message("Invalid ID")
                .details(e.getMessage())
                .timeStamp(LocalDateTime.now())
                .build();
        return ResponseEntity.badRequest().body(dto);
    }


    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> handleBlankInputException(MethodArgumentNotValidException e) {
        Map<String, String> errors = new HashMap<>();
        e.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return ResponseEntity.badRequest().body(errors);
    }

    @ExceptionHandler(AlreadyExistsException.class)
    public ResponseEntity<?> handle(AlreadyExistsException e) {
        ErrorDto dto = ErrorDto.builder()
                .message(e.getMessage())
                .details(e.toString())
                .timeStamp(LocalDateTime.now())
                .build();
        return ResponseEntity.badRequest().body(dto);
    }

    @ExceptionHandler({DataIntegrityViolationException.class})
    public ResponseEntity<?> handleUniqueConstraint(DataIntegrityViolationException e) {
        ErrorDto errorDto = ErrorDto.builder()
                .message(e.getMessage())
                .details(e.getMessage())
                .timeStamp(LocalDateTime.now())
                .build();
        return ResponseEntity.badRequest().body(errorDto);
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<?> handleInvalidArgument(MethodArgumentTypeMismatchException e) {
        ErrorDto errorDto = ErrorDto.builder()
                .message(e.getMessage())
                .timeStamp(LocalDateTime.now())
                .build();
        return ResponseEntity.badRequest().body(errorDto);
    }

}
