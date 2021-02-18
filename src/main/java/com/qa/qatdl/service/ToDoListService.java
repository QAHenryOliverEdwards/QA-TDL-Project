package com.qa.qatdl.service;

import com.qa.qatdl.dto.ToDoListDTO;
import com.qa.qatdl.persistance.domain.ToDoList;
import com.qa.qatdl.persistance.repo.ToDoListRepo;
import com.qa.qatdl.utils.BasketUtil;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ToDoListService {

    private final ToDoListRepo toDoListRepo;

    private final ModelMapper modelMapper;

    private ToDoListDTO mapToDTO(ToDoList toDoList) {
        return this.modelMapper.map(toDoList, ToDoListDTO.class);
    }

    private ToDoList mapToEntity(ToDoListDTO toDoListDTO) {return this.modelMapper.map(toDoListDTO, ToDoList.class);}

    public ToDoListDTO create(ToDoListDTO toDoListDTO) {
        return this.mapToDTO(this.toDoListRepo.save(this.mapToEntity(toDoListDTO)));
    }

    public boolean delete(ToDoListDTO toDoListDTO) {
        this.toDoListRepo.delete(this.mapToEntity(toDoListDTO));
        return !this.toDoListRepo.existsById(mapToEntity(toDoListDTO).getTdlId());
    }

    public boolean deleteByID(Long tdlId) {
        this.toDoListRepo.deleteById(tdlId);
        return !this.toDoListRepo.existsById(tdlId);
    }

    public List<ToDoListDTO> readAll() {
        return this.toDoListRepo.findAll().stream().map(this::mapToDTO).collect(Collectors.toList());
    }

    public ToDoListDTO read(Long tdlId) {
        return this.mapToDTO(this.toDoListRepo.findById(tdlId).orElseThrow());
    }

    public ToDoListDTO updateByID(ToDoListDTO toDoListDTO, Long tdlId) {
        ToDoList toDoList = this.toDoListRepo.findById(tdlId).orElseThrow();
        toDoList.setName(toDoListDTO.getName());
        BasketUtil.mergeNotNull(toDoListDTO, toDoList);
        return this.mapToDTO(this.toDoListRepo.save(toDoList));
    }

}


