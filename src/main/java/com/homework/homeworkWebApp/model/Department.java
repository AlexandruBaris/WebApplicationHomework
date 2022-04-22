package com.homework.homeworkWebApp.model;


import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.Objects;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@SequenceGenerator(name = "mySeq",sequenceName = "id_sequence",schema = "hr_migration",allocationSize = 1)
public class Department {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "mySeq")
    @Column(name = "department_id")
    private Integer id;
    @NotBlank
    @Column(name = "department_name")
    private String name;
    @Column(name = "department_location")
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
