package com.homework.homeworkWebApp.service.interfaces;

import com.homework.homeworkWebApp.model.Department;
import com.homework.homeworkWebApp.model.dto.DepartmentDto;

import java.util.List;

public interface DepartmentService {
    List<DepartmentDto> findAllDepartments();
    DepartmentDto getDepartmentById(Integer id);
    DepartmentDto save(DepartmentDto departmentDto);
    DepartmentDto updateDepartment(Integer id,DepartmentDto departmentDto);
}
