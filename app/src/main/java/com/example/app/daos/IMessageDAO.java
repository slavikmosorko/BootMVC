package com.example.app.daos;


import com.example.app.models.Message;
import com.example.app.models.UserAccount;

import java.util.List;

public interface IMessageDAO {
    List<Message> getAllMessages(UserAccount userAccount);

    Message getMessageById(String messageId);

    void addMessage(Message message);

    void updateMessage(Message message);

    void deleteMessage(String messageId);

    String previewMessage(String messageId);

    List<Message> getAllUnprocessedMessages();
}
