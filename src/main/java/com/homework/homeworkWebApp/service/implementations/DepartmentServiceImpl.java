package com.homework.homeworkWebApp.service.implementations;

import com.homework.homeworkWebApp.exceptions.NotFoundException;
import com.homework.homeworkWebApp.model.Department;
import com.homework.homeworkWebApp.model.dto.DepartmentDto;
import com.homework.homeworkWebApp.repo.DepartmentRepository;
import com.homework.homeworkWebApp.service.DepartmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DepartmentServiceImpl implements DepartmentService {

    private final DepartmentRepository repository;


    @Override
    public List<DepartmentDto> findAllDepartments() {
        List<Department> departments = repository.findAll();
        List<DepartmentDto> departmentDtos = new ArrayList<>();
        for (Department department : departments) {
            departmentDtos.add(DepartmentDto.from(department));
        }
        return departmentDtos;
    }

    @Override
    public DepartmentDto getDepartmentById(Integer id) {
        return DepartmentDto.from(repository.findDepartmentById(id).
                orElseThrow(() -> new NotFoundException("Department by id " + id + " was not found")));
    }

    @Override
    @Transactional
    public DepartmentDto save(DepartmentDto departmentDto) {
        Department department = Department.builder()
                .name(departmentDto.getName())
                .location(departmentDto.getLocation())
                .build();
        return DepartmentDto.from(repository.save(department));
    }

    @Override
    @Transactional
    public DepartmentDto updateDepartment(Integer id, DepartmentDto departmentDto) {
        Department department = repository.findDepartmentById(id).orElseThrow(() -> new NotFoundException("Department id: " + id + " was not found"));

        if (!departmentDto.equals(DepartmentDto.from(department))) {
            department.setName(departmentDto.getName());
            department.setLocation(departmentDto.getLocation());
            repository.save(department);
        }
        return DepartmentDto.from(department);

    }
}
