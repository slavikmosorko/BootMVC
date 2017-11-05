package com.example.app.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
public class IndexController {

    @RequestMapping("/")
    public String index() {
        return "views/index";
    }

    @GetMapping("/login")
    public String login() {
        return "views/login";
    }

    @GetMapping("/access_denied")
    public String access_denied() {
        return "views/errors/access_denied";
    }
}
