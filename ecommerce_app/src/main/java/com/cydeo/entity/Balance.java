package com.cydeo.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.OneToOne;
import java.math.BigDecimal;

@NoArgsConstructor
@Setter
@Getter
@Entity
public class Balance extends BaseEntity{
    private BigDecimal amount;

    @OneToOne()
    private Customer customer;
}
