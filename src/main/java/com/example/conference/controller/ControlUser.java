package com.example.conference.controller;

import com.example.conference.NoNameException;
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
import org.springframework.web.bind.annotation.*;

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
    public String saveUser(Model model, @ModelAttribute("user") User user) throws NoNameException {

        String firstname = user.getFirstname();
        String lastname = user.getLastname();
        int age = user.getAge();
        String email = user.getEmail();
        String username = user.getUsername();
        String password = "{bcrypt}" + new BCryptPasswordEncoder().encode(user.getPassword());

        if (!firstname.isEmpty() && !lastname.isEmpty()) {

            for (User item : repository.findAll()) {

                if (firstname.equals(item.getFirstname()) && lastname.equals(item.getLastname())) {

                    throw new NoNameException("Error:" + errorMessage3);
                }
            }
            User newUser = repository.save(new User(firstname, lastname, age, email, username, password));

            if (!org.springframework.util.StringUtils.isEmpty(user.getEmail())) {
                String message = String.format("Hello, %s! " +
                        "Welcome to Presentation", user.getUsername());

                mailSend.send(user.getEmail(), "Welcome", message);
            }
            return "redirect:/schedule";
        }
        model.addAttribute("errorMessage", errorMessage1);
        return "/registrationUser";
    }

    @Secured(ROLE.ROLE_ADMIN)
    @RequestMapping(value = {"/deleteUser/{id}"}, method = RequestMethod.GET)
    public String deleteUser(@PathVariable("id") Long id) {

        repository.deleteById(id);

        return "redirect:/ListOfUsers";
    }
}