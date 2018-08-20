package com.example.conference.controller;

import com.example.conference.entity.ROLE;
import com.example.conference.entity.Room;
import com.example.conference.repository.RepositoryRoom;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

@Controller
public class ControllerRoom {
    private RepositoryRoom repositoryRoom;

    @Value("${error.message6}")
    private String errorMessage6;
    @Value("${error.message7}")
    private String errorMessage7;

    @Autowired
    public ControllerRoom (RepositoryRoom repositoryRoom) {this.repositoryRoom = repositoryRoom;}

    @Secured(ROLE.ROLE_ADMIN)
    @RequestMapping(value = {"/rooms"}, method = RequestMethod.GET)
    public String listOfRoom(Model model) {
        model.addAttribute("rooms", repositoryRoom.findAll());
        return "/rooms";
    }

    @Secured(ROLE.ROLE_ADMIN)
    @RequestMapping(value = {"/addRoom"}, method = RequestMethod.GET)
    public String addRoom(Model model) {
        Room room = new Room();
        model.addAttribute("room", room);
        model.addAttribute("rooms",  repositoryRoom.findAll());
        return "/addRoom";
    }
    @Secured(ROLE.ROLE_ADMIN)
    @RequestMapping(value = {"/addRoom"}, method = RequestMethod.POST)
    public String saveRoom(Model model, @ModelAttribute("room") Room room) {
        String nameroom = room.getRoom();
        if (!nameroom.isEmpty()) {
            List<String> nameroom1 = new ArrayList<>();
            repositoryRoom.findAll().forEach(item -> {
                nameroom1.add(item.getRoom());
            });
            if (nameroom1.contains(nameroom)) {
                return errorMessage6;
            }
            Room newRoom = new Room(nameroom);
            repositoryRoom.save(newRoom);
            return "redirect:/rooms";
        }
        model.addAttribute("errorMessage", errorMessage7);
        return "/addRoom";
    }

    @Secured(ROLE.ROLE_ADMIN)
    @RequestMapping(value = {"/deleteRoom"}, params = {"id"}, method = RequestMethod.GET)
    public String deleteRoom(Model model, @RequestParam("id") Long id) {

        repositoryRoom.deleteById(id);

        return "redirect:/rooms";
    }

}
