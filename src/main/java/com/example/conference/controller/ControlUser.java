package com.example.conference.controller;

import antlr.StringUtils;
import com.example.conference.entity.ROLE;
import com.example.conference.entity.User;
import com.example.conference.repository.Repository;
import com.example.conference.service.MailSend;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

@Controller
public class ControlUser {
    private Repository repository;

    @Autowired
    private MailSend mailSend;

    @Value("${error.message1}")
    private String errorMessage1;
    @Value("${error.message2}")
    private String errorMessage2;
    @Value("${error.message3}")
    private String errorMessage3;

    @Autowired
    public ControlUser(Repository repository){
        this.repository = repository;
    }

    @RequestMapping(value = {"/registrationUser"}, method = RequestMethod.GET)
    public String addUser(Model model) {
        User user = new User();
        model.addAttribute("user", user);
        model.addAttribute("users", repository.findAll());
        return "/registrationUser";
    }

    @RequestMapping(value = {"/registrationUser"}, method = RequestMethod.POST)
    public String saveUser(Model model, @ModelAttribute("user") User user) {

        String firstname = user.getFirstname();
        String lastname = user.getLastname();
        int age = user.getAge();
        String email = user.getEmail();
        String username = user.getUsername();
        String password = "{bcrypt}" + new BCryptPasswordEncoder().encode(user.getPassword());


        if (!firstname.isEmpty() && !lastname.isEmpty()) {

            List<String> nameStr = new ArrayList<>();
            repository.findAll().forEach(item -> {
                nameStr.add(item.getUsername());
            });
            if(nameStr.contains(username)) {
                return errorMessage3;
            }
            User newUser = repository.save(new User(firstname, lastname, age, email, username, password));

            if (!org.springframework.util.StringUtils.isEmpty(user.getEmail())) {
                String message = String.format("Hello, %s! " +
                        "Welcome to Presentation", user.getUsername());

                mailSend.send(user.getEmail(), "Welcome", message);
            }
            return "redirect:/listOfUser/" + newUser.getId();
        }
        model.addAttribute("errorMessage", errorMessage1);
        return "/registrationUser";
    }

    @Secured(ROLE.ROLE_ADMIN)
    @RequestMapping(value = {"/deleteUser"}, params = {"id"}, method = RequestMethod.GET)
    public String deleteUser(Model model, @RequestParam("id") Long id) {

        repository.deleteById(id);

        return "redirect:/ListOfUsers";
    }
}


