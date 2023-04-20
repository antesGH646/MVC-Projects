package com.cydeo.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import java.math.BigDecimal;

@NoArgsConstructor
@Setter
@Getter
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
