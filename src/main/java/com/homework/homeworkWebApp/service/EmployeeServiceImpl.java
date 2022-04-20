package com.homework.homeworkWebApp.service;

import com.homework.homeworkWebApp.model.Employee;
import com.homework.homeworkWebApp.repo.EmployeeRepository;
import com.homework.homeworkWebApp.service.interfaces.EmployeeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository repository;

    @Override
    public List<Employee> findAllEmployees() {
        return repository.findAll();
    }

    @Override
    public Employee getEmployeeById(Integer id) {
        return repository.findEmployeeById(id).
                orElseThrow(NoSuchElementException::new);
    }

    @Override
    public void save(Employee employee) {
        repository.save(employee);
    }

    @Override
    public Employee updateEmployee(Integer id,Employee employee) {
        Optional<Employee> employeeOptional = repository.findEmployeeById(id);
        if(employeeOptional.isEmpty()){
            throw new NoSuchElementException();
        }
        Employee updatedEmployee = employeeOptional.get();
        updatedEmployee.setFirst_name(employee.getFirst_name());
        updatedEmployee.setLast_name(employee.getLast_name());
        updatedEmployee.setDepartment(employee.getDepartment());
        updatedEmployee.setEmail(employee.getEmail());
        updatedEmployee.setPhone_Number(employee.getPhone_Number());
        updatedEmployee.setSalary(employee.getSalary());

        return null;
    }
}
