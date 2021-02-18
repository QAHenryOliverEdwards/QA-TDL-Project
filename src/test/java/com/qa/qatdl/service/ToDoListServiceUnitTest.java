package com.qa.qatdl.service;

import com.qa.qatdl.dto.ToDoListDTO;
import com.qa.qatdl.persistance.domain.ToDoList;
import com.qa.qatdl.persistance.repo.ToDoListRepo;
import org.junit.jupiter.api.Disabled;
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
@Disabled
public class ToDoListServiceUnitTest {

    @Autowired
    private ToDoListService toDoListService;

    @MockBean
    private ToDoListRepo toDoListRepo;

    @Autowired
    private ModelMapper modelMapper;

    private ToDoList mapToEntity(ToDoListDTO toDoListDTO) {return this.modelMapper.map(toDoListDTO, ToDoList.class);}


    @Test
    public void createBasketTest() {
        ToDoList toDoList = new ToDoList(1L, "food");
        ToDoListDTO toDoListDTO = new ToDoListDTO();
        toDoListDTO.setTdlId(1L);
        toDoListDTO.setName("food");
        toDoListDTO.setTaskList(null);

        when(this.toDoListRepo.save(this.mapToEntity(toDoListDTO))).thenReturn(toDoList);

        ToDoListDTO actual = this.toDoListService.create(toDoListDTO);

        assertEquals(toDoListDTO, actual);

        verify(this.toDoListRepo, Mockito.times(1)).save(toDoList);
    }

    @Test
    public void deleteBasketTest() {
        ToDoList toDoList = new ToDoList(1L, "food");
        ToDoListDTO toDoListDTO = new ToDoListDTO();
        toDoListDTO.setTdlId(1L);
        toDoListDTO.setName("food");
        toDoListDTO.setTaskList(null);

        when(this.toDoListRepo.existsById(mapToEntity(toDoListDTO).getTdlId())).thenReturn(false);

        boolean actual = this.toDoListService.delete(toDoListDTO);

        assertTrue(actual);

        verify(this.toDoListRepo, Mockito.times(1)).delete(toDoList);
    }

    @Test
    public void deleteToDoListByIDTest() {
        Long idToDelete = 1L;

        when(this.toDoListRepo.existsById(idToDelete)).thenReturn(false);

        boolean actual = this.toDoListService.deleteByID(idToDelete);

        assertTrue(actual);

        verify(this.toDoListRepo, times(1)).existsById(idToDelete);
    }

    @Test
    public void readAllTest() {
        List<ToDoListDTO> listDTOS = new ArrayList<>();
        ToDoListDTO toDoListDTO = new ToDoListDTO();
        toDoListDTO.setTdlId(1L);
        toDoListDTO.setName("food");
        toDoListDTO.setTaskList(null);

        ToDoListDTO toDoListDTO1 = new ToDoListDTO();
        toDoListDTO1.setTdlId(2L);
        toDoListDTO1.setName("music");
        toDoListDTO1.setTaskList(null);

        listDTOS.add(toDoListDTO);
        listDTOS.add(toDoListDTO1);

        List<ToDoList> toDoLists = new ArrayList<>();
        ToDoList toDoList = new ToDoList(1L, "food");
        ToDoList toDoList1 = new ToDoList(2L, "music");

        toDoLists.add(toDoList);
        toDoLists.add(toDoList1);

        when(this.toDoListRepo.findAll()).thenReturn(toDoLists);

        List<ToDoListDTO> actual = this.toDoListService.readAll();

        assertEquals(listDTOS, actual);

        verify(this.toDoListRepo, Mockito.times(1)).findAll();

    }

    @Test
    public void readByIDToDoListTest() {
        ToDoListDTO toDoListDTO = new ToDoListDTO();
        toDoListDTO.setTdlId(1L);
        toDoListDTO.setName("food");
        toDoListDTO.setTaskList(null);

        ToDoList toDoList = new ToDoList(1L, "food");

        Long idToFind = 1L;

        when(this.toDoListRepo.findById(idToFind)).thenReturn(java.util.Optional.of(toDoList));

        ToDoListDTO actual = this.toDoListService.read(idToFind);

        assertEquals(toDoListDTO, actual);

        verify(this.toDoListRepo, Mockito.times(1)).findById(idToFind);
    }

    @Test
    public void updateByIDTest() {
        ToDoListDTO toDoListDTO = new ToDoListDTO();
        toDoListDTO.setTdlId(1L);
        toDoListDTO.setName("food");
        toDoListDTO.setTaskList(null);

        ToDoListDTO newToDoListDTO = new ToDoListDTO();
        newToDoListDTO.setTdlId(1L);
        newToDoListDTO.setName("music");
        newToDoListDTO.setTaskList(null);

        ToDoList toDoList = new ToDoList(1L, "food");
        ToDoList newToDoList = new ToDoList(1L, "music");

        Long idToUpdate = 1L;

        when(this.toDoListRepo.findById(idToUpdate)).thenReturn(java.util.Optional.of(toDoList));
        when(this.toDoListRepo.save(toDoList)).thenReturn(newToDoList);

        ToDoListDTO actual = this.toDoListService.updateByID(toDoListDTO, idToUpdate);

        assertEquals(newToDoListDTO, actual);

        verify(this.toDoListRepo, times(1)).findById(idToUpdate);
        verify(this.toDoListRepo, times(1)).save(toDoList);
    }


}
