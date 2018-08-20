package com.example.conference.controller;

import com.example.conference.entity.ROLE;
import com.example.conference.entity.User;
import com.example.conference.repository.Repository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class ControlListofUsers {
    private Repository repository;

    @Autowired
    public ControlListofUsers(Repository repository){
        this.repository = repository;
    }

    @Secured(ROLE.ROLE_ADMIN)
    @RequestMapping(value = {"/ListOfUsers"}, method = RequestMethod.GET)
    public String ListOfUsers(Model model) {
        model.addAttribute("users", repository.findAll());
        return "/ListOfUsers";
    }
}
