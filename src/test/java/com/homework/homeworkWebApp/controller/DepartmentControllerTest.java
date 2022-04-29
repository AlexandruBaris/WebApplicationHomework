package com.homework.homeworkWebApp.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.homework.homeworkWebApp.model.dto.DepartmentDto;
import com.homework.homeworkWebApp.service.DepartmentService;
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
class DepartmentControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private DepartmentService service;

    @Test
    void getAll() throws Exception {
        mockMvc.perform(get("/departments")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[*]", hasSize(19)))
                .andExpect(jsonPath("$[0].id", is(26)))
                .andExpect(jsonPath("$[17].name", is("DEV")))
                .andExpect(jsonPath("$[16].location", is("CH")));
    }

    @Test
    void getDepartmentById() throws Exception {
        mockMvc.perform(get("/departments/33")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("id", "33"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(33))
                .andExpect(jsonPath("$.name").value("DEV"))
                .andExpect(jsonPath("$.location").value("CH"));
    }

    @Test
    void addDepartment() throws Exception {
        DepartmentDto departmentDto = DepartmentDto.builder().name("DEV").location("CH").build();

        mockMvc.perform(post("/departments")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(departmentDto))
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("DEV"))
                .andExpect(jsonPath("$.location").value("CH"))
                .andExpect(jsonPath("$.id").isNotEmpty());
    }

    @Test
    void updateDepartment() throws Exception {
        DepartmentDto departmentDto = service.getDepartmentById(2);
        departmentDto.setName("TESTING");
        departmentDto.setLocation("RO");

        mockMvc.perform(put("/departments/2")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(departmentDto))
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(2)))
                .andExpect(jsonPath("$.name", is("TESTING")))
                .andExpect(jsonPath("$.location", is("RO")));

    }

    @Test
    void getDepartmentByInvalidIdShouldReturnBadRequest() throws Exception {
        mockMvc.perform(get("/departments/1")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("id", "1"))
                .andExpect(status().is4xxClientError());
    }

    @Test
    void getDepartmentByIdWithInvalidValueReturnBadRequest() throws Exception {
        mockMvc.perform(get("/departments/x")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("id", "x"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void passingNullEmptyOrBlankValuesThrowException() throws Exception {
        mockMvc.perform(put("/departments")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(null))
                )
                .andExpect(status().isMethodNotAllowed());
    }

    @Test
    void passingInvalidObjectOnSaveThrowException() throws Exception {
        mockMvc.perform(post("/departments")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(null)))
                .andExpect(status().is4xxClientError());
    }
}