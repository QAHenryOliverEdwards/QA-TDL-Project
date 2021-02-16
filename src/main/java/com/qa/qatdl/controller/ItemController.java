package com.qa.qatdl.controller;

import com.qa.qatdl.dto.ItemDTO;
import com.qa.qatdl.service.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@RequestMapping("/item")
@CrossOrigin("http://localhost:8393")
public class ItemController {

    private final ItemService itemService;

    @PostMapping("/create/{id}")
    public ResponseEntity<ItemDTO> create(@RequestBody ItemDTO inputItemDTO, @PathVariable Long id) {
        ItemDTO itemDTO = this.itemService.create(inputItemDTO, id);
        return new ResponseEntity<>(itemDTO, HttpStatus.CREATED);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<ItemDTO> delete(@RequestBody ItemDTO itemDTO) {
        return this.itemService.delete(itemDTO) ?
                new ResponseEntity<>(HttpStatus.NO_CONTENT)
                : new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ItemDTO> deleteByID(@PathVariable Long id) {
        return this.itemService.deleteByID(id) ?
                new ResponseEntity<>(HttpStatus.NO_CONTENT)
                : new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<ItemDTO> updateByID(@RequestBody ItemDTO itemDTO, @PathVariable Long id) {
        return new ResponseEntity<>(this.itemService.updateByID(itemDTO, id), HttpStatus.ACCEPTED);
    }

    @GetMapping("/read")
    public ResponseEntity<List<ItemDTO>> readAll() {
        return ResponseEntity.ok(this.itemService.readAll());
    }

    @GetMapping("/read/{id}")
    public ResponseEntity<ItemDTO> read(@PathVariable Long id) {
        return ResponseEntity.ok(this.itemService.read(id));
    }

}
