package com.homework.homeworkWebApp.model.dto;

import com.homework.homeworkWebApp.model.Department;
import com.homework.homeworkWebApp.model.Employee;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.Builder;

import javax.persistence.Column;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotBlank;
import java.util.Objects;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
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

    @Override
    public String toString() {
        return "EmployeeDto{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", department=" + department +
                ", email='" + email + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", salary=" + salary +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EmployeeDto that = (EmployeeDto) o;
        return id.equals(that.id) && firstName.equals(that.firstName) && lastName.equals(that.lastName) && email.equals(that.email) && phoneNumber.equals(that.phoneNumber) && salary.equals(that.salary);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, firstName, lastName, email, phoneNumber, salary);
    }
}
