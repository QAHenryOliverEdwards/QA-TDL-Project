package com.qa.qatdl.dto;

import com.qa.qatdl.persistance.domain.Basket;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ItemDTO {

    private Long i_id;
    private String name;
    private Float price;

}