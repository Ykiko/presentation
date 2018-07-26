package com.example.conference.controller;

import com.example.conference.entity.Person;
import com.example.conference.repository.Repository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

public class ControlPerson {
    private Repository repository;

    @Autowired
    public ControlPerson(Repository repository){
        this.repository = repository;
    }

    @RequestMapping(value = {"/addPerson"},method = RequestMethod.GET)
    public String addPerson(Model model) {
        Person person = new Person();
        model.addAttribute("person", person);
        model.addAttribute("persons", repository.findAll());
        return "addPerson";
    }
    @RequestMapping(value = {"/addPerson"},method = RequestMethod.POST)
    public String savePerson(Model model, @ModelAttribute("person") Person person)
    {
        String firstname = person.getFirstname();
        String lastname = person.getLastname();
        Integer age = person.getAge();
        String email = person.getEmail();
        String username = person.getUsername();
        String password = person.getPassword();

    }
}


