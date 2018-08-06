package com.example.conference.controller;

import com.example.conference.entity.Presentation;
import com.example.conference.entity.User;
import com.example.conference.repository.RepositoryPresent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

@Controller
public class ControlPresentation {
    private RepositoryPresent repositoryPresent;

    @Value("${error.message4}")
    private String errorMessage4;
    @Value("${error.message5}")
    private String errorMessage5;

    @Autowired
    public ControlPresentation(RepositoryPresent repositoryPresent) {
        this.repositoryPresent = repositoryPresent;
    }

    @RequestMapping(value = {"/addPresentation"}, method = RequestMethod.GET)
    public String addPresentation(Model model) {
        Presentation presentation = new Presentation();
        model.addAttribute("presentation", presentation);
        model.addAttribute("presentations", repositoryPresent.findAll());
        return "/addPresentation";
    }

    @RequestMapping(value = {"/addPresentation"}, method = RequestMethod.POST)
    public String savePresentation(Model model, @ModelAttribute("presentation") Presentation presentation) {

        String namepresentation = presentation.getNamepresentation();

        if (!namepresentation.isEmpty()) {

            List<String> namepresentat = new ArrayList<>();
            repositoryPresent.findAll().forEach(item -> {
                namepresentat.add(item.getNamepresentation());
            });
            if(namepresentat.contains(namepresentation)) {
                return errorMessage4;
            }
            Presentation newPresentation = new Presentation(namepresentation);
            repositoryPresent.save(newPresentation);
            return "redirect:/startList";
        }
        model.addAttribute("errorMessage", errorMessage5);
        return "/addPresentation";
    }

    @RequestMapping(value = {"/deletePresentation"}, params = {"id"}, method = RequestMethod.GET)
    public String deletePresenattion(Model model, @RequestParam("id") Long id) {

        repositoryPresent.deleteById(id);

        return "redirect:/listOfPresentations";
    }
}
