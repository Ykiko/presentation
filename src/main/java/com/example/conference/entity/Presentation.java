package com.example.conference.entity;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "presentation")
public class Presentation implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String namepresentation;


    public Presentation() {
    }

    public Presentation(String namepresentation) {
        this.namepresentation = namepresentation;
    }

    @Override
    public String toString() {
        return "Presentation{" +
                "namepresentation='" + namepresentation + '\'' +
                '}';
    }

    public String getNamepresentation() {
        return namepresentation;
    }

    public void setNamepresentation(String namepresentation) {
        this.namepresentation = namepresentation;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
