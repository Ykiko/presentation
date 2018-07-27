package com.example.conference.repository;

import com.example.conference.entity.User;
import org.springframework.data.repository.CrudRepository;

public interface Repository extends CrudRepository<User, Long> {
}
