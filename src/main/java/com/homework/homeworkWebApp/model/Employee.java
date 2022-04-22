package com.homework.homeworkWebApp.model;

import lombok.*;

import javax.persistence.*;
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
@SequenceGenerator(name = "mySeq",sequenceName = "id_sequence",schema = "hr_migration",allocationSize = 1)
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO,generator = "mySeq")
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
    @Column(nullable = false,unique = true,name = "email")
    private String email;
    @NotBlank
    @Column(nullable = false,unique = true, name = "phone_number")
    private String phoneNumber;
    @DecimalMin(value = "1.00")
    @Column(name = "salary")
    private Double salary;




    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Employee employee = (Employee) o;
        return id.equals(employee.id) && firstName.equals(employee.firstName)
                && lastName.equals(employee.lastName) && email.equals(employee.email)
                && phoneNumber.equals(employee.phoneNumber)
                && salary.equals(employee.salary);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, firstName, lastName, email, phoneNumber, salary);
    }

}
