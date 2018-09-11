package com.example.conference.controller;

import com.example.conference.NoNameException;
import com.example.conference.entity.Presentation;
import com.example.conference.entity.ROLE;
import com.example.conference.repository.PresentationRepository;
import com.example.conference.repository.RoomRepository;
import com.example.conference.service.PresentationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class PresentationController {
    private PresentationRepository presentationRepository;
    private RoomRepository roomRepository;
    private final PresentationService presentationService;

    @Value("${error.message5}")
    private String errorMessage5;

    @Autowired
    public PresentationController(PresentationRepository presentationRepository, RoomRepository roomRepository, PresentationService presentationService) {
        this.presentationRepository = presentationRepository;
        this.roomRepository = roomRepository;
        this.presentationService = presentationService;
    }

    @Secured({ROLE.ROLE_ADMIN, ROLE.ROLE_PRESENTER})
    @RequestMapping(value = {"/addPresentation"}, method = RequestMethod.GET)
    public String addPresentation(Model model) {
        Presentation presentation = new Presentation();
        model.addAttribute("presentation", presentation);
        model.addAttribute("rooms", roomRepository.findAll());
        return "/addPresentation";
    }

    @Secured({ROLE.ROLE_ADMIN, ROLE.ROLE_PRESENTER})
    @RequestMapping(value = {"/addPresentation"}, method = RequestMethod.POST)
    public String savePresentation(@ModelAttribute("presentation") Presentation presentation,
                                   @ModelAttribute("room") Long roomId) throws NoNameException {

        try {
            presentationService.addPresentation(presentation, roomId);
            return "redirect:/schedule";
        } catch (NoNameException e) {
            throw new NoNameException("Error:" + errorMessage5);
        }

    }

    @Secured({ROLE.ROLE_ADMIN, ROLE.ROLE_PRESENTER})
    @RequestMapping(value = {"/deletePresentation/{id}"}, method = RequestMethod.GET)
    public String deletePresentation(@PathVariable("id") Long id) {

        presentationRepository.deleteById(id);

        return "redirect:/listOfPresentations";
    }
}
