package com.example.app.controllers;

import com.example.app.services.IRegistrationService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.transaction.Transactional;
import java.security.Principal;
import java.util.Objects;

@Controller
public class RegistrationController {
    private final Logger logger = Logger.getLogger(this.getClass());

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
            logger.info("Try to register user: " + email);
            if(registrationService.registerUser(email, password)) {
                return new ResponseEntity<Void>(HttpStatus.OK);
            }
            return new ResponseEntity<Void>(HttpStatus.CONFLICT);
        }
        logger.info("User already exist: " + email);
        return new ResponseEntity<Void>(HttpStatus.CONFLICT);
    }

    @GetMapping(value = "/register/activate/{ac}")
    public String registerNewUser(@PathVariable String ac) {
        if (registrationService.activateUser(ac)) {
            logger.info("User activated: " + ac);
            return "views/registration/thankYouPage";
        }
        logger.info("User already active: " + ac);
        return "redirect: /";
    }
}
