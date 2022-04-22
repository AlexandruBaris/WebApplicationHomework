package com.homework.homeworkWebApp.service.implementations;

import com.homework.homeworkWebApp.exceptions.AlreadyExists;
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
    public EmployeeDto updateEmployee(Integer id,EmployeeDto employeeDto) {
        Employee employee = repository.findEmployeeById(id).orElseThrow(()->new NotFoundException("Id: " +id+ " not found"));

        verifyPhoneAndEmail(employeeDto);

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

    private void verifyPhoneAndEmail(EmployeeDto employeeDto){
//        if(repository.findEmployeeByEmail(employeeDto.getEmail()).isPresent()){
//            throw new AlreadyExists("This email: " + employeeDto.getEmail() + " is already used");
//        }
//        if(repository.findEmployeeByPhoneNumber(employeeDto.getPhoneNumber()).isPresent()){
//            throw new AlreadyExists("This phone number : " + employeeDto.getPhoneNumber() + " is already used");
//        }
        boolean phone, email;
        phone = repository.findEmployeeByPhoneNumber(employeeDto.getPhoneNumber()).isPresent();
        email = repository.findEmployeeByEmail(employeeDto.getEmail()).isPresent();
        switch (phone+"-"+email){
            case "true-false" : throw new AlreadyExists("This phone number : " + employeeDto.getPhoneNumber() + " is already in use");
            case "false-true" : throw new AlreadyExists("This email: " + employeeDto.getEmail() + " is already in use");
            case "true-true"  : throw new AlreadyExists("This phone number : " + employeeDto.getPhoneNumber() + " and this email: "
                    + employeeDto.getEmail() + " have already been used");
        }
    }
}