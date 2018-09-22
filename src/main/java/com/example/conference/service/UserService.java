package com.example.conference.service;

import com.example.conference.myException.NotFoundException;
import com.example.conference.myException.UserNameProblemException;
import com.example.conference.entity.User;
import com.example.conference.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Optional;

@Service
public class UserService {

    private UserRepository userRepository;
    private final MailSend mailSend;

    @Value("${error.message.userIsRequired}")
    private String errorMessageUserIsRequired;
    @Value("${error.message.loginInUse}")
    private String errorMessageLoginInUse;
    @Value("${error.message.notFound}")
    private String errorMessageNotFound;

    @Autowired
    public UserService(UserRepository userRepository, MailSend mailSend) {
        this.userRepository = userRepository;
        this.mailSend = mailSend;
    }

    public void saveUser(User user) throws UserNameProblemException {

        String firstname = user.getFirstname();
        String lastname = user.getLastname();
        Integer age = user.getAge();
        String email = user.getEmail();
        String username = user.getUsername();
        String password = "{bcrypt}" + new BCryptPasswordEncoder().encode(user.getPassword());

        if (firstname.isEmpty() && lastname.isEmpty()) {
            throw new UserNameProblemException(errorMessageUserIsRequired);
        }

        for (User item : userRepository.findAll()) {

            if (firstname.equals(item.getFirstname()) && lastname.equals(item.getLastname())) {

                throw new UserNameProblemException(errorMessageLoginInUse);
            }
        }
        User newUser = new User(firstname, lastname, age, email, username, password);
        userRepository.save(newUser);

        if (!StringUtils.isEmpty(user.getEmail())) {
            String message = String.format("Hello, %s! " +
                    "Welcome to Presentation", user.getUsername());

            mailSend.send(user.getEmail(), "Welcome", message);
        }
    }

    public void updateSetUser(Long id, User user) {
        Optional<User> editUser = userRepository.findById(id);
        if (editUser.isPresent()) {
            User currentUser = editUser.get();
            user.setId(currentUser.getId());
            user.setPassword(currentUser.getPassword());
            userRepository.save(user);
        }
    }

    public void deleteIdUser(Long id) throws NotFoundException {
        if (id != null) {
            userRepository.deleteById(id);
        } else {
            throw new NotFoundException(errorMessageNotFound);
        }
    }

    public Iterable<User> findAll() {
        return userRepository.findAll();
    }

    public Optional<User> findById(Long id) {
        return userRepository.findById(id);
    }
}
