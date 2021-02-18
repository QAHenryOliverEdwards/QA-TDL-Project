package com.qa.qatdl.controller;

import com.qa.qatdl.dto.ToDoListDTO;
import com.qa.qatdl.persistance.domain.ToDoList;
import com.qa.qatdl.service.ToDoListService;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@SpringBootTest
@Disabled
public class ToDoListControllerUnitTest {

    @Autowired
    ToDoListController toDoListController;

    @MockBean
    ToDoListService toDoListService;

    @Autowired
    ModelMapper modelMapper;

    private ToDoListDTO mapToDTO(ToDoList toDoList) {
        return this.modelMapper.map(toDoList, ToDoListDTO.class);
    }

    private final ToDoList toDoList = new ToDoList(1L, "food");
    private final ToDoList toDoList1 = new ToDoList(1L, "music");

    private final List<ToDoList> lists = java.util.List.of(toDoList, toDoList1);

    @Test
    public void createTest() {
        ToDoListDTO toDoListDTO = this.mapToDTO(toDoList);
        when(this.toDoListService.create(toDoListDTO)).thenReturn(toDoListDTO);
        ResponseEntity<ToDoListDTO> expected = new ResponseEntity<>(toDoListDTO, HttpStatus.CREATED);
        ResponseEntity<ToDoListDTO> actual = this.toDoListController.create(toDoListDTO);
        assertEquals(expected, actual);
        verify(this.toDoListService, times(1)).create(toDoListDTO);
    }

    @Test
    public void deleteTest() {
        ToDoListDTO toDoListDTO = this.mapToDTO(toDoList);
        ResponseEntity<ToDoListDTO> deleted = new ResponseEntity<>(HttpStatus.NO_CONTENT);
        when(this.toDoListService.delete(toDoListDTO)).thenReturn(true);
        ResponseEntity<ToDoListDTO> actual = this.toDoListController.delete(toDoListDTO);
        assertEquals(deleted, actual);

        ResponseEntity<ToDoListDTO> notDeleted = new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        when(this.toDoListService.delete(toDoListDTO)).thenReturn(false);
        ResponseEntity<ToDoListDTO> actual1 = this.toDoListController.delete(toDoListDTO);
        assertEquals(notDeleted, actual1);

        verify(this.toDoListService, times(2)).delete(toDoListDTO);
    }

    @Test
    public void deleteByIDTest() {
        Long targetID = 1L;
        ResponseEntity<ToDoListDTO> deleted = new ResponseEntity<>(HttpStatus.NO_CONTENT);
        when(this.toDoListService.deleteByID(targetID)).thenReturn(true);
        ResponseEntity<ToDoListDTO> actual = this.toDoListController.deleteByID(targetID);
        assertEquals(deleted, actual);

        ResponseEntity<ToDoListDTO> notDeleted = new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        when(this.toDoListService.deleteByID(targetID)).thenReturn(false);
        ResponseEntity<ToDoListDTO> actual1 = this.toDoListController.deleteByID(targetID);
        assertEquals(notDeleted, actual1);

        verify(this.toDoListService, times(2)).deleteByID(targetID);
    }

    @Test
    public void readAllTest() {
        List<ToDoListDTO> listDTOS = lists.stream().map(this::mapToDTO).collect(Collectors.toList());
        when(this.toDoListService.readAll()).thenReturn(listDTOS);
        ResponseEntity<List<ToDoListDTO>> expected = ResponseEntity.ok(listDTOS);
        ResponseEntity<List<ToDoListDTO>> actual = this.toDoListController.readAll();
        assertEquals(expected, actual);

        verify(this.toDoListService, times(1)).readAll();
    }

    @Test
    public void readByIDTest() {
        Long targetID = 1L;
        ToDoListDTO toDoListDTO = this.mapToDTO(toDoList);
        when(this.toDoListService.read(targetID)).thenReturn(toDoListDTO);
        ResponseEntity<ToDoListDTO> expected = ResponseEntity.ok(toDoListDTO);
        ResponseEntity<ToDoListDTO> actual = this.toDoListController.read(targetID);
        assertEquals(expected, actual);

        verify(this.toDoListService, times(1)).read(targetID);
    }

    @Test
    public void updateTest() {
        Long targetID = 1L;
        ToDoListDTO toDoListDTO = this.mapToDTO(toDoList);
        ToDoListDTO newToDoListDTO = new ToDoListDTO();
        newToDoListDTO.setTdlId(1L);
        newToDoListDTO.setName("music");
        when(this.toDoListService.updateByID(toDoListDTO, targetID)).thenReturn(newToDoListDTO);
        ResponseEntity<ToDoListDTO> expected = new ResponseEntity<>(newToDoListDTO, HttpStatus.ACCEPTED);
        ResponseEntity<ToDoListDTO> actual = this.toDoListController.update(targetID, toDoListDTO);
        assertEquals(expected, actual);

        verify(this.toDoListService, times(1)).updateByID(toDoListDTO, targetID);
    }
}
