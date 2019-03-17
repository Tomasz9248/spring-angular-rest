package com.tomek.security;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@Controller
public class AuthenticationController {

    // controller returns Principal type object with information whether user is authorized or not, his role and username
    @PostMapping("/login")
    @ResponseBody
    public Principal login(Principal user) {
        return user;
    }
}