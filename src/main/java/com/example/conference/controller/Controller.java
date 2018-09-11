package com.example.conference.controller;

import com.example.conference.entity.Presentation;
import com.example.conference.entity.ROLE;
import com.example.conference.entity.User;
import com.example.conference.repository.Repository;
import com.example.conference.repository.RepositoryPresent;
import com.example.conference.repository.RepositoryRoom;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.sql.RowSet;
import java.util.Optional;

@org.springframework.stereotype.Controller

public class Controller {
    private RepositoryPresent repositoryPresent;
    private RepositoryRoom repositoryRoom;
    private Repository repository;

    @Value("${welcome.message}")
    private String message;

    @Autowired
    public Controller(RepositoryRoom repositoryRoom, RepositoryPresent repositoryPresent, Repository repository) {
        this.repositoryPresent = repositoryPresent;
        this.repositoryRoom = repositoryRoom;
        this.repository = repository;
    }

    @RequestMapping(value = {"/schedule", "/"}, method = RequestMethod.GET)
    public String start(Model model) {
        model.addAttribute("Message", message);
        model.addAttribute("presentations", repositoryPresent.findAll());
        model.addAttribute("rooms", repositoryRoom.findAll());
        model.addAttribute("users", repository.findAll());
        return "/schedule";
    }

    @Secured({ROLE.ROLE_LISTENER, ROLE.ROLE_ADMIN, ROLE.ROLE_PRESENTER})
    @RequestMapping(value = {"/auditionRecord/{id}"}, method = RequestMethod.GET)
    public String auditionRecord(@PathVariable("id") Long id) {

        Optional<Presentation> schedule = repositoryPresent.findById(id);
        if (schedule.isPresent()) {
            Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            if (principal instanceof UserDetails) {
                String username = ((UserDetails) principal).getUsername();
                Optional<User> userOptional = repository.findByUsername(username);
                if (userOptional.isPresent()) {
                    User user = userOptional.get();
                    boolean checkListener = false;
                    if(schedule.get().getListeners().contains(user)) {
                            checkListener = true;
                    }
                    if (!checkListener) {
                        schedule.ifPresent(schedule1 -> {
                            schedule1.getListeners().add(user);
                            repositoryPresent.save(schedule1);
                        });
                    }
                }
            } else {
                return "/registrationUser";
            }
        }
        return "redirect:/schedule";
    }
}
