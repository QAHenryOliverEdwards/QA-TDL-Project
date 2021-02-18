package com.qa.qatdl.controller;

import com.qa.qatdl.dto.TaskDTO;
import com.qa.qatdl.persistance.domain.Task;
import com.qa.qatdl.service.TaskService;
import org.junit.jupiter.api.Disabled;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

@SpringBootTest
@Disabled
public class ItemControllerUnitTest {

    @Autowired
    TaskController taskController;

    @MockBean
    TaskService taskService;

    @Autowired
    ModelMapper modelMapper;

    private TaskDTO mapToDTO(Task task) {
        return this.modelMapper.map(task, TaskDTO.class);
    }

//    private final Task task = new Task(1L, "chicken", 9.99f, null);
//    private final Task task1 = new Task(1L, "ham", 12.99f, null);

//    private final List<Task> tasks =
}
