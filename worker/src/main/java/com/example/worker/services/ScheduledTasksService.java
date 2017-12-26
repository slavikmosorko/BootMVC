package com.example.worker.services;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class ScheduledTasksService {

    private final Logger log = Logger.getLogger(this.getClass());
    private IEmailService emailService;

    @Autowired
    public ScheduledTasksService(IEmailService emailService) {
        this.emailService = emailService;
    }

    @Scheduled(fixedRate = 30000)
    public void sendMassaging() {
        if (ProcessingFacade.messageList.isEmpty()) {
            ProcessingFacade.messageList =
                    emailService.getAllUnprocessedEmails()
                            .stream()
                            .filter(email -> !ProcessingFacade.processedEmails.contains(email.getId()))
                            .collect(Collectors.toList());
        }
        log.info("Emails added for processing: " + ProcessingFacade.messageList.size());
        sendMessages();
    }

    private void sendMessages() {
        if (!ProcessingFacade.messageList.isEmpty()) {
            ProcessingFacade.messageList.stream()
                    .filter(email -> !ProcessingFacade.processedEmails.contains(email.getId()))
                    .forEach(
                            email -> {
                                log.info("\n[EMAIL PREPARED TO SENDING]\n ID:" + email.getId()
                                        + "\n SUBJECT: " + email.getSubject()
                                        + "\n FROM: " + email.getUserId()
                                        + "\n TO: " + email.getAddressee());
                                emailService.sendEmail(email);
                                ProcessingFacade.processedEmails.add(email.getId());
                            }
                    );
            ProcessingFacade.messageList.clear();
        }
        log.info("Unprocessed emails count: " + ProcessingFacade.processedEmails.size());
    }
}
