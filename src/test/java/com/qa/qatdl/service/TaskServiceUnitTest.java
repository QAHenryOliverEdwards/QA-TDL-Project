package com.qa.qatdl.service;

import com.qa.qatdl.dto.TaskDTO;
import com.qa.qatdl.persistance.domain.ToDoList;
import com.qa.qatdl.persistance.domain.Task;
import com.qa.qatdl.persistance.repo.TaskRepo;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
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
public class TaskServiceUnitTest {

    @Autowired
    private TaskService taskService;

    @MockBean
    private TaskRepo taskRepo;

    @Test
    public void createTaskTest() {
        TaskDTO taskDTO = new TaskDTO();
        taskDTO.setTId(1L);
        taskDTO.setName("chicken");
        taskDTO.setDescription("get chicken");

        Long targetBasketID = 1L;

        Task task = new Task(1L, "chicken", "get chicken", new ToDoList(1L));

        when(this.taskRepo.save(task)).thenReturn(task);

        TaskDTO actual = this.taskService.create(taskDTO, targetBasketID);

        assertEquals(taskDTO, actual);

        verify(this.taskRepo, times(1)).save(task);
    }

    @Test
    public void deleteItemTest() {
        TaskDTO taskDTO = new TaskDTO();
        taskDTO.setTId(1L);
        taskDTO.setName("chicken");

        when(this.taskRepo.existsById(taskDTO.getTId())).thenReturn(false);

        boolean actual = this.taskService.delete(taskDTO);

        assertTrue(actual);

        verify(this.taskRepo, times(1)).existsById(taskDTO.getTId());
    }

    @Test
    public void deleteTaskByIDTest() {
        Long idToDelete = 1L;

        when(this.taskRepo.existsById(idToDelete)).thenReturn(false);

        boolean actual = this.taskService.deleteByID(idToDelete);

        assertTrue(actual);

        verify(this.taskRepo, times(1)).existsById(idToDelete);
    }

    @Test
    public void updateByIDTest() {
        TaskDTO taskDTO = new TaskDTO();
        taskDTO.setTId(1L);
        taskDTO.setName("chicken");
        taskDTO.setDescription("get chicken");

        TaskDTO newTaskDTO = new TaskDTO();
        newTaskDTO.setTId(1L);
        newTaskDTO.setName("turkey");
        newTaskDTO.setDescription("get chicken");

        Task task = new Task(1L, "chicken", "get chicken", null);
        Task newTask = new Task(1L, "turkey", "get chicken", null);

        Long targettaskID = 1L;

        when(this.taskRepo.findById(targettaskID)).thenReturn(java.util.Optional.of(task));
        when(this.taskRepo.save(task)).thenReturn(newTask);

        TaskDTO actual = this.taskService.updateByID(taskDTO, targettaskID);

        assertEquals(newTaskDTO, actual);

        verify(this.taskRepo, times(1)).findById(targettaskID);
        verify(this.taskRepo, times(1)).save(task);
    }

    @Test
    public void readAllTest() {
        List<TaskDTO> taskDTOS = new ArrayList<>();
        TaskDTO taskDTO = new TaskDTO();
        taskDTO.setTId(1L);
        taskDTO.setName("chicken");
        taskDTO.setDescription("get chicken");

        TaskDTO taskDTO1 = new TaskDTO();
        taskDTO1.setTId(2L);
        taskDTO1.setName("ham");
        taskDTO1.setDescription("get ham");

        taskDTOS.add(taskDTO);
        taskDTOS.add(taskDTO1);

        List<Task> tasks = new ArrayList<>();
        Task task = new Task(1L, "chicken", "get chicken", null);
        Task task1 = new Task(2L, "ham", "get ham", null);

        tasks.add(task);
        tasks.add(task1);

        when(this.taskRepo.findAll()).thenReturn(tasks);

        List<TaskDTO> actual = this.taskService.readAll();

        assertEquals(taskDTOS, actual);

        verify(this.taskRepo, times(1)).findAll();
    }

    @Test
    public void readByIDTest() {
        TaskDTO taskDTO = new TaskDTO();
        taskDTO.setTId(1L);
        taskDTO.setName("chicken");
        taskDTO.setDescription("get chicken");

        Task task = new Task(1L, "chicken", "get chicken", null);

        Long idToFind = 1L;

        when(this.taskRepo.findById(idToFind)).thenReturn(java.util.Optional.of(task));

        TaskDTO actual = this.taskService.read(idToFind);

        assertEquals(taskDTO, actual);

        verify(this.taskRepo, times(1)).findById(idToFind);
    }

}
