package com.cydeo.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;

@NoArgsConstructor
@Setter
@Getter
@Entity
public class Category extends BaseEntity{
    private String name;
}
