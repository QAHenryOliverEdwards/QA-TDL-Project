package com.qa.qatdl.service;

import com.qa.qatdl.dto.TaskDTO;
import com.qa.qatdl.persistance.domain.ToDoList;
import com.qa.qatdl.persistance.domain.Task;
import com.qa.qatdl.persistance.repo.TaskRepo;
import com.qa.qatdl.utils.ItemUtil;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class TaskService {

    final TaskRepo taskRepo;

    final ModelMapper modelMapper;

    private TaskDTO mapToDTO(Task task) {
        return this.modelMapper.map(task, TaskDTO.class);
    }

    private Task mapToEntity(TaskDTO taskDTO) {return this.modelMapper.map(taskDTO, Task.class);}

    public TaskDTO create(TaskDTO taskDTO, Long basketID) {
        Task task = this.mapToEntity(taskDTO);
        task.setToDoList(new ToDoList(basketID));
        return this.mapToDTO(this.taskRepo.save(task));
    }

    public boolean delete(TaskDTO taskDTO) {
        this.taskRepo.delete(this.mapToEntity(taskDTO));
        return !this.taskRepo.existsById(this.mapToEntity(taskDTO).getTId());
    }

    public boolean deleteByID(Long tId) {
        this.taskRepo.deleteById(tId);
        return !this.taskRepo.existsById(tId);
    }

    public TaskDTO updateByID(TaskDTO taskDTO, Long tId) {
        Task task = this.taskRepo.findById(tId).orElseThrow();
        taskDTO.setName(taskDTO.getName());
        ItemUtil.mergeNotNull(taskDTO, task);
        return mapToDTO(this.taskRepo.save(task));

    }

    public List<TaskDTO> readAll() {
        return this.taskRepo.findAll().stream().map(this::mapToDTO).collect(Collectors.toList());
    }

    public TaskDTO read(Long tId) {
        return this.mapToDTO(this.taskRepo.findById(tId).orElseThrow());
    }
}
