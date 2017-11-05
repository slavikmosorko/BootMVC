package com.example.app.services;

import com.example.app.daos.IMessageDAO;
import com.example.app.models.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class MessageService implements IMessageService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    public MessageService(IMessageDAO messageDAO) {
        this.messageDAO = messageDAO;
    }

    private IMessageDAO messageDAO;

    @Override
    @Transactional(value = Transactional.TxType.REQUIRED, rollbackOn = Exception.class)
    public List<Message> getAllMessages() {
        return messageDAO.getAllMessages();
    }

    @Override
    @Transactional(value = Transactional.TxType.REQUIRED, rollbackOn = Exception.class)
    public Message getMessageById(long messageId) {
        return messageDAO.getMessageById(messageId);
    }

    @Override
    @Transactional(value = Transactional.TxType.REQUIRED, rollbackOn = Exception.class)
    public void updateMessage(long messageId) {
        messageDAO.updateMessage(messageId);
    }

    @Override
    @Transactional(value = Transactional.TxType.REQUIRED, rollbackOn = Exception.class)
    public List<Message> getAllUnprocessedMessages() {
        return messageDAO.getAllUnprocessedMessages();
    }

    @Override
    @Transactional(value = Transactional.TxType.REQUIRED, rollbackOn = Exception.class)
    public void addMessage(Message message) {
        try {
            messageDAO.addMessage(message);
            logger.info("Message ID:{} added successfully!", message.getId());
        } catch (Exception e) {
            logger.error("Message ID:{} adding failed!", message.getId(), e);
        }
    }
}
