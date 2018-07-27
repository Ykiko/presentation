package com.example.conference.controller;

import com.example.conference.entity.User;
import com.example.conference.repository.Repository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class ControlListUser {

    private Repository repository;

    @Autowired
    public ControlListUser(Repository repository){
        this.repository = repository;
    }

    @RequestMapping(value = {"/listOfUser/{id}"}, method = RequestMethod.GET)
    public String listOfUser(@PathVariable ("id") Long id, Model model) {
        model.addAttribute("user", repository.findById(id));
        return "listOfUser";
    }
}
