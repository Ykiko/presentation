package com.example.conference.repository;

import com.example.conference.entity.Person;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface Repository extends CrudRepository<Person, Long> {
}
