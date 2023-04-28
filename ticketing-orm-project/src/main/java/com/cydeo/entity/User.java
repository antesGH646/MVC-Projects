package com.cydeo.entity;

import com.cydeo.enums.Gender;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "users")
public class User extends BaseEntity {

    private String firstName;
    private String lastName;
    private String userName;
    private String passWord;
    private boolean enabled;
    private String phone;

    //mapping to create the relationship, many user can have one role
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="role_id")//good practice to name the jpa generated foreign key
    private Role role;

    @Enumerated(EnumType.STRING)//to avoid the default 0 & 1 ordinal values
    private Gender gender;

    //No constructor b/c no need to use DataGenerator
    // It is directly using the database
}
