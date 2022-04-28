package com.homework.homeworkWebApp.controller;

import com.homework.homeworkWebApp.model.dto.DepartmentDto;
import com.homework.homeworkWebApp.service.DepartmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping("departments")
@RequiredArgsConstructor
public class DepartmentController {

    private final DepartmentService service;


    @GetMapping()
    public ResponseEntity<List<DepartmentDto>> getAll() {
        return new ResponseEntity<>(service.findAllDepartments(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<DepartmentDto> getDepartmentById(@PathVariable("id") Integer id) {
        return new ResponseEntity<>(service.getDepartmentById(id), HttpStatus.OK);
    }

    @PostMapping(consumes = APPLICATION_JSON_VALUE)
    public ResponseEntity<DepartmentDto> addDepartment(@RequestBody @Validated DepartmentDto departmentDto) {
        return new ResponseEntity<>(service.save(departmentDto), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<DepartmentDto> updateDepartment(@Validated @PathVariable("id") Integer id, @Valid @RequestBody DepartmentDto departmentDto) {
        return new ResponseEntity<>(service.updateDepartment(id, departmentDto), HttpStatus.OK);
    }


}
