package com.homework.homeworkWebApp.controller;

import com.homework.homeworkWebApp.model.Department;
import com.homework.homeworkWebApp.service.DepartmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("departments")
@RequiredArgsConstructor
public class DepartmentController {

    private final DepartmentService service;

    @GetMapping
    public ResponseEntity<?> getAll(){
        List<Department> departments = service.findAllDepartments();
        return ResponseEntity.ok(departments);
    }
}
