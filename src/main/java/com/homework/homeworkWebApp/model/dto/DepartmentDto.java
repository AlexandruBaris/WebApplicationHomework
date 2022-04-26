package com.homework.homeworkWebApp.model.dto;

import com.homework.homeworkWebApp.model.Department;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class DepartmentDto {
    private Integer id;
    @NotBlank(message = "The location cannot be empty")
    private String name;
    @NotBlank(message = "The department cannot be empty")
    private String location;

    public static DepartmentDto from(Department department) {
        DepartmentDto result = new DepartmentDto();
        result.setId(department.getId());
        result.setName(department.getName());
        result.setLocation(department.getLocation());
        return result;
    }

    @Override
    public String toString() {
        return "DepartmentDto{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", location='" + location + '\'' +
                '}';
    }
}
