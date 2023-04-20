package com.cydeo.entity;

import com.cydeo.enums.DiscountType;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.OneToMany;
import java.math.BigDecimal;
import java.util.List;

@NoArgsConstructor
@Data
@Entity
public class Discount extends BaseEntity {
     private BigDecimal discount;

     @Enumerated(EnumType.STRING)
     private DiscountType discountType;

     private String name;

     @OneToMany(mappedBy = "discount")
     private List<Cart> cart;
}
