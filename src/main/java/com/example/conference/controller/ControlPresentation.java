package com.example.conference.controller;

import com.example.conference.entity.Presentation;
import com.example.conference.entity.ROLE;
import com.example.conference.entity.Room;
import com.example.conference.repository.RepositoryPresent;
import com.example.conference.repository.RepositoryRoom;
import org.joda.time.Interval;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.Date;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

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
    public ControlPresentation(RepositoryPresent repositoryPresent, RepositoryRoom repositoryRoom) {
        this.repositoryPresent = repositoryPresent;
        this.repositoryRoom = repositoryRoom;
    }

    @Secured({ROLE.ROLE_ADMIN, ROLE.ROLE_PRESENTER})
    @RequestMapping(value = {"/addPresentation"}, method = RequestMethod.GET)
    public String addPresentation(Model model) {
        Presentation presentation = new Presentation();
        model.addAttribute("presentation", presentation);
        model.addAttribute("presentations", repositoryPresent.findAll());
        model.addAttribute("rooms", repositoryRoom.findAll());
        return "/addPresentation";
    }

    @Secured({ROLE.ROLE_ADMIN, ROLE.ROLE_PRESENTER})
    @RequestMapping(value = {"/addPresentation"}, method = RequestMethod.POST)
    public String savePresentation(Model model,
                                   @ModelAttribute("presentation") Presentation presentation,
                                   @ModelAttribute("rooms") Room room) {

        String namepresentation = presentation.getNamepresentation();
        Date startdate = presentation.getStartdate();
        Date enddate = presentation.getEnddate();
        Optional<Room> newRoom = repositoryRoom.findByRoom(room.getRoom());

        if (!namepresentation.isEmpty() && newRoom.isPresent()) {

            Room expectedRoom = newRoom.get();

            for (Presentation item : repositoryPresent.findAll()) {

                if (namepresentation.equals(item.getNamepresentation())) {
                    return errorMessage4;
                }

                // check time overlap
                Interval itemInterval = new Interval(item.getStartdate().getTime(), item.getEnddate().getTime());
                Interval currentIntervar = new Interval(startdate.getTime(), enddate.getTime());


                for (Room item1 : repositoryRoom.findAll()) {

                    if (itemInterval.overlaps(currentIntervar) && expectedRoom.equals(item1)) {
                        return errorMessage8;
                    }
                }
            }
            Presentation newPresentation = new Presentation(namepresentation, startdate, enddate);
            newPresentation.setRoom(expectedRoom);
            repositoryPresent.save(newPresentation);
            return "redirect:/schedule";
        }
        model.addAttribute("errorMessage", errorMessage5);
        return "/addPresentation";
    }

    @Secured({ROLE.ROLE_ADMIN, ROLE.ROLE_PRESENTER})
    @RequestMapping(value = {"/deletePresentation"}, params = {"id"}, method = RequestMethod.GET)
    public String deletePresentation(Model model, @RequestParam("id") Long id) {

        repositoryPresent.deleteById(id);

        return "redirect:/listOfPresentations";
    }
}
