package com.homework.homeworkWebApp.model;

import lombok.Getter;
import lombok.Setter;
import lombok.Builder;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;


import javax.persistence.Id;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.SequenceGenerator;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Column;
import javax.persistence.ManyToOne;
import javax.persistence.JoinColumn;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotBlank;
import java.util.Objects;

@Builder
@Getter
@Setter
@AllArgsConstructor
@Entity
@NoArgsConstructor
@Table(name = "employees")
@SequenceGenerator(name = "mySeq", sequenceName = "id_sequence", schema = "hr_migration", allocationSize = 1)
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "mySeq")
    @Column(name = "employee_id")
    private Integer id;
    @NotBlank
    @Column(name = "first_name")
    private String firstName;
    @NotBlank
    @Column(name = "last_name")
    private String lastName;
    @ManyToOne
    @JoinColumn(name = "department_id")
    private Department department;
    @NotBlank
    @Column(nullable = false, unique = true, name = "email")
    private String email;
    @NotBlank
    @Column(nullable = false, unique = true, name = "phone_number")
    private String phoneNumber;
    @DecimalMin(value = "1.00")
    @Column(name = "salary")
    private Double salary;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Employee employee = (Employee) o;
        return firstName.equals(employee.firstName) && lastName.equals(employee.lastName) && email.equals(employee.email) && phoneNumber.equals(employee.phoneNumber) && salary.equals(employee.salary);
    }

    @Override
    public int hashCode() {
        return Objects.hash(firstName, lastName, email, phoneNumber, salary);
    }
}



