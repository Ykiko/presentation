package com.example.conference.entity;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "presentation")
public class Presentation implements Serializable {

    private ROOM room = ROOM.ROOM0;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String namepresentation;


    public Presentation() {
    }

    public Presentation(String namepresentation) {

        this.namepresentation = namepresentation;
    }

    public Presentation(String namepresentation, ROOM room) {

        this.namepresentation = namepresentation;
        this.room = room;
    }

    @Override
    public String toString() {
        return "Presentation{" +
                "id=" + id +
                ", namepresentation='" + namepresentation + '\'' +
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

    public ROOM getRoom() {
        return room;
    }

    public void setRoom(ROOM room) {
        this.room = room;
    }
}
