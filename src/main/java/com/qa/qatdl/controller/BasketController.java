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
@CrossOrigin
@RequestMapping("/basket")
public class BasketController {

    final private BasketService basketService;

    @PostMapping("/create")
    private ResponseEntity<BasketDTO> create(@RequestBody Basket basket) {
        BasketDTO basketDTO = this.basketService.create(basket);
        return new ResponseEntity<>(basketDTO, HttpStatus.CREATED);
    }

    @DeleteMapping("/delete")
    private ResponseEntity<BasketDTO> delete(@RequestBody Basket basket) {
        return this.basketService.delete(basket) ?
                new ResponseEntity<>(HttpStatus.NO_CONTENT)
                : new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @DeleteMapping("/delete/{id}")
    private ResponseEntity<BasketDTO> deleteByID(@PathVariable Long id) {
        return this.basketService.deleteByID(id) ?
                new ResponseEntity<>(HttpStatus.NO_CONTENT)
                : new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @GetMapping("/read")
    private ResponseEntity<List<BasketDTO>> readAll() {
         return ResponseEntity.ok(this.basketService.readAll());
    }

}
