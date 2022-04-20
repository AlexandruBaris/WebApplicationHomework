package com.homework.homeworkWebApp.repo;

import com.homework.homeworkWebApp.model.Department;
import com.homework.homeworkWebApp.model.dto.DepartmentDto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DepartmentRepository extends JpaRepository<Department,Integer> {
    Optional<Department> findDepartmentById(Integer id);
}
