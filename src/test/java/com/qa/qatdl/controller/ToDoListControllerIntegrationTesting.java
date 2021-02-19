package com.qa.qatdl.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.qa.qatdl.dto.ToDoListDTO;
import com.qa.qatdl.persistance.domain.ToDoList;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@Sql(scripts = "classpath:test-drop-all.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
@Sql(scripts = {"classpath:test-schema.sql", "classpath:test-data.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@ActiveProfiles("dev")
public class ToDoListControllerIntegrationTesting {

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

    @Test
    public void createToDoListTest() throws Exception {
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
    public void deleteToDoListTest() throws Exception {
        String toDoListDTOJSON = this.objectMapper.writeValueAsString(this.mapToDTO(toDoList));
        this.mockMvc
                .perform(delete("/to-do-list/delete")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(toDoListDTOJSON))
                .andExpect(status().isNoContent());

        ToDoList fakeToDoList = new ToDoList(10L, "Fake List");
        ToDoListDTO fakeToDoListDTO = this.mapToDTO(fakeToDoList);
        String fakeToDoListDTOJSON = this.objectMapper.writeValueAsString(fakeToDoListDTO);
        this.mockMvc
                .perform(delete("/to-do-list/delete")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(fakeToDoListDTOJSON))
                .andExpect(status().isInternalServerError());
    }

    @Test
    public void readAllToDoListTest() throws Exception {
        String toDoListDTOJSON = this.objectMapper.writeValueAsString(this.mapToDTO(toDoList));
        String toDoListDTOJSON1 = this.objectMapper.writeValueAsString(this.mapToDTO(toDoList1));
        this.mockMvc
                .perform(get("/to-do-list/read")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").value("?"))
                .andExpect(jsonPath("$[0]").value(toDoListDTOJSON))
                .andExpect(jsonPath("$[1]").value(toDoListDTOJSON1));
    }

//    @Test
//    public void readByIDToDoListTest() {
//        String targetID = "2";
//        this.mockMvc
//                .perform(get("/to-do-list/read/{id}", targetID)
//                .accept(MediaType.APPLICATION_JSON)
//                )
//    }
}
