package com.example.conference.repository;

import com.example.conference.entity.Room;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface RoomRepository extends CrudRepository <Room, Long> {

    List<Room> findByRoom(String nameroom);
}
