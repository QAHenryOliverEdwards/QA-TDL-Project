package com.qa.qatdl.persistance.domain;

import lombok.Data;
import lombok.NoArgsConstructor;
import javax.validation.constraints.NotNull;

import javax.persistence.*;

@Data
@NoArgsConstructor
@Entity
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long tId;

    @NotNull
    private String name;

    @NotNull
    private String description;

    @ManyToOne
    @JoinColumn(name = "todolist_tdlId", nullable = false)
    private ToDoList toDoList = new ToDoList();

    public Task(@NotNull String name, @NotNull String description, ToDoList toDoList) {
        this.name = name;
        this.description = description;
        this.toDoList = toDoList;
    }

    public Task(Long tId, @NotNull String name, @NotNull String description) {
        this.tId = tId;
        this.name = name;
        this.description = description;
    }

    public Task(Long tId, @NotNull String name, @NotNull String description, ToDoList toDoList) {
        this.tId = tId;
        this.name = name;
        this.description = description;
        this.toDoList = toDoList;
    }
}
