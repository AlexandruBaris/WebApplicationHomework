package com.homework.homeworkWebApp.model.dto;

import com.homework.homeworkWebApp.model.Department;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import java.util.Objects;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class DepartmentDto {
    private Integer id;
    @NotBlank(message = "The name cannot be empty")
    private String name;
    @NotBlank(message = "The location cannot be empty")
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DepartmentDto that = (DepartmentDto) o;
        return id.equals(that.id) && name.equals(that.name) && location.equals(that.location);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, location);
    }
}
