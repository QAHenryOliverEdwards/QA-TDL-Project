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
    private Long iId;

    @NotNull
    private String name;

    @NotNull
    private Float price;

    @ManyToOne
    @JoinColumn(name = "basket_b_id", nullable = false)
    private Basket basket = new Basket();

    public Item(@NotNull String name, @NotNull Float price, Basket basket) {
        this.name = name;
        this.price = price;
        this.basket = basket;
    }

    public Item(Long iId, @NotNull String name, @NotNull Float price, Basket basket) {
        this.iId = iId;
        this.name = name;
        this.price = price;
        this.basket = basket;
    }

}
