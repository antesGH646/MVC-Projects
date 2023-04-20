package com.cydeo.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import java.math.BigDecimal;

@NoArgsConstructor
@Data
@Entity
public class Orders extends BaseEntity{
    private BigDecimal paidPrice;
    private BigDecimal totalPrice;

    @ManyToOne(fetch = FetchType.LAZY)
    private Customer customer;

    @OneToOne
    private Cart cart;

    @OneToOne
    private Payment payment;
}
