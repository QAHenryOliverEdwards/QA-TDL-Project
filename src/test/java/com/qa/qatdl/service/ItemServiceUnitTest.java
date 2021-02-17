package com.qa.qatdl.service;

import com.qa.qatdl.dto.BasketDTO;
import com.qa.qatdl.dto.ItemDTO;
import com.qa.qatdl.persistance.domain.Basket;
import com.qa.qatdl.persistance.domain.Item;
import com.qa.qatdl.persistance.repo.BasketRepo;
import com.qa.qatdl.persistance.repo.ItemRepo;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

@SpringBootTest
public class ItemServiceUnitTest {

    @Autowired
    private ItemService itemService;

    @MockBean
    private ItemRepo itemRepo;

    @Autowired
    private ModelMapper modelMapper;

    private Item mapToEntity(ItemDTO itemDTO) {return this.modelMapper.map(itemDTO, Item.class);}

    @Test
    public void createItemTest() {
        ItemDTO itemDTO = new ItemDTO();
        itemDTO.setIId(1L);
        itemDTO.setName("chicken");
        itemDTO.setPrice(9.99f);

        Long targetBasketID = 1L;

        Item item = new Item(1L, "chicken", 9.99f, new Basket(1L));

        when(this.itemRepo.save(item)).thenReturn(item);

        ItemDTO actual = this.itemService.create(itemDTO, targetBasketID);

        assertEquals(itemDTO, actual);

        verify(this.itemRepo, times(1)).save(item);
    }

    @Test
    public void deleteItemTest() {
        ItemDTO itemDTO = new ItemDTO();
        itemDTO.setIId(1L);
        itemDTO.setName("chicken");

        when(this.itemRepo.existsById(itemDTO.getIId())).thenReturn(false);

        boolean actual = this.itemService.delete(itemDTO);

        assertTrue(actual);

        verify(this.itemRepo, times(1)).existsById(itemDTO.getIId());
    }

    @Test
    public void deleteItemByIDTest() {
        Long idToDelete = 1L;

        when(this.itemRepo.existsById(idToDelete)).thenReturn(false);

        boolean actual = this.itemService.deleteByID(idToDelete);

        assertTrue(actual);

        verify(this.itemRepo, times(1)).existsById(idToDelete);
    }

    @Test
    public void updateByIDTest() {
        ItemDTO itemDTO = new ItemDTO();
        itemDTO.setIId(1L);
        itemDTO.setName("chicken");
        itemDTO.setPrice(9.99f);

        ItemDTO newItemDTO = new ItemDTO();
        newItemDTO.setIId(1L);
        newItemDTO.setName("turkey");
        newItemDTO.setPrice(9.99f);

        Item item = new Item(1L, "chicken", 9.99f, null);
        Item newItem = new Item(1L, "turkey", 9.99f, null);

        Long targetItemID = 1L;

        when(this.itemRepo.findById(targetItemID)).thenReturn(java.util.Optional.of(item));
        when(this.itemRepo.save(item)).thenReturn(newItem);

        ItemDTO actual = this.itemService.updateByID(itemDTO, targetItemID);

        assertEquals(newItemDTO, actual);

        verify(this.itemRepo, times(1)).findById(targetItemID);
        verify(this.itemRepo, times(1)).save(item);
    }

    @Test
    public void readAllTest() {
        List<ItemDTO> itemDTOS = new ArrayList<>();
        ItemDTO itemDTO = new ItemDTO();
        itemDTO.setIId(1L);
        itemDTO.setName("chicken");
        itemDTO.setPrice(9.99f);

        ItemDTO itemDTO1 = new ItemDTO();
        itemDTO1.setIId(2L);
        itemDTO1.setName("ham");
        itemDTO1.setPrice(12.99f);

        itemDTOS.add(itemDTO);
        itemDTOS.add(itemDTO1);

        List<Item> items = new ArrayList<>();
        Item item = new Item(1L, "chicken", 9.99f, null);
        Item item1 = new Item(2L, "ham", 12.99f, null);

        items.add(item);
        items.add(item1);

        when(this.itemRepo.findAll()).thenReturn(items);

        List<ItemDTO> actual = this.itemService.readAll();

        assertEquals(itemDTOS, actual);

        verify(this.itemRepo, times(1)).findAll();
    }

    @Test
    public void readByIDTest() {
        ItemDTO itemDTO = new ItemDTO();
        itemDTO.setIId(1L);
        itemDTO.setName("chicken");
        itemDTO.setPrice(9.99f);

        Item item = new Item(1L, "chicken", 9.99f, null);

        Long idToFind = 1L;

        when(this.itemRepo.findById(idToFind)).thenReturn(java.util.Optional.of(item));

        ItemDTO actual = this.itemService.read(idToFind);

        assertEquals(itemDTO, actual);

        verify(this.itemRepo, times(1)).findById(idToFind);
    }

}
