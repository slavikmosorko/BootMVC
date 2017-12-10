package com.example.worker.services;

import com.example.worker.WorkerInfo;
import com.example.worker.daos.IEmailDao;
import com.example.worker.models.EmailDto;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.internet.MimeMessage;
import java.util.List;

@Service
public class EmailService implements IEmailService {

    private final Logger log = Logger.getLogger(this.getClass());
    @Autowired
    public JavaMailSender emailSender;
    @Autowired
    IEmailDao emailDao;

    @Override
    public void sendEmail(EmailDto email) {
        Runnable emailSenderThread = () -> {
            MimeMessage message = emailSender.createMimeMessage();
            try {
                MimeMessageHelper helper = new MimeMessageHelper(message, true);
                helper.setTo(email.getAddressee());
                helper.setSubject(email.getSubject());
                helper.setFrom("SpringBoot@apimail.com");
                helper.setText(email.getContent(), true);
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
