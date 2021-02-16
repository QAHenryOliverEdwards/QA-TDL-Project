package com.qa.qatdl.persistance.domain;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@NoArgsConstructor
@Entity
public class Basket {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long bId;

    @NotNull
    private String name;

    @OneToMany(mappedBy = "basket", fetch = FetchType.EAGER)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private List<Item> itemList;

    public Basket(@NotNull String name) {
        this.name = name;
    }

    public Basket(Long bId, @NotNull String name) {
        this.bId = bId;
        this.name = name;
    }

    public Basket(Long bId) {
        this.bId = bId;
    }
}
