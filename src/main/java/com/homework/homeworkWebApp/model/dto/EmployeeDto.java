package com.homework.homeworkWebApp.model.dto;

import com.homework.homeworkWebApp.model.Department;
import com.homework.homeworkWebApp.model.Employee;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class EmployeeDto {
    private Integer id;
    private String first_name;
    private String last_name;
    private Department department;
    private String email;
    private String phone_number;

    public static EmployeeDto from(Employee employee){
        EmployeeDto result = new EmployeeDto();
        result.setId(employee.getId());
        result.setFirst_name(employee.getFirst_name());
        result.setLast_name(employee.getLast_name());
        result.setDepartment(employee.getDepartment());
        result.setEmail(employee.getEmail());
        result.setPhone_number(employee.getPhone_Number());
        return result;
    }
}
