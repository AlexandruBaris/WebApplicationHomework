package com.homework.homeworkWebApp.service.implementations;

import com.homework.homeworkWebApp.exceptions.NotFoundException;
import com.homework.homeworkWebApp.model.Department;
import com.homework.homeworkWebApp.model.dto.DepartmentDto;
import com.homework.homeworkWebApp.repo.DepartmentRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class DepartmentServiceImplTest {
    private final Department department = Department.builder().id(1).name("DEV").location("CH").build();
    private final Department department2 = Department.builder().id(2).name("HR").location("NY").build();
    private final Department department3 = Department.builder().id(3).name("TEST").location("DC").build();
    @Mock
    private DepartmentRepository repository;
    @InjectMocks
    private DepartmentServiceImpl service;

    @Test
    void findByInvalidIdThrowException() {
        when(repository.findDepartmentById(anyInt())).thenThrow(NotFoundException.class);
        assertThrows(NotFoundException.class, () -> repository.findDepartmentById(anyInt()));
    }

    @Test
    void findByIdShouldReturnDepartmentDto() {
        DepartmentDto departmentDto = DepartmentDto.from(department);

        when(repository.findDepartmentById(anyInt())).thenReturn(Optional.of(department));
        DepartmentDto expected = service.getDepartmentById(1);

        verify(repository).findDepartmentById(department.getId());

        assertThat(departmentDto).isEqualTo(expected);
    }

    @Test
    void findAllDepartmentsShouldReturnListOfAllDepartments() {
        List<Department> departments = Arrays.asList(department, department2, department3);
        Collection<DepartmentDto> dtoCollection = Arrays.asList(DepartmentDto.from(department),
                DepartmentDto.from(department2), DepartmentDto.from(department3));

        when(repository.findAll()).thenReturn(departments);

        List<DepartmentDto> departmentDtoList = service.findAllDepartments();

        verify(repository).findAll();

        assertThat(departmentDtoList.containsAll(dtoCollection)).isTrue();
    }

    @Test
    void saveShouldReturnDepartmentDto() {
        when(repository.save(any())).thenReturn(department);

        Department actual = repository.save(department);

        DepartmentDto expected = service.save(DepartmentDto.from(actual));

        verify(repository).save(department);

        assertThat(DepartmentDto.from(department)).isEqualTo(expected);
    }

    @Test
    void updateDepartmentShouldThrowExceptionIfIdNotFound() {
        when(repository.findDepartmentById(anyInt())).thenThrow(NotFoundException.class);
        assertThrows(NotFoundException.class, () -> service.updateDepartment(1, DepartmentDto.from(department)));
    }

    @Test
    void updateDepartmentShouldReturnDepartmentDto() {
        Department actual = Department.builder().id(department.getId()).name(department.getName()).location(department.getLocation()).build();
        when(repository.findDepartmentById(anyInt())).thenReturn(Optional.of(department));
        actual.setName("UI");
        actual.setLocation("NY");

        DepartmentDto expected = service.updateDepartment(department.getId(), DepartmentDto.from(actual));

        assertThat(expected).isEqualTo(DepartmentDto.from(department));
    }

}