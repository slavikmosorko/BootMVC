package com.example.app.controllers;

import com.example.utils.Constants;
import com.example.app.models.Message;
import com.example.app.services.IMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Date;

@Controller
@RequestMapping(value = "/api")
public class ApiController {
    private IMessageService messageService;

    @Autowired
    public ApiController(IMessageService messageService) {
        this.messageService = messageService;
    }

    @GetMapping(value = "/sendEmail")
    public ResponseEntity<Void> sendEmail(String addressee,
                                          String subject,
                                          String content) {
        try {
            Message message = new Message();
            message.setAddressee(addressee);
            message.setSubject(subject);
            message.setContent(content);
            message.setUserId(Constants.API_EMAIL_ID);
            message.setSendingDate(new Date());
            messageService.addMessage(message);
            return new ResponseEntity("Email successfully scheduled", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity("Error while adding email", HttpStatus.SERVICE_UNAVAILABLE);
        }
    }
}
