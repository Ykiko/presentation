package com.example.conference.controller;

import com.example.conference.NoNameException;
import com.example.conference.entity.Presentation;
import com.example.conference.entity.ROLE;
import com.example.conference.entity.User;
import com.example.conference.repository.UserRepository;
import com.example.conference.repository.PresentationRepository;
import com.example.conference.repository.RoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@org.springframework.stereotype.Controller

public class ScheduleController {
    private PresentationRepository presentationRepository;
    private RoomRepository roomRepository;
    private UserRepository userRepository;

    @Value("${welcome.message}")
    private String message;
    @Value("${error.message2}")
    private String errorMessage2;

    @Autowired
    public ScheduleController(RoomRepository roomRepository, PresentationRepository presentationRepository, UserRepository userRepository) {
        this.presentationRepository = presentationRepository;
        this.roomRepository = roomRepository;
        this.userRepository = userRepository;
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
    public String auditionRecord(@PathVariable("id") Long id) throws NoNameException {

        Optional<Presentation> schedule = presentationRepository.findById(id);
        if (schedule.isPresent()) {
            Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            if (principal instanceof UserDetails) {
                String username = ((UserDetails) principal).getUsername();
                Optional<User> userOptional = userRepository.findByUsername(username);
                if (userOptional.isPresent()) {
                    User user = userOptional.get();
                    boolean checkListener = false;
                    if(schedule.get().getListeners().contains(user)) {
                            checkListener = true;
                    }
                    if (!checkListener) {
                        schedule.ifPresent(schedule1 -> {
                            schedule1.getListeners().add(user);
                            presentationRepository.save(schedule1);
                        });
                    }
                }
            } else {
                throw new NoNameException("Error:" + errorMessage2);
            }
        }
        return "redirect:/schedule";
    }
}
