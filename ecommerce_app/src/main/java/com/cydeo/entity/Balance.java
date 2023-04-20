package com.cydeo.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import java.math.BigDecimal;

@NoArgsConstructor
@Data
@Entity
public class Balance extends BaseEntity{
    private BigDecimal amount;

    @OneToOne()
    private Customer customer;

    public Balance(BigDecimal amount) {
        this.amount = amount;
    }
}
