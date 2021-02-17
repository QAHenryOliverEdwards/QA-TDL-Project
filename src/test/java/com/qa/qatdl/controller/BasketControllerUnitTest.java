package com.qa.qatdl.controller;

import com.qa.qatdl.dto.BasketDTO;
import com.qa.qatdl.persistance.domain.Basket;
import com.qa.qatdl.service.BasketService;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@SpringBootTest
@Profile("dev")
public class BasketControllerUnitTest {

    @Autowired
    BasketController basketController;

    @MockBean
    BasketService basketService;

    @Autowired
    ModelMapper modelMapper;

    private BasketDTO mapToDTO(Basket basket) {
        return this.modelMapper.map(basket, BasketDTO.class);
    }

    private final Basket basket = new Basket(1L, "food");
    private final Basket basket1 = new Basket(1L, "music");

    private final List<Basket> baskets = List.of(basket, basket1);

    @Test
    public void createTest() {
        BasketDTO basketDTO = this.mapToDTO(basket);
        when(this.basketService.create(basketDTO)).thenReturn(basketDTO);
        ResponseEntity<BasketDTO> expected = new ResponseEntity<>(basketDTO, HttpStatus.CREATED);
        ResponseEntity<BasketDTO> actual = this.basketController.create(basketDTO);
        assertEquals(expected, actual);
        verify(this.basketService, times(1)).create(basketDTO);
    }

    @Test
    public void deleteTest() {
        BasketDTO basketDTO = this.mapToDTO(basket);
        ResponseEntity<BasketDTO> deleted = new ResponseEntity<>(HttpStatus.NO_CONTENT);
        when(this.basketService.delete(basketDTO)).thenReturn(true);
        ResponseEntity<BasketDTO> actual = this.basketController.delete(basketDTO);
        assertEquals(deleted, actual);

        ResponseEntity<BasketDTO> notDeleted = new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        when(this.basketService.delete(basketDTO)).thenReturn(false);
        ResponseEntity<BasketDTO> actual1 = this.basketController.delete(basketDTO);
        assertEquals(notDeleted, actual1);

        verify(this.basketService, times(2)).delete(basketDTO);
    }

    @Test
    public void deleteByIDTest() {
        Long targetID = 1L;
        ResponseEntity<BasketDTO> deleted = new ResponseEntity<>(HttpStatus.NO_CONTENT);
        when(this.basketService.deleteByID(targetID)).thenReturn(true);
        ResponseEntity<BasketDTO> actual = this.basketController.deleteByID(targetID);
        assertEquals(deleted, actual);

        ResponseEntity<BasketDTO> notDeleted = new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        when(this.basketService.deleteByID(targetID)).thenReturn(false);
        ResponseEntity<BasketDTO> actual1 = this.basketController.deleteByID(targetID);
        assertEquals(notDeleted, actual1);

        verify(this.basketService, times(2)).deleteByID(targetID);
    }

    @Test
    public void readAllTest() {
        List<BasketDTO> basketDTOS = baskets.stream().map(this::mapToDTO).collect(Collectors.toList());
        when(this.basketService.readAll()).thenReturn(basketDTOS);
        ResponseEntity<List<BasketDTO>> expected = ResponseEntity.ok(basketDTOS);
        ResponseEntity<List<BasketDTO>> actual = this.basketController.readAll();
        assertEquals(expected, actual);

        verify(this.basketService, times(1)).readAll();
    }

    @Test
    public void readByIDTest() {
        Long targetID = 1L;
        BasketDTO basketDTO = this.mapToDTO(basket);
        when(this.basketService.read(targetID)).thenReturn(basketDTO);
        ResponseEntity<BasketDTO> expected = ResponseEntity.ok(basketDTO);
        ResponseEntity<BasketDTO> actual = this.basketController.read(targetID);
        assertEquals(expected, actual);

        verify(this.basketService, times(1)).read(targetID);
    }

    @Test
    public void updateTest() {
        Long targetID = 1L;
        BasketDTO basketDTO = this.mapToDTO(basket);
        BasketDTO newBasketDTO = new BasketDTO();
        newBasketDTO.setBId(1L);
        newBasketDTO.setName("music");
        when(this.basketService.updateByID(basketDTO, targetID)).thenReturn(newBasketDTO);
        ResponseEntity<BasketDTO> expected = new ResponseEntity<>(newBasketDTO, HttpStatus.ACCEPTED);
        ResponseEntity<BasketDTO> actual = this.basketController.update(targetID, basketDTO);
        assertEquals(expected, actual);

        verify(this.basketService, times(1)).updateByID(basketDTO, targetID);
    }
}
