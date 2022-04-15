package com.homework.homeworkWebApp.service;

import com.homework.homeworkWebApp.model.Department;
import com.homework.homeworkWebApp.repo.DepartmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DepartmentServiceImpl implements DepartmentService{

    private final DepartmentRepository repository;

    @Override
    public List<Department> findAllDepartments() {
        return repository.findAll();
    }

    @Override
    public Department getDepartmentById(Integer id) {
        return repository.findDepartmentById(id).
                orElseThrow(()-> new DepartmentNotFoundException("Department by id " + id + " was not found"));
    }

    @Override
    public void save(Department department) {
         repository.save(department);
    }

    @Override
    public Department updateDepartment(Integer id,Department department) {
        Optional<Department> departmentOptional = repository.findDepartmentById(id);
        if(departmentOptional.isEmpty()){
            throw new NoSuchElementException();
        }
        Department updateDepartment = departmentOptional.get();
        updateDepartment.setLocation(department.getName());
        updateDepartment.setLocation(department.getLocation());
        repository.save(updateDepartment);
        return updateDepartment;
    }
}
