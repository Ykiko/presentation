package com.example.conference.controller;

import com.example.conference.entity.ROLE;
import com.example.conference.repository.PresentationRepository;
import com.example.conference.repository.RoomRepository;
import com.example.conference.repository.UserRepository;
import com.example.conference.service.ScheduleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller

public class ScheduleController {
    private PresentationRepository presentationRepository;
    private RoomRepository roomRepository;
    private UserRepository userRepository;
    private final ScheduleService scheduleService;

    @Value("${welcome.message}")
    private String message;

    @Autowired
    public ScheduleController(RoomRepository roomRepository, PresentationRepository presentationRepository, UserRepository userRepository, ScheduleService scheduleService) {
        this.presentationRepository = presentationRepository;
        this.roomRepository = roomRepository;
        this.userRepository = userRepository;
        this.scheduleService = scheduleService;
    }

    @RequestMapping(value = {"/schedule", "/"}, method = RequestMethod.GET)
    public String start(Model model) {
        model.addAttribute("Message", message);
        model.addAttribute("presentations", presentationRepository.findAll());
        model.addAttribute("rooms", roomRepository.findAll());
        model.addAttribute("users", userRepository.findAll());
        return "/schedule";
    }

    @Secured({ROLE.ROLE_LISTENER, ROLE.ROLE_ADMIN, ROLE.ROLE_PRESENTER})
    @RequestMapping(value = {"/auditionRecord/{id}"}, method = RequestMethod.GET)
    public String auditionRecord(@PathVariable("id") Long id) throws Exception {

        scheduleService.Audition(id);
        return "redirect:/schedule";
    }
}
