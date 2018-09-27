package com.example.conference.repository;

import com.example.conference.entity.User;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends CrudRepository<User, Long> {
    Optional<User> findByUsername(String username);

    List<User> findByFirstnameAndLastname(String firstname, String lastname);

    Optional<User> getByFirstnameAndLastname(String firstname, String lastname);
}
