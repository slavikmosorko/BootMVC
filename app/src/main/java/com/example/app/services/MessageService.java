package com.example.app.services;

import com.example.app.daos.IMessageDAO;
import com.example.app.models.Message;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
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
    public List<Message> getAllMessages() {
        return messageDAO.getAllMessages();
    }

    @Override
    public Message getMessageById(long messageId) {
        return messageDAO.getMessageById(messageId);
    }

    @Override
    @Transactional(value = Transactional.TxType.REQUIRED, rollbackOn = Exception.class)
    public void updateMessage(Message message) {
        try {
            messageDAO.updateMessage(message);
            logger.info("Message ID: " + message.getId() + " updated successfully!");
        } catch (Exception e) {
            logger.error("Message ID: " + message.getId() + " updating failed!", e);
        }
    }

    @Override
    @Transactional(value = Transactional.TxType.REQUIRED, rollbackOn = Exception.class)
    public void addMessage(Message message) {
        try {
            messageDAO.addMessage(message);
            logger.info("Message ID: " + message.getId() + " added successfully!");
        } catch (Exception e) {
            logger.error("Message ID: " + message.getId() + " adding failed!", e);
        }
    }

    @Override
    @Transactional(value = Transactional.TxType.REQUIRED, rollbackOn = Exception.class)
    public void deleteMessage(long messageId) {
        try {
            messageDAO.deleteMessage(messageId);
            logger.info("Message ID: " + messageId + " deleted successfully!");
        } catch (Exception e) {
            logger.error("Message ID: " + messageId + " deleting failed!", e);
        }
    }

    @Override
    public String previewMessage(long messageId) {
        return messageDAO.previewMessage(messageId);
    }
}
