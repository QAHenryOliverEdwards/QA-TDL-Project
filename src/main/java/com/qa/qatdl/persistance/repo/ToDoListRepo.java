package com.qa.qatdl.persistance.repo;

import com.qa.qatdl.persistance.domain.ToDoList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ToDoListRepo extends JpaRepository<ToDoList, Long> {
}
