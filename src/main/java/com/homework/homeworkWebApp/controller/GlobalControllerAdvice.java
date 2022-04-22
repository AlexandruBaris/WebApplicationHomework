package com.homework.homeworkWebApp.controller;

import com.homework.homeworkWebApp.exceptions.AlreadyExists;
import com.homework.homeworkWebApp.exceptions.NotFoundException;
import com.homework.homeworkWebApp.model.dto.ErrorDto;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;


@RestControllerAdvice
public class GlobalControllerAdvice {

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<?> handleDepartmentNotFoundException(NotFoundException e){
        ErrorDto dto = ErrorDto.builder()
                .message("Invalid ID")
                .details(e.getMessage())
                .timeStamp(LocalDateTime.now())
                .build();
        return ResponseEntity.badRequest().body(dto);
    }


    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> handleBlankInputException(MethodArgumentNotValidException e){
        Map<String, String> errors = new HashMap<>();
        e.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return ResponseEntity.badRequest().body(errors);
    }

    @ExceptionHandler(AlreadyExists.class)
    public ResponseEntity<?> handle(AlreadyExists exists){
        ErrorDto e = ErrorDto.builder()
                .message(exists.getMessage())
                .details(exists.getLocalizedMessage())
                .timeStamp(LocalDateTime.now())
                .build();
        return ResponseEntity.badRequest().body(e);
    }

//    @ExceptionHandler({DataIntegrityViolationException.class})
//    public ResponseEntity<?> handleUniqueConstraint(DataIntegrityViolationException e){
//        ErrorDto errorDto = ErrorDto.builder()
//                .message(e.getMessage())
//                .details(e.getMessage())
//                .timeStamp(LocalDateTime.now())
//                .build();
//        return ResponseEntity.badRequest().body(errorDto);
//    }

//    @ExceptionHandler({ConstraintViolationException.class})
//    public ResponseEntity<?> handle(ConstraintViolationException e){
//        ErrorDto errorDto = ErrorDto.builder()
//                .message(e.getMessage())
//                .details(String.valueOf(e.initCause(e)))
//                .timeStamp(LocalDateTime.now())
//                .build();
//        return ResponseEntity.badRequest().body(errorDto);
//    }

}
