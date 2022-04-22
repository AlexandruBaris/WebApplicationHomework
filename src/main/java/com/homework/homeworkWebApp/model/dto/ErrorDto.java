package com.homework.homeworkWebApp.model.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class ErrorDto {
    private LocalDateTime timeStamp;
    private String message;
    private String details;
}
