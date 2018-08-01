package com.example.conference.entity;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.io.Serializable;

public class Presentation implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String namePresentation;
    private Long id;

    public Presentation() {
    }

    public Presentation(String namePresentation) {
        this.namePresentation = namePresentation;
    }

    @Override
    public String toString() {
        return String.format("Presentation[id=%d, namePresentation='%s']");
    }

    public String getNamePresentation() {
        return namePresentation;
    }

    public void setNamePresentation(String namePresentation) {
        this.namePresentation = namePresentation;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
