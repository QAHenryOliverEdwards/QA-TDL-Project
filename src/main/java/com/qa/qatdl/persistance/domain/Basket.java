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
    private Long b_id;

    @NotNull
    private String name;

    @OneToMany(mappedBy = "basket", fetch = FetchType.EAGER)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private List<Item> itemList;

    public Basket(@NotNull String name) {
        this.name = name;
    }

    public Basket(Long b_id, @NotNull String name) {
        this.b_id = b_id;
        this.name = name;
    }
}
