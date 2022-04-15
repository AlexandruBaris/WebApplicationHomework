package com.homework.homeworkWebApp.service;

import com.homework.homeworkWebApp.model.Department;

import java.util.List;

public interface DepartmentService {
    List<Department> findAllDepartments();
    Department getDepartmentById(Integer id);
    void save(Department department);
    Department updateDepartment(Integer id,Department department);
}
