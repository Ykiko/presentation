package com.example.conference.repository;

import com.example.conference.entity.Room;
import org.springframework.data.repository.CrudRepository;

public interface RepositoryRoom extends CrudRepository <Room, Long> {
}
