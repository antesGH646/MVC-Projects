
package com.cydeo.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ClassDTO {

    @JsonIgnore
    private Long id;
    private String name;
    private Integer year;
    private CourseDTO course;
    private TeacherDTO teacher;
}