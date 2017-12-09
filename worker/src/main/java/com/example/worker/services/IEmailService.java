package com.example.worker.services;

import com.example.worker.models.EmailDto;

import java.util.List;

public interface IEmailService {
    void sendEmail(EmailDto email);
    List<EmailDto> getAllUnprocessedEmails();
}
