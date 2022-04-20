package com.homework.homeworkWebApp.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Email;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import java.util.Objects;

@Getter
@Setter
@AllArgsConstructor
@Entity
@NoArgsConstructor
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    @NotBlank
    private String first_name;
    @NotBlank
    private String last_name;
    @ManyToOne
    private Department department;
    @NotBlank
    @Column(nullable = false,unique = true)
    private String email;
    @NotBlank
    @Column(nullable = false,unique = true)
    private String phone_Number;
    @DecimalMin(value = "1.00",message = "Salary must be >= 1.0")
    private Double salary;




    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Employee employee = (Employee) o;
        return id.equals(employee.id) && first_name.equals(employee.first_name)
                && last_name.equals(employee.last_name) && email.equals(employee.email)
                && phone_Number.equals(employee.phone_Number)
                && salary.equals(employee.salary);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, first_name, last_name, email, phone_Number, salary);
    }

}
