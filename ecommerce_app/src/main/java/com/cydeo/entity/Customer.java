package com.cydeo.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import java.util.List;

@NoArgsConstructor
@Data
@Entity
public class Customer extends BaseEntity{
    private String email;
    private String firstName;
    private String lastName;
    private String userName;

    @OneToOne(mappedBy = "customer")
    private Balance balance ;

    @OneToMany(mappedBy = "customer")
    private List<Address> address ;

    @OneToMany(mappedBy = "customer")
    private List<Orders> orders ;

    @OneToMany(mappedBy = "customer")
    private List<Cart> carts ;

    public Customer(String email, String firstName,
                    String lastName, String userName) {
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.userName = userName;
    }
}
