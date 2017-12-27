package com.example.app.controllers;

import com.example.app.models.Message;
import com.example.app.models.UserAccount;
import com.example.app.services.IMessageService;
import com.example.app.services.IValidationService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

@Controller
@RequestMapping(value = "/messages")
public class MessageController {
    private final Logger logger = Logger.getLogger(this.getClass());

    private IMessageService messageService;
    private IValidationService validationService;

    @Autowired
    public MessageController(IMessageService messageService,
                             IValidationService validationService) {
        this.messageService = messageService;
        this.validationService = validationService;
    }

    @GetMapping("/list")
    public String getMessagesView() {
        return "views/messages/messagesList";
    }

    @GetMapping(value = "/list.json")
    @ResponseBody
    public List<Message> getMessagesList() {
        UserAccount userAccount = validationService.getUserAccount();
        return messageService.getAllMessages(userAccount);
    }

    @PostMapping("/add")
    public ResponseEntity<Void> addMessage(@Valid Message message) {
        UserAccount userAccount = validationService.getUserAccount();
        message.setUserId(userAccount.getId());
        try {
            messageService.addMessage(message);
            return new ResponseEntity<Void>(HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Error while adding message ID:" + message.getId());
        }
        return new ResponseEntity<Void>(HttpStatus.CONFLICT);
    }

    @GetMapping("/delete")
    public ResponseEntity<Void> deleteMessage(@NotNull String messageId) {
        try {
            messageService.deleteMessage(messageId);
            return new ResponseEntity<Void>(HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Error while deleting message ID:" + messageId);
        }
        return new ResponseEntity<Void>(HttpStatus.CONFLICT);
    }

    @PostMapping("/edit")
    public ResponseEntity<Void> editMessage(@Valid Message message) {
        try {
            messageService.updateMessage(message);
            return new ResponseEntity<Void>(HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Error while updating message ID:" + message.getId());
        }
        return new ResponseEntity<Void>(HttpStatus.CONFLICT);
    }

    @GetMapping("/preview")
    @ResponseBody
    public String previewMessage(@NotNull String messageId) {
        try {
            return messageService.previewMessage(messageId);
        } catch (Exception e) {
            logger.info("Message preview not found ID:" + messageId);
        }
        return "";
    }
}
