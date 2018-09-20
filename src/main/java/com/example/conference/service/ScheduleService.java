package com.example.conference.service;

import com.example.conference.MyException.NoNameUserException;
import com.example.conference.entity.Presentation;
import com.example.conference.entity.User;
import com.example.conference.repository.PresentationRepository;
import com.example.conference.repository.UserRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Optional;

@Service
public class ScheduleService {

    private PresentationRepository presentationRepository;
    private UserRepository userRepository;

    @Value("${error.message.userNotFound}")
    private String errorMessageUserNotFound;

    public ScheduleService(PresentationRepository presentationRepository, UserRepository userRepository) {
        this.presentationRepository = presentationRepository;
        this.userRepository = userRepository;
    }

    public void Audition(@PathVariable("id") Long id) throws NoNameUserException {

        Optional<Presentation> schedule = presentationRepository.findById(id);
        if (schedule.isPresent()) {
            Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            if (principal instanceof UserDetails) {
                String username = ((UserDetails) principal).getUsername();
                Optional<User> userOptional = userRepository.findByUsername(username);
                if (userOptional.isPresent()) {
                    User user = userOptional.get();
                    boolean checkListener = false;
                    if (schedule.get().getListeners().contains(user)) {
                        checkListener = true;
                    }
                    if (!checkListener) {
                        schedule.ifPresent(schedule1 -> {
                            schedule1.getListeners().add(user);
                            presentationRepository.save(schedule1);
                        });
                    }
                }
            } else {
                throw new NoNameUserException(errorMessageUserNotFound);
            }
        }
    }
}
