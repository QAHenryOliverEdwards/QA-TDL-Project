package com.qa.qatdl.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.qa.qatdl.dto.TaskDTO;
import com.qa.qatdl.persistance.domain.Task;
import com.qa.qatdl.persistance.domain.ToDoList;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.List;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@Sql(scripts = "classpath:test-drop-all.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
@Sql(scripts = {"classpath:test-schema.sql", "classpath:test-data.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@ActiveProfiles("dev")
public class TaskControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private ModelMapper modelMapper;

    private TaskDTO mapToDTO(Task task) {
        return this.modelMapper.map(task, TaskDTO.class);
    }

    private final Task task = new Task(1L, "Chicken", "9.99", new ToDoList(1L));
    private final Task task1 = new Task(2L, "Ham", "12.99", new ToDoList(2L));

    @Test
    public void createTaskTest() throws Exception {
        Task newTask = new Task(3L, "Lettuce", "0.99");
        String newTaskDTOJSON = this.objectMapper.writeValueAsString(this.mapToDTO(newTask));
        Long targetID = 1L;
        this.mockMvc
                .perform(post("/task/create/{id}", targetID)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(newTaskDTOJSON))
                .andExpect(status().isCreated())
                .andExpect(content().json(newTaskDTOJSON));
    }

    @Test
    public void deleteTaskTest() throws Exception {
        String toDoListDTOJSON = this.objectMapper.writeValueAsString(this.mapToDTO(task));
        this.mockMvc
                .perform(delete("/task/delete")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(toDoListDTOJSON))
                .andExpect(status().isNoContent());
    }

    @Test
    public void deleteTaskByIDTest() throws Exception {
        Long targetID = 1L;
        this.mockMvc
                .perform(delete("/task/delete/{id}", targetID)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }

    @Test
    public void readAllTaskTest() throws Exception {
        String taskDTOJSON = this.objectMapper.writeValueAsString(this.mapToDTO(task));
        String taskDTOJSON1 = this.objectMapper.writeValueAsString(this.mapToDTO(task1));
        String expected = List.of(taskDTOJSON, taskDTOJSON1).toString().replaceAll(" ", "");
        MvcResult result = this.mockMvc
                .perform(get("/task/read")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        String actual = result.getResponse().getContentAsString().replaceAll(" ", "");
        assertEquals(expected, actual);
    }

    @Test
    public void readByIDTaskTest() throws Exception {
        Long targetID = 1L;
        String taskDTOJSON = this.objectMapper.writeValueAsString(this.mapToDTO(task));
        MvcResult result = this.mockMvc
                .perform(get("/task/read/{id}", targetID)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        String expected = taskDTOJSON.replaceAll(" ", "");
        String actual = result.getResponse().getContentAsString().replaceAll(" ", "");
        assertEquals(expected, actual);
    }

    @Test
    public void updateTaskTest() throws Exception {
        Task task2 = new Task(1L, "Turkey", "9.99", new ToDoList(1L));
        String taskDTO2JSON = this.objectMapper.writeValueAsString(this.mapToDTO(task2));
        Long targetID = 1L;
        MvcResult result = this.mockMvc
                .perform(put("/task/update/{id}", targetID)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(taskDTO2JSON))
                .andExpect(status().isAccepted())
                .andReturn();

        String expected = taskDTO2JSON.replaceAll(" ", "");
        String actual = result.getResponse().getContentAsString().replaceAll(" ", "");
        assertEquals(expected, actual);
    }
}
