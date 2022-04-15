package com.homework.homeworkWebApp.model;


import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.util.Objects;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Department {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    @NotNull
    @NotEmpty
    @NotBlank
    private String name;
    @NotNull
    @NotEmpty
    @NotBlank
    private String location;


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Department that = (Department) o;
        return id.equals(that.id) && name.equals(that.name) && location.equals(that.location);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, location);
    }
}
