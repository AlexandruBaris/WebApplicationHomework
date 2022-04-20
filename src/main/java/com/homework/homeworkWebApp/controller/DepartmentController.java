package com.homework.homeworkWebApp.controller;

import com.homework.homeworkWebApp.model.dto.DepartmentDto;
import com.homework.homeworkWebApp.service.interfaces.DepartmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<List<DepartmentDto>> getAll(){
        List<DepartmentDto> departments = service.findAllDepartments();

        return new ResponseEntity<>(departments, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<DepartmentDto> getDepartmentById(@PathVariable("id") Integer id){
        DepartmentDto departmentDto = service.getDepartmentById(id);
        return new ResponseEntity<>(departmentDto, HttpStatus.OK);
    }

    @PostMapping(consumes = APPLICATION_JSON_VALUE)
    public ResponseEntity<DepartmentDto> addDepartment( @RequestBody @Valid DepartmentDto department){
        DepartmentDto newDepartment = service.save(department);
        return new ResponseEntity<>(newDepartment,HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<DepartmentDto> updateDepartment(@Valid @PathVariable("id")Integer id, @Valid @RequestBody DepartmentDto department){
        DepartmentDto updatedDepartment = service.updateDepartment(id,department);
        return new ResponseEntity<>(updatedDepartment,HttpStatus.OK);
    }


}
