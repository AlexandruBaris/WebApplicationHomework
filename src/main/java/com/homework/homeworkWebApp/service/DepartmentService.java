package com.homework.homeworkWebApp.service;

import com.homework.homeworkWebApp.model.dto.DepartmentDto;

import java.util.List;

public interface DepartmentService {
    List<DepartmentDto> findAllDepartments();

    DepartmentDto getDepartmentById(Integer id);

    DepartmentDto save(DepartmentDto departmentDto);

    DepartmentDto updateDepartment(Integer id, DepartmentDto departmentDto);
}
