package com.qa.qatdl.service;

import com.qa.qatdl.dto.ItemDTO;
import com.qa.qatdl.persistance.domain.Basket;
import com.qa.qatdl.persistance.domain.Item;
import com.qa.qatdl.persistance.repo.ItemRepo;
import com.qa.qatdl.utils.ItemUtil;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ItemService {

    final ItemRepo itemRepo;

    final ModelMapper modelMapper;

    private ItemDTO mapToDTO(Item item) {
        return this.modelMapper.map(item, ItemDTO.class);
    }

    private Item mapToEntity(ItemDTO itemDTO) {return this.modelMapper.map(itemDTO, Item.class);}

    public ItemDTO create(ItemDTO itemDTO, Long basketID) {
        Item item = this.mapToEntity(itemDTO);
        item.setBasket(new Basket(basketID));
        return this.mapToDTO(this.itemRepo.save(item));
    }

    public boolean delete(ItemDTO itemDTO) {
        this.itemRepo.delete(this.mapToEntity(itemDTO));
        return !this.itemRepo.existsById(this.mapToEntity(itemDTO).getIId());
    }

    public boolean deleteByID(Long iId) {
        this.itemRepo.deleteById(iId);
        return !this.itemRepo.existsById(iId);
    }

    public ItemDTO updateByID(ItemDTO itemDTO, Long iId) {
        Item item = this.itemRepo.findById(iId).orElseThrow();
        itemDTO.setName(itemDTO.getName());
        ItemUtil.mergeNotNull(itemDTO, item);
        return mapToDTO(this.itemRepo.save(item));

    }

    public List<ItemDTO> readAll() {
        return this.itemRepo.findAll().stream().map(this::mapToDTO).collect(Collectors.toList());
    }

    public ItemDTO read(Long iId) {
        return this.mapToDTO(this.itemRepo.findById(iId).orElseThrow());
    }
}
