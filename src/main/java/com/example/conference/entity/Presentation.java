package com.example.conference.entity;

import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "presentation")
public class Presentation implements Serializable {

    @ManyToOne
    @JoinTable(name = "Presentation_Room",
            joinColumns = @JoinColumn(name = "PRESENTATION_ID"),
            inverseJoinColumns = @JoinColumn(name = "ROOM_ID"))
    private Room room;

    @ManyToMany
    @JoinTable(name = "Presentation_user",
            joinColumns = @JoinColumn(name = "PRESENTATION_ID"),
            inverseJoinColumns = @JoinColumn(name = "USER_ID"))
    private Set<User> users = new HashSet<>();

    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    private Date startdate;
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    private Date enddate;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String namepresentation;

    public Presentation() {
    }

    public Presentation(String namepresentation, Date startdate, Date enddate) {

        this.namepresentation = namepresentation;
        this.startdate = startdate;
        this.enddate = enddate;
    }

    @Override
    public String toString() {
        return "Presentation{" +
                ", startdate=" + startdate +
                ", enddate=" + enddate +
                ", namepresentation='" + namepresentation + '\'' +
                '}';
    }

    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
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

    public Set<User> getUsers() { return users; }

    public void setUsers(Set<User> users) { this.users = users; }

    public void addUser(User user) { this.users.add(user);}

    public void removeUser(User user) { this.users.remove(user);}

    public Date getStartdate() {
        return startdate;
    }

    public void setStartdate(Date startdate) {
        this.startdate = startdate;
    }

    public Date getEnddate() {
        return enddate;
    }

    public void setEnddate(Date enddate) {
        this.enddate = enddate;
    }
}
