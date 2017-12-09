package com.example.worker.services;

import com.example.worker.WorkerInfo;
import com.example.worker.daos.IEmailDao;
import com.example.worker.models.EmailDto;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class EmailService implements IEmailService {

    private final Logger log = Logger.getLogger(this.getClass());

    @Autowired
    IEmailDao emailDao;
    @Autowired
    public JavaMailSender emailSender;

    @Override
    public void sendEmail(EmailDto email) {
        Runnable emailSenderThread = () -> {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(email.getAddressee());
            message.setSubject(email.getSubject());
            message.setFrom("SpringBoot@apimail.com");
            message.setText(email.getContent());
            try {
                emailSender.send(message);
                emailDao.sendEmail(email);
                WorkerInfo.addProcessedTask();
                log.info("[EMAIL SENT] ID: " + email.getId());
            } catch (Exception e) {
                log.error("[ERROR SENDING EMAIL] " + email.getId());
                e.printStackTrace();
            }
        };
        new Thread(emailSenderThread).start();
    }

    @Override
    public List<EmailDto> getAllUnprocessedEmails() {
        return emailDao.getAllUnprocessedEmails();
    }
}
