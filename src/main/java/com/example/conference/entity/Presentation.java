package com.example.conference.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "presentation")
@Data
@NoArgsConstructor
@ToString
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

    @ManyToMany
    @JoinTable(name = "Presentation_listener",
            joinColumns = @JoinColumn(name = "PRESENTATION_ID"),
            inverseJoinColumns = @JoinColumn(name = "USER_ID"))
    private Set<User> listeners = new HashSet<>();

    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    private Date startdate;
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    private Date enddate;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String namepresentation;

    public Presentation(String namepresentation, Date startdate, Date enddate) {

        this.namepresentation = namepresentation;
        this.startdate = startdate;
        this.enddate = enddate;
    }
}
