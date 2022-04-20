package com.homework.homeworkWebApp.service.interfaces;

import com.homework.homeworkWebApp.model.Employee;

import java.util.List;

public interface EmployeeService {
    List<Employee> findAllEmployees();
    Employee getEmployeeById(Integer id);
    void save(Employee employee);
    Employee updateEmployee(Integer id,Employee employee);

}
