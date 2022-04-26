package com.homework.homeworkWebApp.model.dto;

import com.homework.homeworkWebApp.model.Department;
import com.homework.homeworkWebApp.model.Employee;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotBlank;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class EmployeeDto {
    private Integer id;
    @NotBlank(message = "The first name cannot be empty")
    private String firstName;
    @NotBlank(message = "The last name cannot be empty")
    private String lastName;
    private Department department;
    @NotBlank(message = "The email cannot be empty")
    @Column(unique = true)
    private String email;
    @NotBlank(message = "The phone number cannot be empty")
    @Column(unique = true)
    private String phoneNumber;
    @DecimalMin(value = "1.00", message = "Salary must be >= 1.0")
    private Double salary;

    public static EmployeeDto from(Employee employee) {
        EmployeeDto result = new EmployeeDto();
        result.setId(employee.getId());
        result.setFirstName(employee.getFirstName());
        result.setLastName(employee.getLastName());
        result.setDepartment(employee.getDepartment());
        result.setEmail(employee.getEmail());
        result.setPhoneNumber(employee.getPhoneNumber());
        result.setSalary(employee.getSalary());
        return result;
    }
}
