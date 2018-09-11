package com.example.conference.controller;

import com.example.conference.entity.ROLE;
import com.example.conference.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class ListofUsersController {
    private UserRepository userRepository;

    @Autowired
    public ListofUsersController(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    @Secured(ROLE.ROLE_ADMIN)
    @RequestMapping(value = {"/ListOfUsers"}, method = RequestMethod.GET)
    public String ListOfUsers(Model model) {
        model.addAttribute("users", userRepository.findAll());
        return "/ListOfUsers";
    }
}
