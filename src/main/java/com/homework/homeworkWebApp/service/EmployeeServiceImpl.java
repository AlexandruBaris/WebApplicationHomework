package com.homework.homeworkWebApp.service;

import com.homework.homeworkWebApp.exceptions.AlreadyExists;
import com.homework.homeworkWebApp.exceptions.NotFoundException;
import com.homework.homeworkWebApp.model.Employee;
import com.homework.homeworkWebApp.model.dto.EmployeeDto;
import com.homework.homeworkWebApp.repo.EmployeeRepository;
import com.homework.homeworkWebApp.service.interfaces.EmployeeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository repository;

    @Override
    public List<EmployeeDto> findAllEmployees() {
        List<Employee> employees = repository.findAll();
        List<EmployeeDto> employeeDtoList = new ArrayList<>();
        for(Employee employee : employees){
            employeeDtoList.add(EmployeeDto.from(employee));
        }
        return employeeDtoList;
    }

    @Override
    public EmployeeDto getEmployeeById(Integer id) {
        return EmployeeDto.from(repository.findEmployeeById(id).
                orElseThrow(()-> new NotFoundException("Cannot find employee by id "+ id)));

    }

    @Override
    @Transactional
    public EmployeeDto save(EmployeeDto employeeDto) {
        if(repository.findEmployeeByEmailOrPhoneNumber(employeeDto.getEmail(),employeeDto.getPhoneNumber()).isPresent()){
            System.out.println("IS PRESENT");
            throw new AlreadyExists("EXISTS");
        }
        Employee employee = Employee.builder()
                .firstName(employeeDto.getFirstName())
                .lastName(employeeDto.getLastName())
                .department(employeeDto.getDepartment())
                .email(employeeDto.getEmail())
                .phoneNumber(employeeDto.getPhoneNumber())
                .salary(employeeDto.getSalary())
                .build();
        return EmployeeDto.from(repository.save(employee));
    }

    @Override
    @Transactional
    public EmployeeDto updateEmployee(Integer id,EmployeeDto employeeDto) {
        Employee employee = repository.findEmployeeById(id).orElseThrow(()->new NotFoundException("Id: " +id+ " not found"));
        if(!employeeDto.equals(EmployeeDto.from(employee))){
            employee.setFirstName(employeeDto.getFirstName());
            employee.setLastName(employeeDto.getLastName());
            employee.setDepartment(employeeDto.getDepartment());
            employee.setEmail(employeeDto.getEmail());
            employee.setPhoneNumber(employeeDto.getPhoneNumber());
            employee.setSalary(employeeDto.getSalary());
            repository.save(employee);
        }
        return EmployeeDto.from(employee);
    }
}
