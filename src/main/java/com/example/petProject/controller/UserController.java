package com.example.petProject.controller;

import com.example.petProject.model.User;
import com.example.petProject.service.JwtUtil;
import com.example.petProject.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api")
@Slf4j
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private JwtUtil jwtUtil;

    @GetMapping("/user")
    public ResponseEntity<User> getUser(@RequestParam String email) {
        User getUser = userService.findUserByEmail(email);
        if (getUser == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(getUser);
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody Map<String, String> payload) {
        String email = payload.get("email");
        String password = payload.get("password");

        if (email == null || password == null) {
            return ResponseEntity.badRequest().body(Collections.singletonMap("error", "Email и пароль обязательны"));
        }

        try {
            if (userService.findUserByEmail(email) != null) {
                return ResponseEntity.status(409).body(Collections.singletonMap("error", "Пользователь с таким email уже существует"));
            }

            String token = jwtUtil.generateToken(email);
            Map<String, String> response = new HashMap<>();

            response.put("token", token);
            userService.registerUser(email,password);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("sdfsdmfsdfklsdmf");
            return ResponseEntity.status(500).body(Collections.singletonMap("error", "Произошла ошибка при регистрации"));
        }


    }
    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody Map<String, String> payload){
        try{
            String email =  payload.get("email");
            String password =  payload.get("password");
            if(userService.authenticate(email,password)){
                String token = jwtUtil.generateToken(email);
                return ResponseEntity.ok(Map.of("token", token));
            }else return ResponseEntity.status(401).body(Collections.singletonMap("error", "Ошибка входа"));
        }catch (Exception e){
            return ResponseEntity.status(401).body(Collections.singletonMap("error", "Ошибка входа"));
        }
    }}