package com.example.conference.repository;

import com.example.conference.entity.Presentation;
import org.springframework.data.repository.CrudRepository;

public interface RepositoryPresent extends CrudRepository <Presentation, Long> {
    Presentation getById(Long id);

}
