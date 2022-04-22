package com.homework.homeworkWebApp.service.interfaces;

import com.homework.homeworkWebApp.model.Employee;
import com.homework.homeworkWebApp.model.dto.EmployeeDto;

import java.util.List;

public interface EmployeeService {
    List<EmployeeDto> findAllEmployees();
    EmployeeDto getEmployeeById(Integer id);
    EmployeeDto save(EmployeeDto employee);
    EmployeeDto updateEmployee(Integer id,EmployeeDto employee);

}
