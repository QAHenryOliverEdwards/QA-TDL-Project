package com.qa.qatdl.controller;

import com.qa.qatdl.dto.ItemDTO;
import com.qa.qatdl.persistance.domain.Item;
import com.qa.qatdl.service.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @DeleteMapping("/delete")
    private ResponseEntity<ItemDTO> delete(@RequestBody Item item) {
        return this.itemService.delete(item) ?
                new ResponseEntity<>(HttpStatus.NO_CONTENT)
                : new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @DeleteMapping("/delete/{id}")
    private ResponseEntity<ItemDTO> deleteByID(@PathVariable Long id) {
        return this.itemService.deleteByID(id) ?
                new ResponseEntity<>(HttpStatus.NO_CONTENT)
                : new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @PutMapping("/update/{id}")
    private ResponseEntity<ItemDTO> updateByID(@RequestBody ItemDTO itemDTO, @PathVariable Long id) {
        return new ResponseEntity<>(this.itemService.updateByID(itemDTO, id), HttpStatus.ACCEPTED);
    }

    @GetMapping("/read")
    private ResponseEntity<List<ItemDTO>> readAll() {
        return ResponseEntity.ok(this.itemService.readAll());
    }
}
