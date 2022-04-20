package com.homework.homeworkWebApp.controller;

import com.homework.homeworkWebApp.model.Employee;
import com.homework.homeworkWebApp.service.interfaces.EmployeeService;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("employees")
@RequiredArgsConstructor
public class EmployeeController {

    private final EmployeeService service;

    @GetMapping()
    public List<Employee> getAll(){
        return service.findAllEmployees();
    }

    @GetMapping("/{id}")
    public Employee getEmployeeById(@PathVariable("id") Integer id){
        return service.getEmployeeById(id);
    }

    @PostMapping()
    public void addEmployee(@Validated @RequestBody Employee employee){
         service.save(employee);
    }

    @PutMapping("/{id}")
    public void updateEmployee(@Validated @PathVariable("id") Integer id,@Validated @RequestBody Employee employee){
        service.updateEmployee(id,employee);
    }
}
