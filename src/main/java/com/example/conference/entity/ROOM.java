package com.example.conference.entity;

public enum ROOM {
    ROOM0 ("0"),
    ROOM1 ("1"),
    ROOM2 ("2"),
    ROOM3 ("3"),
    ROOM4 ("4"),
    ROOM5 ("5");

    private String room;

    ROOM(final String room) {
        this.room = room;
    }

    public String getRoom() {
        return room;
    }
    public void setRoom(String room) {
        this.room = room;
    }

}
