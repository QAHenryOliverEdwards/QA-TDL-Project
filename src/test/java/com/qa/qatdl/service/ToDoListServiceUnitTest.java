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
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
public class ToDoListServiceUnitTest {

    @Autowired
    private ToDoListService toDoListService;

    @MockBean
    private ToDoListRepo toDoListRepo;

    @Autowired
    private ModelMapper modelMapper;

    private ToDoListDTO mapToDTO(ToDoList toDoList) {
        return this.modelMapper.map(toDoList, ToDoListDTO.class);
    }

    private final ToDoList toDoList = new ToDoList(1L, "General");
    private final ToDoList toDoList1 = new ToDoList(2L, "Music To Buy");
    private final List<ToDoList> toDoLists = List.of(toDoList, toDoList1);

    @Test
    public void createToDoListTest() {
        ToDoListDTO toDoListDTO = this.mapToDTO(toDoList);

        when(this.toDoListRepo.save(toDoList)).thenReturn(toDoList);

        ToDoListDTO expected = toDoListDTO;
        ToDoListDTO actual = this.toDoListService.create(toDoListDTO);

        assertEquals(expected, actual);

        verify(this.toDoListRepo, times(1)).save(toDoList);
    }

    @Test
    public void deleteToDoListTest() {
        ToDoListDTO toDoListDTO = this.mapToDTO(toDoList);

        when(this.toDoListRepo.existsById(toDoListDTO.getTdlId())).thenReturn(false);

        boolean actual = this.toDoListService.delete(toDoListDTO);

        assertTrue(actual);

        when(!this.toDoListRepo.existsById(toDoListDTO.getTdlId())).thenReturn(true);

        boolean actual1 = this.toDoListService.delete(toDoListDTO);

        assertFalse(actual1);

        verify(this.toDoListRepo, times(2)).existsById(toDoListDTO.getTdlId());
    }

    @Test
    public void deleteToDoListByIDTest() {
        Long targetID = 1L;

        when(this.toDoListRepo.existsById(targetID)).thenReturn(false);

        boolean actual = this.toDoListService.deleteByID(targetID);

        assertTrue(actual);

        when(!this.toDoListRepo.existsById(targetID)).thenReturn(true);

        boolean actual1 = this.toDoListService.deleteByID(targetID);

        assertFalse(actual1);

        verify(this.toDoListRepo, times(2)).existsById(targetID);
    }

    @Test
    public void readAllToDoListTest() {
        List<ToDoListDTO> toDoListDTOS = new ArrayList<>();
        toDoListDTOS.add(this.mapToDTO(toDoList));
        toDoListDTOS.add(this.mapToDTO(toDoList1));

        when(this.toDoListRepo.findAll()).thenReturn(toDoLists);

        List<ToDoListDTO> expected = toDoListDTOS;
        List<ToDoListDTO> actual = this.toDoListService.readAll();

        assertEquals(expected, actual);

        verify(this.toDoListRepo, times(1)).findAll();

    }

    @Test
    public void readByIDToDoListTest() {
        ToDoListDTO toDoListDTO = this.mapToDTO(toDoList);

        Long targetID = 1L;

        when(this.toDoListRepo.findById(targetID)).thenReturn(Optional.of(toDoList));

        ToDoListDTO expected = toDoListDTO;
        ToDoListDTO actual = this.toDoListService.read(targetID);

        assertEquals(expected, actual);

        verify(this.toDoListRepo, Mockito.times(1)).findById(targetID);
    }

    @Test
    public void updateByIDTest() {
        ToDoList toDoList2 = new ToDoList(1L, "To Do This Week");
        ToDoListDTO toDoListDTO = this.mapToDTO(toDoList);
        ToDoListDTO toDoListDTO1 = this.mapToDTO(toDoList);
        toDoListDTO1.setName("To Do This Week");

        Long targetID = 1L;

        when(this.toDoListRepo.findById(targetID)).thenReturn(Optional.of(toDoList));
        when(this.toDoListRepo.save(toDoList)).thenReturn(toDoList2);

        ToDoListDTO expected = toDoListDTO1;
        ToDoListDTO actual = this.toDoListService.updateByID(toDoListDTO, targetID);

        assertEquals(expected, actual);

        verify(this.toDoListRepo, times(1)).findById(targetID);
        verify(this.toDoListRepo, times(1)).save(toDoList);
    }

}
