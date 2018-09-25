package com.example.conference.service;

import com.example.conference.entity.Presentation;
import com.example.conference.entity.User;
import com.example.conference.exception.NotFoundException;
import com.example.conference.repository.PresentationRepository;
import com.example.conference.repository.UserRepository;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@ConfigurationProperties(prefix = "error.message")
@Data
public class ScheduleService {

    private final PresentationRepository presentationRepository;
    private final UserRepository userRepository;
    private String notFound;

    public void audition(Long id) throws NotFoundException {

        Presentation schedule = presentationRepository.findById(id).orElseThrow(NotFoundException::new);

        String username = Optional.ofNullable(SecurityContextHolder.getContext().getAuthentication().getPrincipal()).
                filter(o -> o instanceof UserDetails).map(o -> (UserDetails)o).map(UserDetails::getUsername).orElseThrow(NotFoundException::new);

            User user = userRepository.findByUsername(username).orElseThrow(NotFoundException::new);

            if(schedule.getListeners().contains(user)) {
                throw new NotFoundException(notFound);
            }
            schedule.getListeners().add(user);
            presentationRepository.save(schedule);

    }
}
