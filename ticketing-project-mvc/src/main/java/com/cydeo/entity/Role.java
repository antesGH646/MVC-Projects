package com.cydeo.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class Role {
    //these role is created to fill the role types under the Choose a role dropdown
    private Long id;
    private String description;
}
