package com.cydeo.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import java.math.BigDecimal;

@NoArgsConstructor
@Data
@Entity
public class CartItem  extends BaseEntity{

    private BigDecimal quantity;

    @ManyToOne
    private Product product;

    @ManyToOne
    private Cart cart;
}
