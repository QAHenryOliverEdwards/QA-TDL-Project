package com.qa.qatdl.controller;

import com.qa.qatdl.dto.ItemDTO;
import com.qa.qatdl.persistance.domain.Item;
import com.qa.qatdl.service.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/item")
@CrossOrigin
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ItemController {

    final private ItemService itemService;

    @PostMapping("/create")
    private ResponseEntity<ItemDTO> create(@RequestBody Item item) {
        ItemDTO itemDTO = this.itemService.create(item);
        return new ResponseEntity<>(itemDTO, HttpStatus.CREATED);
    }
}
