package com.example.conference.controller;

import com.example.conference.entity.ROLE;
import com.example.conference.entity.ROOM;
import com.example.conference.repository.Repository;
import com.example.conference.repository.RepositoryPresent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.Arrays;

@Controller
public class ControlSettingPresent {
    private RepositoryPresent repositoryPresent;
    private Repository repository;

    @Autowired
    public ControlSettingPresent(RepositoryPresent repositoryPresent, Repository repository){
        this.repositoryPresent = repositoryPresent;
        this.repository = repository;
    }

    @RequestMapping(value = {"/listOfPresentations"}, method = RequestMethod.GET)
    public String listOfPresentation (Model model) {
        model.addAttribute("presentations", repositoryPresent.findAll());
        model.addAttribute("users", repository.findAll());
        model.addAttribute("rooms", Arrays.asList(ROOM.ROOM0.toString(), ROOM.ROOM1.toString(), ROOM.ROOM2.toString(),
                ROOM.ROOM3.toString(), ROOM.ROOM4.toString(), ROOM.ROOM5.toString()));
        return "/listOfPresentations";
    }
}
