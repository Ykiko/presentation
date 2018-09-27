package com.example.conference.service;

import com.example.conference.entity.User;
import com.example.conference.exception.NotFoundException;
import com.example.conference.exception.UserNameProblemException;
import com.example.conference.repository.UserRepository;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@ConfigurationProperties(prefix = "error.message")
@Data
public class UserService {

    private final UserRepository userRepository;
    private final MailSend mailSend;

    private String userIsRequired;

    private String loginInUse;

    private String notFound;

    private String nameAlreadyUsed;

    public void saveUser(User user) throws UserNameProblemException {

        String firstname = user.getFirstname();
        String lastname = user.getLastname();
        Integer age = user.getAge();
        String email = user.getEmail();
        String username = user.getUsername();
        String password = "{bcrypt}" + new BCryptPasswordEncoder().encode(user.getPassword());

        if (firstname.isEmpty() && lastname.isEmpty()) {
            throw new UserNameProblemException(userIsRequired);
        }

        if (userRepository.getByFirstnameAndLastname(firstname, lastname).isPresent()) {
            throw new UserNameProblemException(nameAlreadyUsed);
        }
//        for (User item : userRepository.findByFirstnameAndLastname(firstname, lastname)) {
//            throw new UserNameProblemException(nameAlreadyUsed);
//        }
        User newUser = new User(firstname, lastname, age, email, username, password);
        userRepository.save(newUser);

        if (!StringUtils.isEmpty(user.getEmail())) {
            String message = String.format("Hello, %s! " +
                    "Welcome to Presentation", user.getUsername());

            mailSend.send(user.getEmail(), "Welcome", message);
        }
    }

    public void updateSetUser(Long id, User user) throws NotFoundException {
        User editUser = userRepository.findById(id).orElseThrow(NotFoundException::new);

        user.setId(editUser.getId());
        user.setPassword(editUser.getPassword());
        userRepository.save(user);
    }

    public void deleteIdUser(Long id) throws NotFoundException {
        if (id != null) {
            userRepository.deleteById(id);
        } else {
            throw new NotFoundException(notFound);
        }
    }

    public Iterable<User> findAll() {
        return userRepository.findAll();
    }

    public Optional<User> findById(Long id) {
        return userRepository.findById(id);
    }
}
