package com.example.conference.controller;

import com.example.conference.entity.Presentation;
import com.example.conference.entity.Room;
import com.example.conference.repository.RepositoryPresent;
import com.example.conference.repository.RepositoryRoom;
import org.joda.time.Interval;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Date;

@Controller
public class ControlPresentation {
    private RepositoryPresent repositoryPresent;
    private RepositoryRoom repositoryRoom;

    @Value("${error.message4}")
    private String errorMessage4;
    @Value("${error.message5}")
    private String errorMessage5;
    @Value("${error.message8}")
    private String errorMessage8;

    @Autowired
    public ControlPresentation(RepositoryPresent repositoryPresent, RepositoryRoom repositoryRoom){
        this.repositoryPresent = repositoryPresent;
        this.repositoryRoom = repositoryRoom;
    }

    @RequestMapping(value = {"/addPresentation"}, method = RequestMethod.GET)
    public String addPresentation(Model model) {
        Presentation presentation = new Presentation();
        model.addAttribute("presentation", presentation);
        model.addAttribute("presentations", repositoryPresent.findAll());
        model.addAttribute("rooms", repositoryRoom.findAll());
        return "/addPresentation";
    }

    @RequestMapping(value = {"/addPresentation"}, method = RequestMethod.POST)
    public String savePresentation(Model model, @ModelAttribute("presentation") Presentation presentation,
                                   @ModelAttribute("rooms") Room room) {

        String namepresentation = presentation.getNamepresentation();
        Date startdate = presentation.getStartdate();
        Date enddate = presentation.getEnddate();
        String room1 = room.getRoom();

        if (!namepresentation.isEmpty()) {

            for (Presentation item : repositoryPresent.findAll()) {

                if (namepresentation.equals(item.getNamepresentation())) {
                    return errorMessage4;
                }

                // check time overlap
                Interval itemInterval = new Interval(item.getStartdate().getTime(), item.getEnddate().getTime());
                Interval currentIntervar = new Interval(startdate.getTime(), enddate.getTime());

                if (itemInterval.overlaps(currentIntervar) && room1.equals(item.getRooms())) {
                    return errorMessage8;
                }
            }
            Presentation newPresentation = new Presentation(namepresentation, startdate, enddate);
            repositoryPresent.save(newPresentation);
            return "redirect:/startList";
        }
        model.addAttribute("errorMessage", errorMessage5);
        return "/addPresentation";
    }

    @RequestMapping(value = {"/deletePresentation"}, params = {"id"}, method = RequestMethod.GET)
    public String deletePresentation(Model model, @RequestParam("id") Long id) {

        repositoryPresent.deleteById(id);

        return "redirect:/listOfPresentations";
    }
}
