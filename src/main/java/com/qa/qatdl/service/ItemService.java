package com.qa.qatdl.service;

import com.qa.qatdl.dto.ItemDTO;
import com.qa.qatdl.persistance.domain.Item;
import com.qa.qatdl.persistance.repo.ItemRepo;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ItemService {

    final ItemRepo itemRepo;

    final ModelMapper modelMapper;

    private ItemDTO mapToDTO(Item item) {
        return this.modelMapper.map(item, ItemDTO.class);
    }

    public ItemDTO create(Item item) {
        return this.mapToDTO(this.itemRepo.save(item));
    }

    public boolean delete(Item item) {
        this.itemRepo.delete(item);
        return !this.itemRepo.existsById(item.getI_id());
    }

    public boolean deleteByID(Long i_id) {
        this.itemRepo.deleteById(i_id);
        return !this.itemRepo.existsById(i_id);
    }
}
