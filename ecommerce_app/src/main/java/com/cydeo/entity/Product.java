package com.cydeo.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import java.math.BigDecimal;

@NoArgsConstructor
@Data
@Entity
public class Product extends BaseEntity{
    private String name;
    private BigDecimal price;
    private BigDecimal quantity;
    private BigDecimal remaining_quantity;
}
