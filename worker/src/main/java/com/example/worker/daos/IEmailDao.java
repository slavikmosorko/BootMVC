package com.example.worker.daos;

import com.example.worker.models.EmailDto;

import java.util.List;

public interface IEmailDao {
    void sendEmail(EmailDto email);
    List<EmailDto> getAllUnprocessedEmails();
}
