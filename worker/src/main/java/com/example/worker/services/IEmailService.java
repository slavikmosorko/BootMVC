package com.example.worker.services;

import com.example.worker.models.Message;

import java.util.List;

public interface IEmailService {
    void sendEmail(Message email);
    List<Message> getAllUnprocessedEmails();
}
