package com.cydeo.entity;

import com.cydeo.enums.CartState;
import com.cydeo.enums.DiscountType;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@NoArgsConstructor
@Data
@Entity
public class Cart extends BaseEntity {

    @Enumerated(EnumType.STRING)
    private CartState cartState;

    @ManyToOne(fetch = FetchType.LAZY)
    private Discount discount;

    @ManyToOne(fetch = FetchType.LAZY)
    private Customer customer;

    @OneToOne
    private Orders orders;

    @OneToMany(mappedBy = "cart")
    private List<CartItem> cartItem;
}
