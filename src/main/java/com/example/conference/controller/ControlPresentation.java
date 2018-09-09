package com.example.conference.controller;

import com.example.conference.NoNameException;
import com.example.conference.entity.Presentation;
import com.example.conference.entity.ROLE;
import com.example.conference.repository.RepositoryPresent;
import com.example.conference.repository.RepositoryRoom;
import com.example.conference.service.PresentationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class ControlPresentation {
    private RepositoryPresent repositoryPresent;
    private RepositoryRoom repositoryRoom;

    private final PresentationService presentationService;

    @Autowired
    public ControlPresentation(RepositoryPresent repositoryPresent, RepositoryRoom repositoryRoom, PresentationService presentationService) {
        this.repositoryPresent = repositoryPresent;
        this.repositoryRoom = repositoryRoom;
        this.presentationService = presentationService;
    }

    @Secured({ROLE.ROLE_ADMIN, ROLE.ROLE_PRESENTER})
    @RequestMapping(value = {"/addPresentation"}, method = RequestMethod.GET)
    public String addPresentation(Model model) {
        Presentation presentation = new Presentation();
        model.addAttribute("presentation", presentation);
        model.addAttribute("rooms", repositoryRoom.findAll());
        return "/addPresentation";
    }

    @Secured({ROLE.ROLE_ADMIN, ROLE.ROLE_PRESENTER})
    @RequestMapping(value = {"/addPresentation"}, method = RequestMethod.POST)
    public String savePresentation(Model model,
                                   @ModelAttribute("presentation") Presentation presentation,
                                   @ModelAttribute("room") Long roomId) {

        try {
            presentationService.addPresentation(presentation, roomId);
            return "redirect:/schedule";
        } catch (NoNameException e) {
            return  "/addPresentation";
        }

    }

    @Secured({ROLE.ROLE_ADMIN, ROLE.ROLE_PRESENTER})
    @RequestMapping(value = {"/deletePresentation/{id}"}, method = RequestMethod.GET)
    public String deletePresentation(@PathVariable("id") Long id) {

        repositoryPresent.deleteById(id);

        return "redirect:/listOfPresentations";
    }
}
