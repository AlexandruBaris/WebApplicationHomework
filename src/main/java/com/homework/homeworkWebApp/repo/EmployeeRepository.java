package com.homework.homeworkWebApp.repo;

import com.homework.homeworkWebApp.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EmployeeRepository extends JpaRepository<Employee,Integer> {
    Optional<Employee> findEmployeeById(Integer id);
    Optional<Employee> findEmployeeByEmail(String email);
    Optional<Employee> findEmployeeByPhoneNumber(String number);

}
