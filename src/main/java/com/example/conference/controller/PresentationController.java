package com.example.conference.controller;

import com.example.conference.MyException.*;
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
    public String AddPresentation(Model model) {
        Presentation presentation = new Presentation();
        model.addAttribute("presentation", presentation);
        model.addAttribute("rooms", roomService.findAll());
        return "/addPresentation";
    }

    @Secured({ROLE.ROLE_ADMIN, ROLE.ROLE_PRESENTER})
    @RequestMapping(value = {"/addPresentation"}, method = RequestMethod.POST)
    public String SavePresentation(@ModelAttribute("presentation") Presentation presentation,
                                   @ModelAttribute("room") Long roomId) throws PresentationAlreadyExistsException, CoincidesTimeException, PresentationException, NoRoomException {

        presentationService.AddPresentation(presentation, roomId);
        return "redirect:/schedule";
    }

    @Secured({ROLE.ROLE_ADMIN, ROLE.ROLE_PRESENTER})
    @RequestMapping(value = {"/listOfPresentations"}, method = RequestMethod.GET)
    public String ListOfPresentation(Model model) {
        model.addAttribute("presentations", presentationService.findAll());
        return "/listOfPresentations";
    }

    @Secured({ROLE.ROLE_ADMIN, ROLE.ROLE_PRESENTER})
    @RequestMapping(value = {"/updatePresentation/{id}"}, method = RequestMethod.GET)
    public String UpdatePresentation(@PathVariable("id") Long id, Model model) {
        presentationService.findById(id).ifPresent(o -> model.addAttribute("presentation", o));
        model.addAttribute("users", userService.findAll());
        model.addAttribute("rooms", roomService.findAll());
        return "settingPresentation";
    }

    @Secured({ROLE.ROLE_ADMIN, ROLE.ROLE_PRESENTER})
    @RequestMapping(value = {"/updatePresentation/{id}"}, method = RequestMethod.POST)
    public String UpdatePresentation(@PathVariable("id") Long id,
                                     @ModelAttribute("presentation") Presentation presentation,
                                     @ModelAttribute("room") Long roomId) throws CoincidesTimeException, NoRoomException {
        presentationService.UpdateSetPresentation(id, presentation, roomId);
        return "redirect:/listOfPresentations";
    }

    @Secured({ROLE.ROLE_ADMIN, ROLE.ROLE_PRESENTER})
    @RequestMapping(value = {"/deletePresentation/{id}"}, method = RequestMethod.GET)
    public String DeletePresentation(@PathVariable("id") Long id) throws NotFoundException {
        presentationService.DeleteIdPresentation(id);
        return "redirect:/listOfPresentations";
    }
}
