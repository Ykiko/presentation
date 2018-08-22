package com.example.conference.repository;

import com.example.conference.entity.Room;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface RepositoryRoom extends CrudRepository <Room, Long> {
    Optional<Room> findByRoom(String room);

}
