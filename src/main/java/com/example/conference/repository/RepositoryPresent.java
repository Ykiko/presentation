package com.example.conference.repository;

import com.example.conference.entity.Presentation;
import com.example.conference.entity.Room;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface RepositoryPresent extends CrudRepository <Presentation, Long> {
    Presentation getById(Long id);
    List<Presentation> findByRoom(Room room);
    Optional<Presentation> findByNamepresentation(String namepresentation);
}
