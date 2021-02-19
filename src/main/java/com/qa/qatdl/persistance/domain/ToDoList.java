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

    public ToDoList(Long tdlId) {
        this.tdlId = tdlId;
    }

    public ToDoList(Long tdlId, @NotNull String name) {
        this.tdlId = tdlId;
        this.name = name;
    }

}
