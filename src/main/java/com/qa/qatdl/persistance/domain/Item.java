package com.qa.qatdl.persistance.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@Entity
public class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long i_id;

    @NotNull
    private String name;

    @NotNull
    private Float price;

    @ManyToOne
    @JoinColumn(name = "basket_b_id", nullable = false)
    private Basket basket = null;

    public Item(@NotNull String name, @NotNull Float price, Basket basket) {
        this.name = name;
        this.price = price;
        this.basket = basket;
    }

    public Item(Long i_id, @NotNull String name, @NotNull Float price, Basket basket) {
        this.i_id = i_id;
        this.name = name;
        this.price = price;
        this.basket = basket;
    }
}
