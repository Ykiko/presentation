package com.example.conference.controller;

import com.example.conference.entity.Presentation;
import com.example.conference.entity.ROLE;
import com.example.conference.entity.Room;
import com.example.conference.repository.Repository;
import com.example.conference.repository.RepositoryPresent;
import com.example.conference.repository.RepositoryRoom;
import org.joda.time.Interval;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Controller
public class ControlSettingPresent {
    private RepositoryPresent repositoryPresent;
    private Repository repository;
    private RepositoryRoom repositoryRoom;

    @Value("${error.message5}")
    private String errorMessage5;
    @Value("${error.message8}")
    private String errorMessage8;

    @Autowired
    public ControlSettingPresent(RepositoryPresent repositoryPresent, Repository repository, RepositoryRoom repositoryRoom) {
        this.repositoryPresent = repositoryPresent;
        this.repository = repository;
        this.repositoryRoom = repositoryRoom;
    }

    @Secured({ROLE.ROLE_ADMIN, ROLE.ROLE_PRESENTER})
    @RequestMapping(value = {"/listOfPresentations"}, method = RequestMethod.GET)
    public String listOfPresentation(Model model) {
        model.addAttribute("presentations", repositoryPresent.findAll());
        return "/listOfPresentations";
    }

    @Secured({ROLE.ROLE_ADMIN, ROLE.ROLE_PRESENTER})
    @RequestMapping(value = {"/updatePresentation/{id}"}, method = RequestMethod.GET)
    public String updatePresentation(@PathVariable("id") Long id, Model model) {
        model.addAttribute("presentation", repositoryPresent.getById(id));
        model.addAttribute("presentations", repositoryPresent.findAll());
        model.addAttribute("users", repository.findAll());
        model.addAttribute("rooms", repositoryRoom.findAll());
        return "settingPresentation";
    }

    @Secured({ROLE.ROLE_ADMIN, ROLE.ROLE_PRESENTER})
    @RequestMapping(value = {"/updatePresentation/{id}"}, method = RequestMethod.POST)
    public String updatePresent(Model model, @PathVariable("id") Long id,
                           @ModelAttribute("presentation") Presentation presentation,
                           @ModelAttribute("rooms") Room room) {

        String namepresentation = presentation.getNamepresentation();
        Date startdate = presentation.getStartdate();
        Date enddate = presentation.getEnddate();
        Optional<Presentation> editPresentation = repositoryPresent.findById(id);
        String room1 = room.getRoom();

        if (!namepresentation.isEmpty()) {

            // check time overlap
            for (Presentation item : repositoryPresent.findAll()) {

                for (Room items : repositoryRoom.findAll()) {

                    Interval currentIntervar = new Interval(startdate.getTime(), enddate.getTime());
                    Interval itemInterval = new Interval(item.getEnddate().getTime(), item.getStartdate().getTime());

                    if (itemInterval.overlaps(currentIntervar) && room1.equals(items.getRoom())) {
                        return errorMessage8;
                    }
                    if (editPresentation.isPresent()) {

                        Presentation currentPresentation = editPresentation.get();
                        presentation.setId(currentPresentation.getId());
                        repositoryPresent.save(presentation);
                    }
                }
            }
        }
            model.addAttribute("errorMessage", errorMessage5);
            return "redirect:/listOfPresentations";
        }

}
