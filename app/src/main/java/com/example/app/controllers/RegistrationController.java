package com.example.app.controllers;

import com.example.app.services.IRegistrationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;
import java.util.Objects;

@Controller
public class RegistrationController {
    IRegistrationService registrationService;

    @Autowired
    public RegistrationController(IRegistrationService registrationService) {
        this.registrationService = registrationService;
    }

    @GetMapping(value = "/register")
    public String getRegistrationView(Principal principal) {
        if (Objects.nonNull(principal)) {
            return "redirect: /";
        }
        return "views/registration/register";
    }

    @PostMapping(value = "/register/newuser")
    public ResponseEntity<Void> registerNewUser(@RequestParam String email, @RequestParam String password) {
        if (registrationService.validateUser(email)) {
            if (registrationService.registerUser(email, password)) {
                return new ResponseEntity<Void>(HttpStatus.OK);
            }
            return new ResponseEntity<Void>(HttpStatus.CONFLICT);
        }
        return new ResponseEntity<Void>(HttpStatus.CONFLICT);
    }

    @GetMapping(value = "/register/activate/{ac}")
    public String registerNewUser(@PathVariable String ac) {
        if (registrationService.activateUser(ac)) {
            return "views/registration/thankYouPage";
        }
        return "redirect: /";
    }
}
