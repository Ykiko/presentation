package com.example.conference.controller;

import com.example.conference.MyException.NoNameUserException;
import com.example.conference.entity.ROLE;
import com.example.conference.entity.User;
import com.example.conference.repository.UserRepository;
import com.example.conference.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.Arrays;

@Controller
public class UserController {
    private UserRepository userRepository;
    private final UserService userService;

    @Autowired
    public UserController(UserRepository userRepository, UserService userService) {
        this.userRepository = userRepository;
        this.userService = userService;
    }

    @RequestMapping(value = {"/registrationUser"}, method = RequestMethod.GET)
    public String addUser(Model model) {
        User user = new User();
        model.addAttribute("user", user);
        model.addAttribute("users", userRepository.findAll());
        return "/registrationUser";
    }

    @RequestMapping(value = {"/registrationUser"}, method = RequestMethod.POST)
    public String addUser(@ModelAttribute("user") User user) throws Exception {
        userService.saveUser(user);
        return "redirect:/schedule";
    }

    @Secured(ROLE.ROLE_ADMIN)
    @RequestMapping(value = {"/ListOfUsers"}, method = RequestMethod.GET)
    public String ListOfUsers(Model model) {
        model.addAttribute("users", userRepository.findAll());
        return "/ListOfUsers";
    }

    @RequestMapping(value = {"/listOfUser/{id}"}, method = RequestMethod.GET)
    public String listOfUser(@PathVariable ("id") Long id, Model model) {
        userRepository.findById(id).ifPresent(o -> model.addAttribute("user", o));
        return "listOfUser";
    }

    @Secured(ROLE.ROLE_ADMIN)
    @RequestMapping(value = {"/updateUser/{id}"}, method = RequestMethod.GET)
    public String updateUser(@PathVariable("id") Long id, Model model) {
        userRepository.findById(id).ifPresent(o -> model.addAttribute("user", o));
        model.addAttribute("roles", Arrays.toString(ROLE.values()));
        return "settingUser";
    }

    @Secured(ROLE.ROLE_ADMIN)
    @RequestMapping(value = {"/updateUser/{id}"}, method = RequestMethod.POST)
    public String updateUser(@PathVariable("id") Long id,
                           @ModelAttribute("user") User user) {
        userService.updateSetUser(id, user);
        return "redirect:/listOfUser/" + user.getId();
    }

    @Secured(ROLE.ROLE_ADMIN)
    @RequestMapping(value = {"/deleteUser/{id}"}, method = RequestMethod.GET)
    public String deleteUser(@PathVariable("id") Long id) throws Exception {
        userService.deleteIdUser(id);
        return "redirect:/ListOfUsers";
    }
}