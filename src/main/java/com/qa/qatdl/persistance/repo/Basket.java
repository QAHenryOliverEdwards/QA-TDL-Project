package com.qa.qatdl.persistance.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface Basket extends JpaRepository<Basket, Long> {
}
