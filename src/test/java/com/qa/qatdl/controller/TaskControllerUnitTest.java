package com.qa.qatdl.controller;

import com.qa.qatdl.dto.TaskDTO;
import com.qa.qatdl.persistance.domain.Task;
import com.qa.qatdl.persistance.domain.ToDoList;
import com.qa.qatdl.service.TaskService;
import net.bytebuddy.utility.RandomString;
import org.apache.coyote.Response;
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
class TaskControllerUnitTest {

    @Autowired
    TaskController taskController;

    @MockBean
    TaskService taskService;

    @Autowired
    ModelMapper modelMapper;

    private TaskDTO mapToDTO(Task task) {
        return this.modelMapper.map(task, TaskDTO.class);
    }

    private final Task task = new Task(1L, "Chicken", "9.99", new ToDoList(1L));
    private final Task task1 = new Task(2L, "Ham", "12.99", new ToDoList(1L));
    private final List<Task> tasks = List.of(task, task1);

    @Test
    void createTaskTest() {
        TaskDTO taskDTO = this.mapToDTO(task);
        Long targetID = 1L;
        when(this.taskService.create(taskDTO, targetID)).thenReturn(taskDTO);
        ResponseEntity<TaskDTO> expected = new ResponseEntity<>(taskDTO, HttpStatus.CREATED);
        ResponseEntity<TaskDTO> actual = this.taskController.create(taskDTO, targetID);
        assertEquals(expected, actual);
        verify(this.taskService, times(1)).create(taskDTO, targetID);
    }

    @Test
    void deleteTaskTest() {
        TaskDTO taskDTO = this.mapToDTO(task);
        when(this.taskService.delete(taskDTO)).thenReturn(true);
        ResponseEntity<TaskDTO> deleted = new ResponseEntity<>(HttpStatus.NO_CONTENT);
        ResponseEntity<TaskDTO> actual = this.taskController.delete(taskDTO);
        assertEquals(deleted, actual);

        when(this.taskService.delete(taskDTO)).thenReturn(false);
        ResponseEntity<TaskDTO> notDeleted = new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        ResponseEntity<TaskDTO> actual1 = this.taskController.delete(taskDTO);
        assertEquals(notDeleted, actual1);

        verify(this.taskService, times(2)).delete(taskDTO);
    }

    @Test
    void deletedByIDTaskTest() {
        Long targetID = 1L;
        when(this.taskService.deleteByID(targetID)).thenReturn(true);
        ResponseEntity<TaskDTO> deleted = new ResponseEntity<>(HttpStatus.NO_CONTENT);
        ResponseEntity<TaskDTO> actual = this.taskController.deleteByID(targetID);
        assertEquals(deleted, actual);

        when(this.taskService.deleteByID(targetID)).thenReturn(false);
        ResponseEntity<TaskDTO> notDeleted = new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        ResponseEntity<TaskDTO> actual1 = this.taskController.deleteByID(targetID);
        assertEquals(notDeleted, actual1);

        verify(this.taskService, times(2)).deleteByID(targetID);
    }

    @Test
    void readAllTaskTest() {
        List<TaskDTO> taskDTOS = tasks.stream().map(this::mapToDTO).collect(Collectors.toList());
        when(this.taskService.readAll()).thenReturn(taskDTOS);
        ResponseEntity<List<TaskDTO>> expected = ResponseEntity.ok(taskDTOS);
        ResponseEntity<List<TaskDTO>> actual = this.taskController.readAll();
        assertEquals(expected, actual);

        verify(this.taskService, times(1)).readAll();
    }

    @Test
    void readByIDTaskTest() {
        Long targetID = 1L;
        TaskDTO taskDTO = this.mapToDTO(task);
        when(this.taskService.read(targetID)).thenReturn(taskDTO);
        ResponseEntity<TaskDTO> expected = ResponseEntity.ok(taskDTO);
        ResponseEntity<TaskDTO> actual = this.taskController.read(targetID);
        assertEquals(expected, actual);

        verify(this.taskService, times(1)).read(targetID);
    }

    @Test
    void updateTaskTest() {
        TaskDTO taskDTO = this.mapToDTO(task);
        TaskDTO taskDTO1 = this.mapToDTO(task);
        taskDTO1.setName("Turkey");

        Long targetID = 1L;

        when(this.taskService.updateByID(taskDTO, targetID)).thenReturn(taskDTO1);
        ResponseEntity<TaskDTO> expected = new ResponseEntity<>(taskDTO1, HttpStatus.ACCEPTED);
        ResponseEntity<TaskDTO> actual = this.taskController.updateByID(taskDTO, targetID);
        assertEquals(expected, actual);

        verify(this.taskService, times(1)).updateByID(taskDTO, targetID);
    }
}
