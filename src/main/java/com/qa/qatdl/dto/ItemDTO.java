package com.qa.qatdl.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ItemDTO {

    private Long iId;
    private String name;
    private Float price;

}