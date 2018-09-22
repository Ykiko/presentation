package com.example.conference.controller;

import com.example.conference.myException.*;
import com.example.conference.entity.Presentation;
import com.example.conference.entity.ROLE;
import com.example.conference.service.PresentationService;
import com.example.conference.service.RoomService;
import com.example.conference.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@RequiredArgsConstructor
@Controller
public class PresentationController {

    private final RoomService roomService;
    private final PresentationService presentationService;
    private final UserService userService;


    @Secured({ROLE.ROLE_ADMIN, ROLE.ROLE_PRESENTER})
    @RequestMapping(value = {"/addPresentation"}, method = RequestMethod.GET)
    public String addPresentation(Model model) {
        Presentation presentation = new Presentation();
        model.addAttribute("presentation", presentation);
        model.addAttribute("rooms", roomService.findAll());
        return "/addPresentation";
    }

    @Secured({ROLE.ROLE_ADMIN, ROLE.ROLE_PRESENTER})
    @RequestMapping(value = {"/addPresentation"}, method = RequestMethod.POST)
    public String savePresentation(@ModelAttribute("presentation") Presentation presentation,
                                   @ModelAttribute("room") Long roomId) throws PresentationAlreadyExistsException, CoincidesTimeException, PresentationException, NoRoomException {

        presentationService.addPresentation(presentation, roomId);
        return "redirect:/schedule";
    }

    @Secured({ROLE.ROLE_ADMIN, ROLE.ROLE_PRESENTER})
    @RequestMapping(value = {"/listOfPresentations"}, method = RequestMethod.GET)
    public String listOfPresentation(Model model) {
        model.addAttribute("presentations", presentationService.findAll());
        return "/listOfPresentations";
    }

    @Secured({ROLE.ROLE_ADMIN, ROLE.ROLE_PRESENTER})
    @RequestMapping(value = {"/updatePresentation/{id}"}, method = RequestMethod.GET)
    public String updatePresentation(@PathVariable("id") Long id, Model model) {
        presentationService.findById(id).ifPresent(o -> model.addAttribute("presentation", o));
        model.addAttribute("users", userService.findAll());
        model.addAttribute("rooms", roomService.findAll());
        return "settingPresentation";
    }

    @Secured({ROLE.ROLE_ADMIN, ROLE.ROLE_PRESENTER})
    @RequestMapping(value = {"/updatePresentation/{id}"}, method = RequestMethod.POST)
    public String updatePresentation(@PathVariable("id") Long id,
                                     @ModelAttribute("presentation") Presentation presentation,
                                     @ModelAttribute("room") Long roomId) throws CoincidesTimeException, NoRoomException, NotFoundException, PresentationAlreadyExistsException, PresentationException {
        presentationService.updateSetPresentation(id, presentation, roomId);
        return "redirect:/listOfPresentations";
    }

    @Secured({ROLE.ROLE_ADMIN, ROLE.ROLE_PRESENTER})
    @RequestMapping(value = {"/deletePresentation/{id}"}, method = RequestMethod.GET)
    public String deletePresentation(@PathVariable("id") Long id) throws NotFoundException {
        presentationService.deleteIdPresentation(id);
        return "redirect:/listOfPresentations";
    }
}
