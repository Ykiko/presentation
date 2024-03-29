package com.example.conference.controller;

import com.example.conference.exception.NoRoomException;
import com.example.conference.exception.NotFoundException;
import com.example.conference.exception.RoomException;
import com.example.conference.entity.ROLE;
import com.example.conference.entity.Room;
import com.example.conference.service.RoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@RequiredArgsConstructor
@Controller
public class RoomController {
    private final RoomService roomService;

    @Secured(ROLE.ROLE_ADMIN)
    @RequestMapping(value = {"/rooms"}, method = RequestMethod.GET)
    public String listOfRoom(Model model) {
        model.addAttribute("rooms", roomService.findAll());
        return "/rooms";
    }

    @Secured(ROLE.ROLE_ADMIN)
    @RequestMapping(value = {"/addRoom"}, method = RequestMethod.GET)
    public String addRoom(Model model) {
        Room room = new Room();
        model.addAttribute("room", room);
        model.addAttribute("rooms", roomService.findAll());
        return "/addRoom";
    }

    @Secured(ROLE.ROLE_ADMIN)
    @RequestMapping(value = {"/addRoom"}, method = RequestMethod.POST)
    public String addRoom(@ModelAttribute("room") Room room) throws RoomException, NoRoomException {
        roomService.saveRoom(room);
        return "redirect:/rooms";
    }

    @Secured(ROLE.ROLE_ADMIN)
    @RequestMapping(value = {"/deleteRoom"}, params = {"id"}, method = RequestMethod.GET)
    public String deleteRoom(@RequestParam("id") Long id) throws NotFoundException {
        roomService.deleteIdRoom(id);
        return "redirect:/rooms";
    }

}
