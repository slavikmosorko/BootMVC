package com.example.app.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;
import java.util.Objects;


@Controller
public class LoginController {

    @GetMapping("/login")
    public String login(Principal principal) {
        if (Objects.nonNull(principal)) {
            return "redirect: /";
        }
        return "views/login";
    }

    @GetMapping("/access_denied")
    public String access_denied() {
        return "views/errors/access_denied";
    }
}
