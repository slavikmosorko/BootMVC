package com.example.app.services;


import com.example.app.models.Message;

import java.util.HashMap;
import java.util.List;

public interface IMessageService {
    List<Message> getAllMessages();

    Message getMessageById(long messageId);

    void addMessage(Message message);

    void updateMessage(Message message);

    void deleteMessage(long messageId);

    String previewMessage(long messageId);
}
