package com.qa.qatdl.service;

import com.qa.qatdl.dto.BasketDTO;
import com.qa.qatdl.persistance.domain.Basket;
import com.qa.qatdl.persistance.repo.BasketRepo;
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

    public BasketDTO create(Basket basket) {
        return this.mapToDTO(this.basketRepo.save(basket));
    }

    public boolean delete(Basket basket) {
        this.basketRepo.delete(basket);
        return !this.basketRepo.existsById(basket.getB_id());
    }

    public boolean deleteByID(Long b_id) {
        this.basketRepo.deleteById(b_id);
        return !this.basketRepo.existsById(b_id);
    }

}
