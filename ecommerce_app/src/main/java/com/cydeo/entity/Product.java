package com.cydeo.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;

@NoArgsConstructor
@Setter
@Getter
@Entity
public class Product extends BaseEntity{
    private String name;
    private BigDecimal price;
    private BigDecimal quantity;
    private BigDecimal remaining_quantity;

    @OneToMany(mappedBy = "product")
    private List<CartItem> cartItem;

    @ManyToMany
    @JoinTable(name = "product_category_rel",
            joinColumns = @JoinColumn(name ="p_id"),
            inverseJoinColumns = @JoinColumn(name ="c_id"))
    private List<Category> categoryList;
}
