package com.example.conference.controller;

import com.example.conference.NoNameException;
import com.example.conference.entity.Presentation;
import com.example.conference.entity.ROLE;
import com.example.conference.entity.Room;
import com.example.conference.repository.RoomRepository;
import com.example.conference.repository.UserRepository;
import com.example.conference.repository.PresentationRepository;
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

import java.util.Date;
import java.util.Optional;

@Controller
public class SettingPresentationController {
    private PresentationRepository presentationRepository;
    private UserRepository userRepository;
    private RoomRepository roomRepository;

    @Value("${error.message5}")
    private String errorMessage5;
    @Value("${error.message8}")
    private String errorMessage8;

    @Autowired
    public SettingPresentationController(PresentationRepository presentationRepository, UserRepository userRepository, RoomRepository roomRepository) {
        this.presentationRepository = presentationRepository;
        this.userRepository = userRepository;
        this.roomRepository = roomRepository;
    }

    @Secured({ROLE.ROLE_ADMIN, ROLE.ROLE_PRESENTER})
    @RequestMapping(value = {"/listOfPresentations"}, method = RequestMethod.GET)
    public String listOfPresentation(Model model) {
        model.addAttribute("presentations", presentationRepository.findAll());
        return "/listOfPresentations";
    }

    @Secured({ROLE.ROLE_ADMIN, ROLE.ROLE_PRESENTER})
    @RequestMapping(value = {"/updatePresentation/{id}"}, method = RequestMethod.GET)
    public String updatePresentation(@PathVariable("id") Long id, Model model) {
        model.addAttribute("presentation", presentationRepository.findById(id));
        model.addAttribute("users", userRepository.findAll());
        model.addAttribute("rooms", roomRepository.findAll());
        return "settingPresentation";
    }

    @Secured({ROLE.ROLE_ADMIN, ROLE.ROLE_PRESENTER})
    @RequestMapping(value = {"/updatePresentation/{id}"}, method = RequestMethod.POST)
    public String updatePresent(@PathVariable("id") Long id,
                                @ModelAttribute("presentation") Presentation presentation,
                                @ModelAttribute("room") Long roomId) throws NoNameException {
        String namepresentation = presentation.getNamepresentation();
        Date startdate = presentation.getStartdate();
        Date enddate = presentation.getEnddate();
        Optional<Presentation> editPresentation = presentationRepository.findById(id);
        Optional<Room> newRoom = roomRepository.findById(roomId);
        Interval currentIntervar = new Interval(startdate.getTime(), enddate.getTime());

        if (!namepresentation.isEmpty() && editPresentation.isPresent() && newRoom.isPresent()) {

            Presentation currentPresentation = editPresentation.get();
            Room expectedRoom = newRoom.get();

            for (Presentation item : presentationRepository.findByRoom(expectedRoom)) {

                if (item == currentPresentation) {
                    continue;
                }

                Interval itemInterval = new Interval(item.getStartdate().getTime(), item.getEnddate().getTime());

                if (itemInterval.overlaps(currentIntervar)) {
                    throw new NoNameException("Error:" + errorMessage8);
                }
            }

            currentPresentation.setNamepresentation(namepresentation);
            currentPresentation.setUsers(presentation.getUsers());
            currentPresentation.setStartdate(startdate);
            currentPresentation.setEnddate(enddate);
            currentPresentation.setRoom(expectedRoom);
            presentationRepository.save(currentPresentation);
        }
        return "redirect:/listOfPresentations";
    }

}
