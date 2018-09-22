package com.example.conference.controller;

import com.example.conference.myException.UserNameProblemException;
import com.example.conference.entity.ROLE;
import com.example.conference.service.PresentationService;
import com.example.conference.service.RoomService;
import com.example.conference.service.ScheduleService;
import com.example.conference.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@RequiredArgsConstructor
@Controller
public class ScheduleController {

    private final ScheduleService scheduleService;
    private final PresentationService presentationService;
    private final RoomService roomService;
    private final UserService userService;

    @Value("${welcome.message}")
    private String message;

    @RequestMapping(value = {"/schedule", "/"}, method = RequestMethod.GET)
    public String start(Model model) {
        model.addAttribute("Message", message);
        model.addAttribute("presentations", presentationService.findAll());
        model.addAttribute("rooms", roomService.findAll());
        model.addAttribute("users", userService.findAll());
        return "/schedule";
    }

    @Secured({ROLE.ROLE_LISTENER, ROLE.ROLE_ADMIN, ROLE.ROLE_PRESENTER})
    @RequestMapping(value = {"/auditionRecord/{id}"}, method = RequestMethod.GET)
    public String auditionRecord(@PathVariable("id") Long id) throws UserNameProblemException {

        scheduleService.audition(id);
        return "redirect:/schedule";
    }
}
