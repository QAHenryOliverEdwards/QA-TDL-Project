package com.qa.qatdl.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class TaskDTO {

    private Long tId;
    private String name;
    private String description;

}