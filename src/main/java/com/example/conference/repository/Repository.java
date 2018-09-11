package com.example.conference.repository;

import com.example.conference.entity.User;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface Repository extends CrudRepository<User, Long> {
    User getById(Long id);

    Optional<User> findByUsername(String username);
}
