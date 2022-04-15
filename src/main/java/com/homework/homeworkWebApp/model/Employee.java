package com.homework.homeworkWebApp.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
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
    private String first_name;
    private String last_name;
    @ManyToOne
    private Department department;
    private String email;
    private String phoneNumber;
    private Double salary;




    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Employee employee = (Employee) o;
        return id.equals(employee.id) && first_name.equals(employee.first_name)
                && last_name.equals(employee.last_name) && email.equals(employee.email)
                && phoneNumber.equals(employee.phoneNumber)
                && salary.equals(employee.salary);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, first_name, last_name, email, phoneNumber, salary);
    }

}
