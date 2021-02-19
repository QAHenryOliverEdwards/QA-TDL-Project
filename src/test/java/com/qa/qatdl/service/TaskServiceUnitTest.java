package com.qa.qatdl.service;

import com.qa.qatdl.dto.TaskDTO;
import com.qa.qatdl.persistance.domain.Task;
import com.qa.qatdl.persistance.domain.ToDoList;
import com.qa.qatdl.persistance.repo.TaskRepo;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
public class TaskServiceUnitTest {

    @Autowired
    private TaskService taskService;

    @MockBean
    private TaskRepo taskRepo;

    @Autowired
    private ModelMapper modelMapper;

    private TaskDTO mapToDTO(Task task) {
        return this.modelMapper.map(task, TaskDTO.class);
    }

    private final Task task = new Task(1L,"Chicken", "9.99", new ToDoList(1L));
    private final Task task1 = new Task(2L, "Ham", "12.99", new ToDoList(1L));
    private final List<Task> tasks = List.of(task, task1);

    @Test
    public void createTaskTest() {
        TaskDTO taskDTO = this.mapToDTO(task);

        Long targetID = 1L;

        when(this.taskRepo.save(task)).thenReturn(task);

        TaskDTO expected = taskDTO;
        TaskDTO actual = this.taskService.create(taskDTO, targetID);

        assertEquals(expected, actual);

        verify(this.taskRepo, times(1)).save(task);
    }

    @Test
    public void deleteTaskTest() {
        TaskDTO taskDTO = this.mapToDTO(task);

        when(this.taskRepo.existsById(taskDTO.getTId())).thenReturn(false);

        boolean actual = this.taskService.delete(taskDTO);

        assertTrue(actual);

        when(!this.taskRepo.existsById(taskDTO.getTId())).thenReturn(true);

        boolean actual1 = this.taskService.delete(taskDTO);

        assertFalse(actual1);

        verify(this.taskRepo, times(2)).existsById(taskDTO.getTId());

    }

    @Test
    public void deleteTaskByIDTest() {
        Long targetID = 1L;

        when(this.taskRepo.existsById(targetID)).thenReturn(false);

        boolean actual = this.taskService.deleteByID(targetID);

        assertTrue(actual);

        when(!this.taskRepo.existsById(targetID)).thenReturn(true);

        boolean actual1 = this.taskService.deleteByID(targetID);

        assertFalse(actual1);

        verify(this.taskRepo, times(2)).deleteById(targetID);
    }

    @Test
    public void updateByIDTest() {
        Task task2 = new Task(1L, "Turkey", "9.99", new ToDoList(1L));
        TaskDTO taskDTO = this.mapToDTO(task);
        TaskDTO taskDTO1 = this.mapToDTO(task);
        taskDTO1.setName("Turkey");

        Long targetID = 1L;

        when(this.taskRepo.findById(targetID)).thenReturn(Optional.of(task));
        when(this.taskRepo.save(task)).thenReturn(task2);

        TaskDTO expected = taskDTO1;
        TaskDTO actual = this.taskService.updateByID(taskDTO, targetID);

        assertEquals(expected, actual);

        verify(this.taskRepo, times(1)).findById(targetID);
        verify(this.taskRepo, times(1)).save(task);
    }

    @Test
    public void readAllTest() {
        List<TaskDTO> taskDTOS = new ArrayList<>();
        taskDTOS.add(this.mapToDTO(task));
        taskDTOS.add(this.mapToDTO(task1));

        when(this.taskRepo.findAll()).thenReturn(tasks);

        List<TaskDTO> expected = taskDTOS;
        List<TaskDTO> actual = this.taskService.readAll();

        assertEquals(expected, actual);

        verify(this.taskRepo, times(1)).findAll();
    }

    @Test
    public void readByIDTest() {
        TaskDTO taskDTO = this.mapToDTO(task);

        Long targetID = 1L;

        when(this.taskRepo.findById(targetID)).thenReturn(Optional.of(task));

        TaskDTO expected = taskDTO;
        TaskDTO actual = this.taskService.read(targetID);

        assertEquals(expected, actual);

        verify(this.taskRepo, times(1)).findById(targetID);
    }
}
