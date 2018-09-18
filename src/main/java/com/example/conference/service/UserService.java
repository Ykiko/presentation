package com.example.conference.service;

import com.example.conference.MyException.NoNameUserException;
import com.example.conference.MyException.NotFoundException;
import com.example.conference.entity.Presentation;
import com.example.conference.entity.User;
import com.example.conference.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Optional;

@Service
public class UserService {

    private UserRepository userRepository;

    private final MailSend mailSend;

    @Value("${error.message1}")
    private String errorMessage1;
    @Value("${error.message3}")
    private String errorMessage3;
    @Value("${error.message9}")
    private String errorMessage9;

    @Autowired
    public UserService(UserRepository userRepository, MailSend mailSend) {
        this.userRepository = userRepository;
        this.mailSend = mailSend;
    }

    public void saveUser(@ModelAttribute("user") User user) throws NoNameUserException {

        String firstname = user.getFirstname();
        String lastname = user.getLastname();
        Integer age = user.getAge();
        String email = user.getEmail();
        String username = user.getUsername();
        String password = "{bcrypt}" + new BCryptPasswordEncoder().encode(user.getPassword());

        if (!firstname.isEmpty() && !lastname.isEmpty()) {

            for (User item : userRepository.findAll()) {

                if (firstname.equals(item.getFirstname()) && lastname.equals(item.getLastname())) {

                    throw new NoNameUserException("Error:" + errorMessage3);
                }
            }
            User newUser = new User(firstname, lastname, age, email, username, password);
            userRepository.save(newUser);

            if (!StringUtils.isEmpty(user.getEmail())) {
                String message = String.format("Hello, %s! " +
                        "Welcome to Presentation", user.getUsername());

                mailSend.send(user.getEmail(), "Welcome", message);
            }
        } else
            throw new NoNameUserException("Error:" + errorMessage1);
    }

    public void updateSetUser(@PathVariable("id") Long id,
                              @ModelAttribute("user") User user) {
        Optional<User> editUser = userRepository.findById(id);
        if (editUser.isPresent()) {
            User currentUser = editUser.get();
            user.setId(currentUser.getId());
            user.setPassword(currentUser.getPassword());
            userRepository.save(user);
        }
    }

    public void deleteIdUser(@PathVariable("id") Long id) throws Exception {
        if (id != null) {
            userRepository.deleteById(id);
        } else throw new NotFoundException("Error:" + errorMessage9);
    }

    public Iterable<User> findAll() {
        return userRepository.findAll();
    }
    public Optional<User> findById(Long id) { return  userRepository.findById(id);}
}
