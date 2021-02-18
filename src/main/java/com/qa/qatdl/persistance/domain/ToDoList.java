package com.qa.qatdl.persistance.domain;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.jetbrains.annotations.NotNull;

import javax.persistence.*;
import java.util.List;

@Data
@NoArgsConstructor
@Entity
public class ToDoList {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long tdlId;

    @NotNull
    private String name;

    @OneToMany(mappedBy = "toDoList", fetch = FetchType.EAGER)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private List<Task> taskList;

    public ToDoList(@NotNull String name) {
        this.name = name;
    }

    public ToDoList(@NotNull Long tdlId) {
        this.tdlId = tdlId;
    }

    public ToDoList(@NotNull Long tdlId, @NotNull String name) {
        this.tdlId = tdlId;
        this.name = name;
    }

}
