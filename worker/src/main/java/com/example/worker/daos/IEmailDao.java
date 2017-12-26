package com.example.worker.daos;

import com.example.worker.models.Message;

import java.util.List;

public interface IEmailDao {
    void sendEmail(Message email);

    void setEmailAsInvalid(String emailId);

    List<Message> getAllUnprocessedEmails();
}
