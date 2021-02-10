package com.qa.qatdl.dto;


import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
public class BasketDTO {

    private Long b_id;
    private String name;
    private List<ItemDTO> itemDTOList = new ArrayList<>();
}
