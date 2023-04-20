package com.cydeo.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import java.util.List;

@NoArgsConstructor
@Setter
@Getter
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
}
