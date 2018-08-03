package com.example.conference.entity;

import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

@Entity
@Table(name = "presentation")
public class Presentation implements Serializable {

    @ManyToMany
    @JoinTable(name = "Presentation_Room",
            joinColumns = @JoinColumn(name = "PRESENTATION_ID"),
            inverseJoinColumns = @JoinColumn(name = "ROOM_ID"))
    private Set<Room> rooms;

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
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Set<Room> getRooms() {
        return rooms;
    }

    public void setRooms(Set<Room> rooms) {
        this.rooms = rooms;
    }

    public void addRoom(Room room ) {
        this.rooms.add(room);
    }

    public void removeRoom(Room room) {
        this.rooms.remove(room);
    }
}
