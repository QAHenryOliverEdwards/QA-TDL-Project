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

    final private BasketRepo basketRepo;

    final private ModelMapper modelMapper;

    private BasketDTO mapToDTO(Basket basket) {
        return this.modelMapper.map(basket, BasketDTO.class);
    }

    private Basket mapToEntity(BasketDTO basketDTO) {return this.modelMapper.map(basketDTO, Basket.class);}

    public BasketDTO create(BasketDTO basketDTO) {
        System.out.println(basketDTO);
        System.out.println(this.mapToEntity(basketDTO));
        return this.mapToDTO(this.basketRepo.save(this.mapToEntity(basketDTO)));
    }

    public boolean delete(BasketDTO basketDTO) {
        this.basketRepo.delete(this.mapToEntity(basketDTO));
        return !this.basketRepo.existsById(mapToEntity(basketDTO).getB_id());
    }

    public boolean deleteByID(Long b_id) {
        this.basketRepo.deleteById(b_id);
        return !this.basketRepo.existsById(b_id);
    }

    public List<BasketDTO> readAll() {
        return this.basketRepo.findAll().stream().map(this::mapToDTO).collect(Collectors.toList());
    }

    public BasketDTO read(Long b_id) {
        return this.mapToDTO(this.basketRepo.findById(b_id).orElseThrow());
    }

    public BasketDTO updateByID(BasketDTO basketDTO, Long b_id) {
        Basket basket = this.basketRepo.findById(b_id).orElseThrow();
        basket.setName(basketDTO.getName());
        BasketUtil.mergeNotNull(basketDTO, basket);
        return this.mapToDTO(this.basketRepo.save(basket));
    }

}


