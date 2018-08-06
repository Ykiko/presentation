package com.example.conference.entity;

import javax.persistence.*;

@Entity
@Table(name = "Room")
public class Room {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String room;
    private String time;

    public Room() {
    }

    public Room(String room) {
        this.room = room;
    }

    public Room(String room, String time) {
        this.room = room;
        this.time = time;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRoom() {
        return room;
    }
    public  String getTime() {
        return time;
    }

    public void setRoom(String room) {
        this.room = room;
    }

    public void setTime(String time) {
        this.time = time;
    }

    @Override
    public String toString() {

        return String.format("Room[room='%s', time='%s']", room, time);
    }
}
