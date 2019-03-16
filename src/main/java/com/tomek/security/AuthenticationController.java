package com.tomek.security;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
public class AuthenticationController {

    // controller returns Principal type object with information whether user is authorized or not, his role and username
    @GetMapping("/login")
    public Principal login(Principal user) {
        return user;
    }
}