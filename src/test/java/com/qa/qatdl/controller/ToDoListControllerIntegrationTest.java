package com.qa.qatdl.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.qa.qatdl.dto.ToDoListDTO;
import com.qa.qatdl.persistance.domain.Task;
import com.qa.qatdl.persistance.domain.ToDoList;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.JsonPath;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@Sql(scripts = "classpath:test-drop-all.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
@Sql(scripts = {"classpath:test-schema.sql", "classpath:test-data.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@ActiveProfiles("dev")
class ToDoListControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private ModelMapper modelMapper;


    private ToDoListDTO mapToDTO(ToDoList toDoList) {
        return this.modelMapper.map(toDoList, ToDoListDTO.class);
    }

    private final ToDoList toDoList = new ToDoList(1L, "General");
    private final ToDoList toDoList1 = new ToDoList(2L, "Music To Buy");

    private final Task task = new Task(1L, "Chicken", "9.99", new ToDoList(1L));
    private final Task task1 = new Task(2L, "Ham", "12.99", new ToDoList(2L));

    @Test
    void createToDoListTest() throws Exception {
        String toDoListDTOJSON = this.objectMapper.writeValueAsString(this.mapToDTO(toDoList));
        this.mockMvc
                .perform(post("/to-do-list/create")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(toDoListDTOJSON))
                .andExpect(status().isCreated())
                .andExpect(content().json(toDoListDTOJSON));
    }

    @Test
    void deleteToDoListTest() throws Exception {
        String toDoListDTOJSON = this.objectMapper.writeValueAsString(this.mapToDTO(toDoList));
        this.mockMvc
                .perform(delete("/to-do-list/delete")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(toDoListDTOJSON))
                .andExpect(status().isNoContent());
    }

    @Test
    void deleteToDoListByIDTest() throws Exception {
        Long targetID = 1L;
        this.mockMvc
                .perform(delete("/to-do-list/delete/{id}", targetID)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }

    @Test
    void readAllToDoListTest() throws Exception {
        List<Task> tasks = List.of(task, task1);
        toDoList.setTaskList(tasks);
        toDoList1.setTaskList(new ArrayList<>());
        String toDoListDTOJSON = this.objectMapper.writeValueAsString(this.mapToDTO(toDoList));
        String toDoListDTOJSON1 = this.objectMapper.writeValueAsString(this.mapToDTO(toDoList1));
        String expected = List.of(toDoListDTOJSON, toDoListDTOJSON1).toString().replaceAll(" ", "");
        MvcResult result = this.mockMvc
                .perform(get("/to-do-list/read")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        String actual = result.getResponse().getContentAsString().replaceAll(" ", "");
        assertEquals(expected, actual);
    }

    @Test
    void readByIDToDoListTest() throws Exception {
        Long targetID = 1L;
        List<Task> tasks = List.of(task, task1);
        toDoList.setTaskList(tasks);
        String toDoListDTOJSON = this.objectMapper.writeValueAsString(this.mapToDTO(toDoList));
        MvcResult result = this.mockMvc
                .perform(get("/to-do-list/read/{id}", targetID)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        String expected = toDoListDTOJSON.replaceAll(" ", "");
        String actual = result.getResponse().getContentAsString().replaceAll(" ", "");
        assertEquals(expected, actual);
    }

    @Test
    void updateToDoListTest() throws Exception {
        List<Task> tasks = List.of(task, task1);
        ToDoList toDoList2 = new ToDoList(1L, "New List");
        toDoList2.setTaskList(tasks);
        String toDoListDTO2JSON = this.objectMapper.writeValueAsString(this.mapToDTO(toDoList2));
        Long targetID = 1L;
        MvcResult result = this.mockMvc
                .perform(put("/to-do-list/update/{id}", targetID)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(toDoListDTO2JSON))
                .andExpect(status().isAccepted())
                .andReturn();

        String expected = toDoListDTO2JSON.replaceAll(" ", "");
        String actual = result.getResponse().getContentAsString().replaceAll(" ", "");
        assertEquals(expected, actual);
    }
}
