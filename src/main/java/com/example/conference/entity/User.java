package com.example.conference.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

@Entity
@Table(name = "user")
@Data
@NoArgsConstructor
@ToString
public class User implements Serializable {

    private ROLE role = ROLE.LISTENER;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String firstname;
    private String lastname;
    private Integer age;
    private String email;
    private String username;
    private String password;

    public User(String firstname, String lastname, Integer age, String email, String username, String password) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.age = age;
        this.email = email;
        this.username = username;
        this.password = password;
    }

    public User(ROLE role, String firstname, String lastname, Integer age, String email, String username, String password) {
        this.role = role;
        this.firstname = firstname;
        this.lastname = lastname;
        this.age = age;
        this.email = email;
        this.username = username;
        this.password = password;
    }
}
