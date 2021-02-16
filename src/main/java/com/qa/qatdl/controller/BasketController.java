package com.qa.qatdl.controller;

import com.qa.qatdl.dto.BasketDTO;
import com.qa.qatdl.persistance.domain.Basket;
import com.qa.qatdl.service.BasketService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@CrossOrigin("http://localhost:8393")
@RequestMapping("/basket")
public class BasketController {

    final private BasketService basketService;

    @PostMapping("/create")
    public ResponseEntity<BasketDTO> create(@RequestBody BasketDTO inputBasketDTO) {
        BasketDTO basketDTO = this.basketService.create(inputBasketDTO);
        return new ResponseEntity<>(basketDTO, HttpStatus.CREATED);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<BasketDTO> delete(@RequestBody BasketDTO inputBasketDTO) {
        return this.basketService.delete(inputBasketDTO) ?
                new ResponseEntity<>(HttpStatus.NO_CONTENT)
                : new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<BasketDTO> deleteByID(@PathVariable Long id) {
        return this.basketService.deleteByID(id) ?
                new ResponseEntity<>(HttpStatus.NO_CONTENT)
                : new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @GetMapping("/read")
    public ResponseEntity<List<BasketDTO>> readAll() {
         return ResponseEntity.ok(this.basketService.readAll());
    }

    @GetMapping("/read/{id}")
    public ResponseEntity<BasketDTO> read(@PathVariable Long id) {
        return ResponseEntity.ok(this.basketService.read(id));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<BasketDTO> update(@PathVariable Long id, @RequestBody BasketDTO basketDTO) {
        return new ResponseEntity<>(this.basketService.updateByID(basketDTO, id), HttpStatus.ACCEPTED);
    }

}
