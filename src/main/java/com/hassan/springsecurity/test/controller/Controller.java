package com.hassan.springsecurity.test.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.hassan.springsecurity.test.dto.AuthRequest;
import com.hassan.springsecurity.test.entity.UserEntity;
import com.hassan.springsecurity.test.service.UserService;
import com.hassan.springsecurity.test.util.JwtUtil;


@RestController
public class Controller {
	

    @Autowired
    UserService userService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtil jwtUtil;


    @GetMapping("/hello")
    public String welcome() {
        return "Hello world";
    }

    @GetMapping("/admin")
    public String welcomeAdmin() {
        return "Welcome Admin";
    }

    @PostMapping("/register")
    public UserEntity registerUser(@RequestBody UserEntity user) {
        return userService.addUser(user);
    }

    @PostMapping("/login")
    public String login(@RequestBody AuthRequest authRequest) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                        authRequest.getEmail(), authRequest.getPassword())
                );
        if (authentication.isAuthenticated()) {
            return jwtUtil.generateToken(authRequest.getEmail());
        } else {
            throw new RuntimeException("Invalid access");
        }
    }

}
