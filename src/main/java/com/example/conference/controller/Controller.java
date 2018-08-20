package com.example.conference.controller;

import com.example.conference.repository.Repository;
import com.example.conference.repository.RepositoryPresent;
import com.example.conference.repository.RepositoryRoom;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@org.springframework.stereotype.Controller

public class Controller {
    private RepositoryPresent repositoryPresent;
    private RepositoryRoom repositoryRoom;

    @Value("${welcome.message}")
    private String message;

    @Autowired
    public Controller(RepositoryRoom repositoryRoom, RepositoryPresent repositoryPresent, Repository repository) {
        this.repositoryPresent = repositoryPresent;
        this.repositoryRoom = repositoryRoom;
    }

    @RequestMapping(value = {"/schedule","/"}, method = RequestMethod.GET)
    public String start (Model model) {
        model.addAttribute("Message", message);
        model.addAttribute("presentations", repositoryPresent.findAll());
        model.addAttribute("rooms", repositoryRoom.findAll());
        return "/schedule";
    }
}
