package com.example.conference.controller;

import com.example.conference.MyException.NoNameUserException;
import com.example.conference.MyException.NotFoundException;
import com.example.conference.entity.ROLE;
import com.example.conference.entity.User;
import com.example.conference.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.Arrays;

@RequiredArgsConstructor
@Controller
public class UserController {
    private final UserService userService;

    @RequestMapping(value = {"/registrationUser"}, method = RequestMethod.GET)
    public String AddUser(Model model) {
        User user = new User();
        model.addAttribute("user", user);
        model.addAttribute("users", userService.findAll());
        return "/registrationUser";
    }

    @RequestMapping(value = {"/registrationUser"}, method = RequestMethod.POST)
    public String AddUser(@ModelAttribute("user") User user) throws NoNameUserException {
        userService.SaveUser(user);
        return "redirect:/schedule";
    }

    @Secured(ROLE.ROLE_ADMIN)
    @RequestMapping(value = {"/ListOfUsers"}, method = RequestMethod.GET)
    public String ListOfUsers(Model model) {
        model.addAttribute("users", userService.findAll());
        return "/ListOfUsers";
    }

    @RequestMapping(value = {"/listOfUser/{id}"}, method = RequestMethod.GET)
    public String ListOfUser(@PathVariable ("id") Long id, Model model) {
        userService.findById(id).ifPresent(o -> model.addAttribute("user", o));
        return "listOfUser";
    }

    @Secured(ROLE.ROLE_ADMIN)
    @RequestMapping(value = {"/updateUser/{id}"}, method = RequestMethod.GET)
    public String UpdateUser(@PathVariable("id") Long id, Model model) {
        userService.findById(id).ifPresent(o -> model.addAttribute("user", o));
        model.addAttribute("roles", Arrays.toString(ROLE.values()));
        return "settingUser";
    }

    @Secured(ROLE.ROLE_ADMIN)
    @RequestMapping(value = {"/updateUser/{id}"}, method = RequestMethod.POST)
    public String UpdateUser(@PathVariable("id") Long id,
                           @ModelAttribute("user") User user) {
        userService.UpdateSetUser(id, user);
        return "redirect:/listOfUser/" + user.getId();
    }

    @Secured(ROLE.ROLE_ADMIN)
    @RequestMapping(value = {"/deleteUser/{id}"}, method = RequestMethod.GET)
    public String DeleteUser(@PathVariable("id") Long id) throws NotFoundException {
        userService.DeleteIdUser(id);
        return "redirect:/ListOfUsers";
    }
}