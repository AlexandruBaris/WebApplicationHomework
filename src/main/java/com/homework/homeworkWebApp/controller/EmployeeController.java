package com.homework.homeworkWebApp.controller;

import com.homework.homeworkWebApp.service.EmployeeService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("employees")
@RequiredArgsConstructor
public class EmployeeController {
    private final EmployeeService service;

    @GetMapping
    public String getEmployees(){
        return "STARTED";
    }
}
