package com.example.worker.services;

import com.example.worker.WorkerInfo;
import com.example.worker.daos.IEmailDao;
import com.example.worker.models.Message;
import org.apache.log4j.Logger;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.mail.internet.MimeMessage;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Service
public class EmailService implements IEmailService {

    private final Logger log = Logger.getLogger(this.getClass());
    @Autowired
    public JavaMailSender emailSender;
    @Autowired
    IEmailDao emailDao;

    @Override
    public void setEmailAsInvalid(String emailId) {
        try {
            emailDao.setEmailAsInvalid(emailId);
            log.info("Email ID: " + emailId + " marked as invalid");
        } catch (Exception e) {
            log.error("Failed to set invalid at email ID: " + emailId);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public void sendEmail(Message email) {
        Runnable emailSenderThread = () -> {
            MimeMessage message = emailSender.createMimeMessage();
            try {
                MimeMessageHelper helper = new MimeMessageHelper(message, true);
                helper.setTo(email.getAddressee());
                helper.setSubject(email.getSubject());
                helper.setFrom("SpringBoot@apimail.com");
                Hibernate.initialize(email.getParameters());
                if (Objects.nonNull(email.getParameters())) {
                    helper.setText(getTemplateContent(email.getParameters(), email.getContent()), true);
                } else {
                    helper.setText(email.getContent(), true);
                }
                emailSender.send(message);
                emailDao.sendEmail(email);
                WorkerInfo.addProcessedTask();
                log.info("[EMAIL SENT] ID: " + email.getId());
            } catch (Exception e) {
                log.error("[ERROR SENDING EMAIL] " + email.getId());
                setEmailAsInvalid(email.getId());
                e.printStackTrace();
            }
            ProcessingFacade.deleteEmailFromQueue(email.getId());
        };
        new Thread(emailSenderThread).start();
    }

    @Override
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED, readOnly = true)
    public List<Message> getAllUnprocessedEmails() {
        return emailDao.getAllUnprocessedEmails();
    }

    public String getTemplateContent(Map<String, String> parameters, String content) {
        for (Map.Entry<String, String> entry : parameters.entrySet()) {
            content = content.replace("${" + entry.getKey() + "}", entry.getValue());
        }
        return content;
    }
}
