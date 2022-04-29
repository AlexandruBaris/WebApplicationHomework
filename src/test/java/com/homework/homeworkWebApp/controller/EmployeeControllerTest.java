package com.homework.homeworkWebApp.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.homework.homeworkWebApp.model.dto.EmployeeDto;
import com.homework.homeworkWebApp.service.EmployeeService;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import javax.transaction.Transactional;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class EmployeeControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private EmployeeService service;

    @Test
    void getAll() throws Exception {
        mockMvc.perform(get("/employees")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[*]", hasSize(4)))
                .andExpect(jsonPath("$[1].firstName", is("Mike")))
                .andExpect(jsonPath("$[3].lastName", is("Dicaprio")))
                .andExpect(jsonPath("$[2].id", is(22)));
    }

    @Test
    void getEmployeeById() throws Exception {
        mockMvc.perform(get("/employees/1")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("id", "1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName", is("Mike")))
                .andExpect(jsonPath("$.lastName", is("Petrov")))
                .andExpect(jsonPath("$.salary", is(1000.00)))
                .andExpect(jsonPath("$.phoneNumber", is("789123456")))
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.email", is("mike@gmai.com")));
    }

    @Test
    void addEmployee() throws Exception {
        EmployeeDto employeeDto = EmployeeDto.builder().firstName("Robert").lastName("Lew")
                .email("robert@gmail.com").phoneNumber("789123654").salary(200.00).build();

        mockMvc.perform(post("/employees")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(employeeDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.lastName", is("Lew")))
                .andExpect(jsonPath("$.firstName", is("Robert")))
                .andExpect(jsonPath("$.email", is("robert@gmail.com")))
                .andExpect(jsonPath("$.phoneNumber", is("789123654")))
                .andExpect(jsonPath("$.salary", is(200.00)))
                .andExpect(jsonPath("$.id").isNotEmpty());
    }

    @Test
    void updateEmployee() throws Exception {
        EmployeeDto dto = service.getEmployeeById(23);
        System.out.println(dto);
        dto.setFirstName("Michael");
        dto.setLastName("Jordan");
        dto.setPhoneNumber("789875421");
        dto.setEmail("michael@gmai.com");
        dto.setSalary(1000.00);

        mockMvc.perform(put("/employees/8")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto))
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName", is("Michael")))
                .andExpect(jsonPath("$.lastName", is("Jordan")))
                .andExpect(jsonPath("$.phoneNumber", is("789875421")))
                .andExpect(jsonPath("$.email", is("michael@gmai.com")))
                .andExpect(jsonPath("$.salary", is(1000.00)));

    }

    @Test
    void getEmployeeByInvalidIdShouldReturnBadRequest() throws Exception {
        mockMvc.perform(get("/employees/100")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("id", "100"))
                .andExpect(status().is4xxClientError());
    }

    @Test
    void getEmployeeByIdWithInvalidValueReturnBadRequest() throws Exception {
        mockMvc.perform(get("/employees/x")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("id", "x"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void passingNullEmptyOrBlankValuesThrowException() throws Exception {
        mockMvc.perform(post("/employees")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(null))
                )
                .andExpect(status().isBadRequest());
    }

    @Test
    void passingInvalidObjectOnUpdateThrowException() throws Exception {
        mockMvc.perform(put("/employees")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(null))
                )
                .andExpect(status().is4xxClientError());
    }

    @Test
    void whenSavingEmployeeWithAnExistingEmailOrPhoneNumberThrowException() throws Exception {
        EmployeeDto existingEmployee = service.getEmployeeById(1);
        EmployeeDto newEmployee = EmployeeDto.builder().firstName("Name").lastName("LastName")
                .email(existingEmployee.getEmail()).phoneNumber(existingEmployee.getPhoneNumber())
                .salary(100.00).build();

        mockMvc.perform(post("/employees")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(newEmployee)))
                .andExpect(status().is4xxClientError());
    }

    @Test
    void whenUpdatingEmployeeWithAnExistingEmailOrPhoneNumberThrowException() throws Exception {
        EmployeeDto employeeFromDb = service.getEmployeeById(1);
        EmployeeDto employeeToBeUpdated = service.getEmployeeById(23);

        employeeToBeUpdated.setEmail(employeeFromDb.getEmail());
        employeeToBeUpdated.setPhoneNumber(employeeFromDb.getPhoneNumber());

        mockMvc.perform(put("/employees/23")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(employeeToBeUpdated)))
                .andExpect(status().is4xxClientError());
    }
}