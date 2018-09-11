package com.example.conference.controller;

import com.example.conference.entity.ROLE;
import com.example.conference.entity.User;
import com.example.conference.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.Arrays;
import java.util.Optional;

@Controller
public class ListofUserController {

    private UserRepository userRepository;

    @Autowired
    public ListofUserController(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    @RequestMapping(value = {"/listOfUser/{id}"}, method = RequestMethod.GET)
    public String listOfUser(@PathVariable ("id") Long id, Model model) {
        model.addAttribute("user", userRepository.findById(id));
        return "listOfUser";
    }

    @Secured(ROLE.ROLE_ADMIN)
    @RequestMapping(value = {"/updateUser/{id}"}, method = RequestMethod.GET)
    public String updateUser(@PathVariable("id") Long id, Model model) {
        model.addAttribute("user", userRepository.findById(id));
        model.addAttribute("roles", Arrays.toString(ROLE.values()));
        return "settingUser";
    }

    @Secured(ROLE.ROLE_ADMIN)
    @RequestMapping(value = {"/updateUser/{id}"}, method = RequestMethod.POST)
    public String saveUser(@PathVariable("id") Long id,
                           @ModelAttribute("user") User user) {
        Optional<User> editUser = userRepository.findById(id);
        if (editUser.isPresent()) {
            User currentUser = editUser.get();
            user.setId(currentUser.getId());
            user.setPassword(currentUser.getPassword());
            userRepository.save(user);
        }
        return "redirect:/listOfUser/" + user.getId();
    }
}
