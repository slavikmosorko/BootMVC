package com.example.app.services;

import com.example.app.daos.IMessageDAO;
import com.example.app.models.Message;
import com.example.app.models.UserAccount;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class MessageService implements IMessageService {

    private final Logger logger = Logger.getLogger(this.getClass());
    private IMessageDAO messageDAO;

    @Autowired
    public MessageService(IMessageDAO messageDAO) {
        this.messageDAO = messageDAO;
    }

    @Override
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED, readOnly = true)
    public List<Message> getAllMessages(UserAccount userAccount) {
        return messageDAO.getAllMessages(userAccount);
    }

    @Override
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED, readOnly = true)
    public Message getMessageById(String messageId) {
        return messageDAO.getMessageById(messageId);
    }

    @Override
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public void updateMessage(Message message) {
        try {
            message.setValid(true);
            messageDAO.updateMessage(message);
            logger.info("Message ID: " + message.getId() + " updated successfully");
        } catch (Exception e) {
            logger.error("Message ID: " + message.getId() + " updating failed!", e);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public void addMessage(Message message) {
        try {
            messageDAO.addMessage(message);
            logger.info("Message ID: " + message.getId() + " added successfully");
        } catch (Exception e) {
            logger.error("Message ID: " + message.getId() + " adding failed!", e);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public void deleteMessage(String messageId) {
        try {
            messageDAO.deleteMessage(messageId);
            logger.info("Message ID: " + messageId + " deleted successfully");
        } catch (Exception e) {
            logger.error("Message ID: " + messageId + " deleting failed!", e);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED, readOnly = true)
    public String previewMessage(String messageId) {
        return messageDAO.previewMessage(messageId);
    }
}
