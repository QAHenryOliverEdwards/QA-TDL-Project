package com.qa.qatdl.service;

import com.qa.qatdl.dto.BasketDTO;
import com.qa.qatdl.persistance.domain.Basket;
import com.qa.qatdl.persistance.repo.BasketRepo;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

@SpringBootTest
public class BasketServiceUnitTest {

    @Autowired
    private BasketService basketService;

    @MockBean
    private BasketRepo basketRepo;

    @Autowired
    private ModelMapper modelMapper;

    private Basket mapToEntity(BasketDTO basketDTO) {return this.modelMapper.map(basketDTO, Basket.class);}


    @Test
    public void createBasketTest() {
        Basket basket = new Basket(1L, "food");
        BasketDTO basketDTO = new BasketDTO();
        basketDTO.setBId(1L);
        basketDTO.setName("food");
        basketDTO.setItemList(null);

        when(this.basketRepo.save(this.mapToEntity(basketDTO))).thenReturn(basket);

        BasketDTO actual = this.basketService.create(basketDTO);

        assertEquals(basketDTO, actual);

        verify(this.basketRepo, Mockito.times(1)).save(basket);
    }

    @Test
    public void deleteBasketTest() {
        Basket basket = new Basket(1L, "food");
        BasketDTO basketDTO = new BasketDTO();
        basketDTO.setBId(1L);
        basketDTO.setName("food");
        basketDTO.setItemList(null);

        when(this.basketRepo.existsById(mapToEntity(basketDTO).getBId())).thenReturn(false);

        boolean actual = this.basketService.delete(basketDTO);

        assertTrue(actual);

        verify(this.basketRepo, Mockito.times(1)).delete(basket);
    }

    @Test
    public void deleteBasketByIDTest() {
        Long idToDelete = 1L;

        when(this.basketRepo.existsById(idToDelete)).thenReturn(false);

        boolean actual = this.basketService.deleteByID(idToDelete);

        assertTrue(actual);

        verify(this.basketRepo, times(1)).existsById(idToDelete);
    }

    @Test
    public void readAllTest() {
        List<BasketDTO> basketDTOS = new ArrayList<>();
        BasketDTO basketDTO = new BasketDTO();
        basketDTO.setBId(1L);
        basketDTO.setName("food");
        basketDTO.setItemList(null);

        BasketDTO basketDTO1 = new BasketDTO();
        basketDTO1.setBId(2L);
        basketDTO1.setName("music");
        basketDTO1.setItemList(null);

        basketDTOS.add(basketDTO);
        basketDTOS.add(basketDTO1);

        List<Basket> baskets = new ArrayList<>();
        Basket basket = new Basket(1L, "food");
        Basket basket1 = new Basket(2L, "music");

        baskets.add(basket);
        baskets.add(basket1);

        when(this.basketRepo.findAll()).thenReturn(baskets);

        List<BasketDTO> actual = this.basketService.readAll();

        assertEquals(basketDTOS, actual);

        verify(this.basketRepo, Mockito.times(1)).findAll();

    }

    @Test
    public void readByIDBasketTest() {
        BasketDTO basketDTO = new BasketDTO();
        basketDTO.setBId(1L);
        basketDTO.setName("food");
        basketDTO.setItemList(null);

        Basket basket = new Basket(1L, "food");

        Long idToFind = 1L;

        when(this.basketRepo.findById(idToFind)).thenReturn(java.util.Optional.of(basket));

        BasketDTO actual = this.basketService.read(idToFind);

        assertEquals(basketDTO, actual);

        verify(this.basketRepo, Mockito.times(1)).findById(idToFind);
    }

    @Test
    public void updateByIDTest() {
        BasketDTO basketDTO = new BasketDTO();
        basketDTO.setBId(1L);
        basketDTO.setName("food");
        basketDTO.setItemList(null);

        BasketDTO newBasketDTO = new BasketDTO();
        newBasketDTO.setBId(1L);
        newBasketDTO.setName("music");
        newBasketDTO.setItemList(null);

        Basket basket = new Basket(1L, "food");
        Basket newBasket = new Basket(1L, "music");

        Long idToUpdate = 1L;

        when(this.basketRepo.findById(idToUpdate)).thenReturn(java.util.Optional.of(basket));
        when(this.basketRepo.save(basket)).thenReturn(newBasket);

        BasketDTO actual = this.basketService.updateByID(basketDTO, idToUpdate);

        assertEquals(newBasketDTO, actual);

        verify(this.basketRepo, times(1)).findById(idToUpdate);
        verify(this.basketRepo, times(1)).save(basket);
    }


}
