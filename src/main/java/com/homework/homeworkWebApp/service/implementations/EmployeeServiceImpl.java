package com.homework.homeworkWebApp.service.implementations;

import com.homework.homeworkWebApp.exceptions.AlreadyExistsException;
import com.homework.homeworkWebApp.exceptions.NotFoundException;
import com.homework.homeworkWebApp.model.Employee;
import com.homework.homeworkWebApp.model.dto.EmployeeDto;
import com.homework.homeworkWebApp.repo.EmployeeRepository;
import com.homework.homeworkWebApp.service.EmployeeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository repository;

    @Override
    public List<EmployeeDto> findAllEmployees() {
        List<Employee> employees = repository.findAll();
        List<EmployeeDto> employeeDtoList = new ArrayList<>();
        for (Employee employee : employees) {
            employeeDtoList.add(EmployeeDto.from(employee));
        }
        return employeeDtoList;
    }

    @Override
    public EmployeeDto getEmployeeById(Integer id) {
        return EmployeeDto.from(repository.findEmployeeById(id).
                orElseThrow(() -> new NotFoundException("Cannot find employee by id " + id)));

    }

    @Override
    @Transactional
    public EmployeeDto save(EmployeeDto employeeDto) {
        verifyPhoneAndEmail(employeeDto);

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
    public EmployeeDto updateEmployee(Integer id, EmployeeDto employeeDto) {
        Employee employee = repository.findEmployeeById(id).orElseThrow(() -> new NotFoundException("Id: " + id + " not found"));

        if (!employeeDto.equals(EmployeeDto.from(employee))) {
            employee.setFirstName(employeeDto.getFirstName());
            employee.setLastName(employeeDto.getLastName());
            employee.setDepartment(employeeDto.getDepartment());
            if(!Objects.equals(employee.getEmail(), employeeDto.getEmail())) {
                verifyEmail(employeeDto);
                employee.setEmail(employeeDto.getEmail());
            }
            if(!Objects.equals(employee.getPhoneNumber(), employeeDto.getPhoneNumber())) {
                verifyPhoneNumber(employeeDto);
                employee.setPhoneNumber(employeeDto.getPhoneNumber());
            }
            employee.setSalary(employeeDto.getSalary());
            repository.save(employee);
        }
        return EmployeeDto.from(employee);
    }

    public void verifyPhoneAndEmail(EmployeeDto employeeDto) {

        repository.findEmployeeByPhoneNumberAndEmail(employeeDto.getPhoneNumber(), employeeDto.getEmail()).ifPresent(employee -> {
            throw new AlreadyExistsException("This phone number : " + employeeDto.getPhoneNumber() + " and this email: "
                    + employeeDto.getEmail() + " have already been used");
        });
        verifyPhoneNumber(employeeDto);
        verifyEmail(employeeDto);

    }

    public void verifyEmail(EmployeeDto employeeDto) {
        repository.findEmployeeByEmail(employeeDto.getEmail()).ifPresent(employee -> {
            throw new AlreadyExistsException("This email: " + employeeDto.getEmail() + " is already in use");
        });
    }

    public void verifyPhoneNumber(EmployeeDto employeeDto) {
        repository.findEmployeeByPhoneNumber(employeeDto.getPhoneNumber()).ifPresent(employee -> {
            throw new AlreadyExistsException("This phone number : " + employeeDto.getPhoneNumber() + " is already in use");
        });
    }
}
