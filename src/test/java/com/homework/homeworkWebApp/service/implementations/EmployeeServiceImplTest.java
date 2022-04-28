package com.homework.homeworkWebApp.service.implementations;

import com.homework.homeworkWebApp.exceptions.AlreadyExistsException;
import com.homework.homeworkWebApp.exceptions.NotFoundException;
import com.homework.homeworkWebApp.model.Employee;
import com.homework.homeworkWebApp.model.dto.EmployeeDto;
import com.homework.homeworkWebApp.repo.EmployeeRepository;
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
class EmployeeServiceImplTest {

    private final Employee employee = Employee.builder().id(1).firstName("Alex").lastName("Green")
            .phoneNumber("123").email("abc@gmail.com").salary(200.00).department(null).build();
    private final Employee employee2 = Employee.builder().id(2).firstName("Jon").lastName("Jones")
            .phoneNumber("1234").email("abcd@gmail.com").salary(200.00).department(null).build();
    private final Employee employee3 = Employee.builder().id(3).firstName("Anderson").lastName("Silva")
            .phoneNumber("12345").email("abcde@gmail.com").salary(200.00).department(null).build();
    @InjectMocks
    private EmployeeServiceImpl service;
    @Mock
    private EmployeeRepository repository;

    @Test
    void findAllEmployeesShouldReturnAListOfEmployeeDto() {
        List<Employee> employees = Arrays.asList(employee, employee2, employee3);
        Collection<EmployeeDto> dtoCollection = Arrays.asList(EmployeeDto.from(employee),
                EmployeeDto.from(employee2), EmployeeDto.from(employee3));

        when(repository.findAll()).thenReturn(employees);

        List<EmployeeDto> employeeDtos = service.findAllEmployees();

        verify(repository).findAll();
        System.out.println(dtoCollection);
        System.out.println(employeeDtos);

        assertThat(employeeDtos.containsAll(dtoCollection)).isTrue();
    }

    @Test
    void findByInvalidIdShouldThrowException() {
        when(repository.findEmployeeById(anyInt())).thenThrow(NotFoundException.class);
        assertThrows(NotFoundException.class, () -> repository.findEmployeeById(employee.getId()));
    }

    @Test
    void getEmployeeByIdShouldReturnEmployeeDto() {
        EmployeeDto employeeDto = EmployeeDto.from(employee);

        when(repository.findEmployeeById(anyInt())).thenReturn(Optional.of(employee));

        EmployeeDto expected = service.getEmployeeById(1);

        verify(repository).findEmployeeById(employee.getId());

        assertThat(employeeDto).isEqualTo(expected);
    }

    @Test
    void saveShouldReturnEmployeeDto() {
        when(repository.save(any())).thenReturn(employee);

        Employee actual = repository.save(employee);

        EmployeeDto expected = service.save(EmployeeDto.from(actual));

        assertThat(EmployeeDto.from(employee)).isEqualTo(expected);
    }

    @Test
    void ifEmployeeWithEmailIsPresentShouldThrowException() {
        when(repository.findEmployeeByEmail(any())).thenReturn(Optional.of(employee));
        assertThrows(AlreadyExistsException.class, () -> service.verifyPhoneAndEmail(EmployeeDto.from(employee)));
    }

    @Test
    void ifEmployeeWithPhoneNumberIsPresentShouldThrowException() {
        when(repository.findEmployeeByPhoneNumber(any())).thenReturn(Optional.of(employee));
        assertThrows(AlreadyExistsException.class, () -> service.verifyPhoneAndEmail(EmployeeDto.from(employee)));
    }

    @Test
    void ifEmployeeWithEmailAndPhoneNumberIsPresentShouldThrowException() {
        when(repository.findEmployeeByPhoneNumberAndEmail(any(), any())).thenReturn(Optional.of(employee));
        assertThrows(AlreadyExistsException.class, () -> service.verifyPhoneAndEmail(EmployeeDto.from(employee)));
    }

    @Test
    void updateEmployee() {
        Employee actual = Employee.builder().id(employee.getId()).firstName(employee.getFirstName())
                .lastName(employee.getLastName()).email(employee.getEmail()).salary(employee.getSalary())
                .department(employee.getDepartment()).phoneNumber(employee.getPhoneNumber()).build();

        when(repository.findEmployeeById(anyInt())).thenReturn(Optional.of(employee));
        actual.setFirstName("FirstName");
        actual.setLastName("LastName");

        EmployeeDto expected = service.updateEmployee(employee.getId(), EmployeeDto.from(actual));

        assertThat(expected).isEqualTo(EmployeeDto.from(employee));
    }
}