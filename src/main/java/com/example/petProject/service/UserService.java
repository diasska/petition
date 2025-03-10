package com.example.petProject.service;

import com.example.petProject.model.User;
import com.example.petProject.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.regex.Pattern;

@Service


public class UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    private static final String EMAIL_PATTERN = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$";
    private static final Pattern ALLOWED_CHARACTERS_PATTERN = Pattern.compile("^[a-zA-Z0-9.@_]+$");


    public User findUserByEmail(String email) {
        return userRepository.findByEmail(email).orElse(null);
    }


    public void registerUser(String email, String password) throws Exception {

        User existingUser =userRepository.findByEmail(email).orElse(null);
        if (existingUser != null) {
            throw new Exception("Email already exists");
        }
        if (!Pattern.matches(EMAIL_PATTERN, email)) {
            throw new Exception("Invalid email");
        }


        if(password.length()<6){
            throw new Exception("Password must be at least 6 characters");
        }

        if (email.length()<6) {
            throw new Exception("Email must be at least 6 characters");
        }



        User user = new User(email, password);
        user.setEmail(email);
        user.setPassword(passwordEncoder.encode(password));

        userRepository.save(user);


    }

    public boolean authenticate(String email, String password) {
        User user = userRepository.findByEmail(email).orElseThrow(() ->  new IllegalArgumentException("User not found"));
        return passwordEncoder.matches(password, user.getPassword());
    }
}
