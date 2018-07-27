package com.example.conference.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@org.springframework.stereotype.Controller

public class Controller {

    @Value("${welcome.message}")
    private String message;

    @RequestMapping(value = {"/startList","/"}, method = RequestMethod.GET)
    public String start (Model model) {
        model.addAttribute("Message", message);
        return "/startList";
    }
}
