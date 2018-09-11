package com.example.conference.controller;

import com.example.conference.NoNameException;
import com.example.conference.entity.ROLE;
import com.example.conference.entity.Room;
import com.example.conference.repository.RoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class RoomController {
    private RoomRepository roomRepository;

    @Value("${error.message6}")
    private String errorMessage6;
    @Value("${error.message7}")
    private String errorMessage7;

    @Autowired
    public RoomController(RoomRepository roomRepository) {
        this.roomRepository = roomRepository;
    }

    @Secured(ROLE.ROLE_ADMIN)
    @RequestMapping(value = {"/rooms"}, method = RequestMethod.GET)
    public String listOfRoom(Model model) {
        model.addAttribute("rooms", roomRepository.findAll());
        return "/rooms";
    }

    @Secured(ROLE.ROLE_ADMIN)
    @RequestMapping(value = {"/addRoom"}, method = RequestMethod.GET)
    public String addRoom(Model model) {
        Room room = new Room();
        model.addAttribute("room", room);
        model.addAttribute("rooms", roomRepository.findAll());
        return "/addRoom";
    }

    @Secured(ROLE.ROLE_ADMIN)
    @RequestMapping(value = {"/addRoom"}, method = RequestMethod.POST)
    public String saveRoom(@ModelAttribute("room") Room room) throws NoNameException {
        String nameroom = room.getRoom();

        if (!nameroom.isEmpty()) {
            for (Room item : roomRepository.findAll()) {

                if (nameroom.equals(item.getRoom())) {

                    throw new NoNameException("Error:" + errorMessage6);
                }
            }
            Room newRoom = new Room(nameroom);
            roomRepository.save(newRoom);
            return "redirect:/rooms";
        }
        throw new NoNameException("Error:" + errorMessage7);
    }

    @Secured(ROLE.ROLE_ADMIN)
    @RequestMapping(value = {"/deleteRoom"}, params = {"id"}, method = RequestMethod.GET)
    public String deleteRoom(@RequestParam("id") Long id) {

        roomRepository.deleteById(id);

        return "redirect:/rooms";
    }

}
