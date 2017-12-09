package com.example.worker.services;

import com.example.worker.models.EmailDto;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;

@Component
public class ScheduledTasksService {
    private static List<EmailDto> messageList = Collections.EMPTY_LIST;
    private final Logger log = Logger.getLogger(this.getClass());
    private IEmailService emailService;

    @Autowired
    public ScheduledTasksService(IEmailService emailService) {
        this.emailService = emailService;
    }

    @Scheduled(fixedRate = 60000)
    public void sendMassaging() {
        if (messageList.isEmpty()) {
            messageList = emailService.getAllUnprocessedEmails();
        }
        log.info("Messages amount: " + messageList.size());
        sendMessages();
    }

    private void sendMessages() {
        if (!messageList.isEmpty()) {
            messageList.stream()
                    .forEach(
                            email -> {
                                log.info("\n[EMAIL PREPARED TO SENDING]\n ID:" + email.getId() + "\n CONTENT: " + email.getContent());
                                emailService.sendEmail(email);
                            }
                    );
            messageList.clear();
        }
    }
}