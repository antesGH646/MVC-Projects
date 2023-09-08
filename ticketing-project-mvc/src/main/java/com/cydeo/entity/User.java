package com.cydeo.entity;

import com.cydeo.enums.Gender;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

//when a class is extending from super class lombok will not create for the super, create it manually
@NoArgsConstructor
@Data
public class User extends BaseEntity{
    //these are the fields shown in the user create form
    private String firstName;
    private String lastName;
    private String userName;
    private String password;
    private boolean enabled;
    private String phone;
    private Role role;
    private Gender gender;

    //manually created all arguments constructor
    public User(Long id, LocalDateTime insertDateTime, Long insertUserId,
                LocalDateTime lastUpdateDateTime, Long lastUpdateUserId,
                String firstName, String lastName, String userName,
                String passWord, boolean enabled, String phone, Role role, Gender gender) {
        super(id, insertDateTime, insertUserId, lastUpdateDateTime, lastUpdateUserId);
        this.firstName = firstName;
        this.lastName = lastName;
        this.userName = userName;
        this.password = passWord;
        this.enabled = enabled;
        this.role = role;
        this.phone = phone;
        this.gender = gender;
    }
}

