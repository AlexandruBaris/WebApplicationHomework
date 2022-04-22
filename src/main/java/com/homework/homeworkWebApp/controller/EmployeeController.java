package com.homework.homeworkWebApp.controller;

import com.homework.homeworkWebApp.model.dto.EmployeeDto;
import com.homework.homeworkWebApp.service.interfaces.EmployeeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("employees")
@RequiredArgsConstructor
public class EmployeeController {

    private final EmployeeService service;

    @GetMapping()
    public ResponseEntity<List<EmployeeDto>>getAll(){
        return new ResponseEntity<>(service.findAllEmployees(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EmployeeDto> getEmployeeById(@PathVariable("id") Integer id){
        return new ResponseEntity<>(service.getEmployeeById(id), HttpStatus.OK);
    }

    @PostMapping()
    public ResponseEntity<EmployeeDto> addEmployee(@Validated @RequestBody EmployeeDto employeeDto){
         return new ResponseEntity<>(service.save(employeeDto), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<EmployeeDto> updateEmployee(@Validated @PathVariable("id") Integer id,@Validated @RequestBody EmployeeDto employeeDto){
        return new ResponseEntity<>(service.updateEmployee(id,employeeDto),HttpStatus.OK);
    }
}
