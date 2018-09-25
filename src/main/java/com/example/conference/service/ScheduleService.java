package com.example.conference.service;

import com.example.conference.entity.Presentation;
import com.example.conference.entity.User;
import com.example.conference.exception.NotFoundException;
import com.example.conference.repository.PresentationRepository;
import com.example.conference.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ScheduleService {

    private final PresentationRepository presentationRepository;
    private final UserRepository userRepository;

    public void audition(Long id) throws NotFoundException {

        Presentation schedule = presentationRepository.findById(id).orElseThrow(NotFoundException::new);
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetails) {
            String username = ((UserDetails) principal).getUsername();
            User user = userRepository.findByUsername(username).orElseThrow(NotFoundException::new);
            schedule.getListeners().add(user);
            presentationRepository.save(schedule);
        }
    }
}
