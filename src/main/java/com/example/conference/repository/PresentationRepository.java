package com.example.conference.repository;

import com.example.conference.entity.Presentation;
import com.example.conference.entity.Room;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface PresentationRepository extends CrudRepository <Presentation, Long> {
    List<Presentation> findByRoom(Room room);
    Optional<Presentation> findByNamepresentation(String namepresentation);
}
