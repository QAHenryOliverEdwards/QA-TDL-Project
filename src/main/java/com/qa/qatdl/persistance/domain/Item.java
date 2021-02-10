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
    @JoinColumn(name = "b_id", nullable = false)
    private Basket basket = null;
}
