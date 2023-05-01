package com.cydeo.entity;

import com.cydeo.enums.Gender;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Where;

import javax.persistence.*;

@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "users")
@Where(clause = "is_deleted=false")//will automatically concatenate
// wherever the user repository is using the User
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

    @Override
    public String toString() {
        return "User{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", userName='" + userName + '\'' +
                ", passWord='" + passWord + '\'' +
                ", enabled=" + enabled +
                ", phone='" + phone + '\'' +
                ", gender=" + gender +
                '}';
    }

    //since it is using database no constructor b/c no need to use DataGenerator
}
