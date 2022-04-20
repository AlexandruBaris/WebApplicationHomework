package com.homework.homeworkWebApp.service;

import com.homework.homeworkWebApp.model.Department;
import com.homework.homeworkWebApp.model.dto.DepartmentDto;
import com.homework.homeworkWebApp.repo.DepartmentRepository;
import com.homework.homeworkWebApp.service.exceptions.DepartmentNotFoundException;
import com.homework.homeworkWebApp.service.interfaces.DepartmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.util.*;

@Service
@RequiredArgsConstructor
public class DepartmentServiceImpl implements DepartmentService {

    private final DepartmentRepository repository;


    @Override
    @Transactional
    public List<DepartmentDto> findAllDepartments() {
        List<Department> departments = repository.findAll();
        List<DepartmentDto> departmentDtos = new ArrayList<>();
        for(Department department : departments){
            departmentDtos.add(DepartmentDto.from(department));
        }
        return departmentDtos;
    }

    @Override
    public DepartmentDto getDepartmentById(Integer id) {
        return DepartmentDto.from(repository.findDepartmentById(id).
                orElseThrow(()-> new DepartmentNotFoundException("Department by id " + id + " was not found")));
    }

    @Override
    @Transactional
    public DepartmentDto save(DepartmentDto departmentDto) {
        System.out.println(departmentDto);
        Department department = Department.builder()
                .name(departmentDto.getName())
                .location(departmentDto.getLocation())
                .build();
        System.out.println(department);
         return DepartmentDto.from(repository.save(department));
    }

    @Override
    public DepartmentDto updateDepartment(Integer id,DepartmentDto departmentDto) {
        Department department = repository.findDepartmentById(id).orElseThrow();
        if(!departmentDto.equals(DepartmentDto.from(department))) {
            department.setName(departmentDto.getName());
            department.setLocation(departmentDto.getLocation());
            repository.save(department);
        }
        return DepartmentDto.from(department);

    }
}
