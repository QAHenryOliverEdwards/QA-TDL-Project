package com.qa.qatdl.service;

import com.qa.qatdl.dto.BasketDTO;
import com.qa.qatdl.persistance.domain.Basket;
import com.qa.qatdl.persistance.repo.BasketRepo;
import com.qa.qatdl.utils.BasketUtil;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class BasketService {

    private final BasketRepo basketRepo;

    private final ModelMapper modelMapper;

    private BasketDTO mapToDTO(Basket basket) {
        return this.modelMapper.map(basket, BasketDTO.class);
    }

    private Basket mapToEntity(BasketDTO basketDTO) {return this.modelMapper.map(basketDTO, Basket.class);}

    public BasketDTO create(BasketDTO basketDTO) {
        return this.mapToDTO(this.basketRepo.save(this.mapToEntity(basketDTO)));
    }

    public boolean delete(BasketDTO basketDTO) {
        this.basketRepo.delete(this.mapToEntity(basketDTO));
        return !this.basketRepo.existsById(mapToEntity(basketDTO).getBId());
    }

    public boolean deleteByID(Long bId) {
        this.basketRepo.deleteById(bId);
        return !this.basketRepo.existsById(bId);
    }

    public List<BasketDTO> readAll() {
        return this.basketRepo.findAll().stream().map(this::mapToDTO).collect(Collectors.toList());
    }

    public BasketDTO read(Long bId) {
        return this.mapToDTO(this.basketRepo.findById(bId).orElseThrow());
    }

    public BasketDTO updateByID(BasketDTO basketDTO, Long bId) {
        Basket basket = this.basketRepo.findById(bId).orElseThrow();
        basket.setName(basketDTO.getName());
        BasketUtil.mergeNotNull(basketDTO, basket);
        return this.mapToDTO(this.basketRepo.save(basket));
    }

}


