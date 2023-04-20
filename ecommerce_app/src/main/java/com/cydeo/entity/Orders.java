package com.cydeo.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import java.math.BigDecimal;

@NoArgsConstructor
@Data
@Entity
public class Orders extends BaseEntity{
    private BigDecimal paidPrice;
    private BigDecimal totalPrice;
}
