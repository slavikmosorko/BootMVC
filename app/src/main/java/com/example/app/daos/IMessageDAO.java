package com.example.app.daos;


import com.example.app.models.Message;

import java.util.List;

public interface IMessageDAO {
    List<Message> getAllMessages();

    Message getMessageById(long messageId);

    void addMessage(Message message);

    void updateMessage(Message message);

    void deleteMessage(long messageId);

    List<Message> getAllUnprocessedMessages();
}
