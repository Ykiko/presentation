package com.example.conference.controller;

import com.example.conference.MyException.NotFoundException;
import com.example.conference.entity.Presentation;
import com.example.conference.entity.ROLE;
import com.example.conference.repository.PresentationRepository;
import com.example.conference.repository.RoomRepository;
import com.example.conference.repository.UserRepository;
import com.example.conference.service.PresentationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class PresentationController {
    private PresentationRepository presentationRepository;
    private RoomRepository roomRepository;
    private UserRepository userRepository;
    private final PresentationService presentationService;

    @Autowired
    public PresentationController(PresentationRepository presentationRepository, RoomRepository roomRepository, UserRepository userRepository, PresentationService presentationService) {
        this.presentationRepository = presentationRepository;
        this.roomRepository = roomRepository;
        this.userRepository = userRepository;
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
                                   @ModelAttribute("room") Long roomId) throws Exception {

        presentationService.addPresentation(presentation, roomId);
        return "redirect:/schedule";
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
        presentationRepository.findById(id).ifPresent(o -> model.addAttribute("presentation", o));
        model.addAttribute("users", userRepository.findAll());
        model.addAttribute("rooms", roomRepository.findAll());
        return "settingPresentation";
    }

    @Secured({ROLE.ROLE_ADMIN, ROLE.ROLE_PRESENTER})
    @RequestMapping(value = {"/updatePresentation/{id}"}, method = RequestMethod.POST)
    public String updatePresentation(@PathVariable("id") Long id,
                                     @ModelAttribute("presentation") Presentation presentation,
                                     @ModelAttribute("room") Long roomId) throws Exception {
        presentationService.updateSetPresentation(id, presentation, roomId);
        return "redirect:/listOfPresentations";
    }

    @Secured({ROLE.ROLE_ADMIN, ROLE.ROLE_PRESENTER})
    @RequestMapping(value = {"/deletePresentation/{id}"}, method = RequestMethod.GET)
    public String deletePresentation(@PathVariable("id") Long id) throws Exception {
        presentationService.deleteIdPresentation(id);
        return "redirect:/listOfPresentations";
    }
}
