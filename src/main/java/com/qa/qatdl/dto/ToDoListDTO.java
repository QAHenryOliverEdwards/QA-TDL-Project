package com.qa.qatdl.dto;


import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
public class ToDoListDTO {

    private Long tdlId;
    private String name;
    private List<TaskDTO> taskList = new ArrayList<>();
}
