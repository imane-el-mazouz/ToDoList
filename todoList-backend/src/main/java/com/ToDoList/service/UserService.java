package com.ToDoList.service;


import com.ToDoList.model.User;
import com.ToDoList.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {

    private final UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;


    @Autowired
    public UserService(UserRepository userRepository ) {
        this.userRepository = userRepository;
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }



public User createUser(User user) {
    PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    String passwordToHash = (user.getPassword() == null || user.getPassword().isEmpty()) ? "0000" : user.getPassword();

    String hashedPassword = passwordEncoder.encode(passwordToHash);

    user.setPassword(hashedPassword);

    return userRepository.save(user);
}



}
