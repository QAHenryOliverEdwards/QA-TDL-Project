package com.qa.qatdl.controller;

import com.qa.qatdl.dto.ToDoListDTO;
import com.qa.qatdl.service.ToDoListService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@RequestMapping("/to-do-list")
@CrossOrigin("http://localhost:8393")
public class ToDoListController {

    private final ToDoListService toDoListService;

    @PostMapping("/create")
    public ResponseEntity<ToDoListDTO> create(@RequestBody ToDoListDTO inputToDoListDTO) {
        ToDoListDTO toDoListDTO = this.toDoListService.create(inputToDoListDTO);
        return new ResponseEntity<>(toDoListDTO, HttpStatus.CREATED);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<ToDoListDTO> delete(@RequestBody ToDoListDTO inputToDoListDTO) {
        return this.toDoListService.delete(inputToDoListDTO) ?
                new ResponseEntity<>(HttpStatus.NO_CONTENT)
                : new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ToDoListDTO> deleteByID(@PathVariable Long id) {
        return this.toDoListService.deleteByID(id) ?
                new ResponseEntity<>(HttpStatus.NO_CONTENT)
                : new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @GetMapping("/read")
    public ResponseEntity<List<ToDoListDTO>> readAll() {
         return ResponseEntity.ok(this.toDoListService.readAll());
    }

    @GetMapping("/read/{id}")
    public ResponseEntity<ToDoListDTO> read(@PathVariable Long id) {
        return ResponseEntity.ok(this.toDoListService.read(id));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<ToDoListDTO> update(@PathVariable Long id, @RequestBody ToDoListDTO toDoListDTO) {
        return new ResponseEntity<>(this.toDoListService.updateByID(toDoListDTO, id), HttpStatus.ACCEPTED);
    }

}
