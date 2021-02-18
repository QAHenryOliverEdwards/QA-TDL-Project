package com.qa.qatdl.controller;

import com.qa.qatdl.dto.TaskDTO;
import com.qa.qatdl.service.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@RequestMapping("/task")
@CrossOrigin("http://localhost:8393")
public class TaskController {

    private final TaskService taskService;

    @PostMapping("/create/{id}")
    public ResponseEntity<TaskDTO> create(@RequestBody TaskDTO inputTaskDTO, @PathVariable Long id) {
        TaskDTO taskDTO = this.taskService.create(inputTaskDTO, id);
        return new ResponseEntity<>(taskDTO, HttpStatus.CREATED);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<TaskDTO> delete(@RequestBody TaskDTO taskDTO) {
        return this.taskService.delete(taskDTO) ?
                new ResponseEntity<>(HttpStatus.NO_CONTENT)
                : new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<TaskDTO> deleteByID(@PathVariable Long id) {
        return this.taskService.deleteByID(id) ?
                new ResponseEntity<>(HttpStatus.NO_CONTENT)
                : new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<TaskDTO> updateByID(@RequestBody TaskDTO taskDTO, @PathVariable Long id) {
        return new ResponseEntity<>(this.taskService.updateByID(taskDTO, id), HttpStatus.ACCEPTED);
    }

    @GetMapping("/read")
    public ResponseEntity<List<TaskDTO>> readAll() {
        return ResponseEntity.ok(this.taskService.readAll());
    }

    @GetMapping("/read/{id}")
    public ResponseEntity<TaskDTO> read(@PathVariable Long id) {
        return ResponseEntity.ok(this.taskService.read(id));
    }

}
