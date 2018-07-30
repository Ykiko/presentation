package com.example.conference.entity;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.io.Serializable;

public class Presentation implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String namePresentatoin;
    private Long id;

    public Presentation() {
    }

    public String getNamePresentatoin() {
        return namePresentatoin;
    }

    public void setNamePresentatoin(String namePresentatoin) {
        this.namePresentatoin = namePresentatoin;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
