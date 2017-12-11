package com.example.app.daos;

import com.example.app.models.Message;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;
import java.util.Objects;

@Repository
public class MessageDAO implements IMessageDAO {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Message> getAllMessages() {
        String hql = "" +
                "FROM Message as msg" +
                " WHERE msg.deleted = 0" +
                " ORDER BY msg.id";
        return (List<Message>) entityManager
                .createQuery(hql).getResultList();
    }

    @Override
    public Message getMessageById(long messageId) {
        return entityManager.find(Message.class, messageId);
    }

    @Override
    public void addMessage(Message message) {
        entityManager.persist(message);
    }

    @Override
    public void updateMessage(Message message) {
        entityManager.merge(message);
    }

    @Override
    public void deleteMessage(long messageId) {
        String nativeQuery = "" +
                "UPDATE messages m" +
                " SET m.deleted = 1" +
                " WHERE m.id = :messageId";
        Query query = entityManager
                .createNativeQuery(nativeQuery, Message.class)
                .setParameter("messageId", messageId);
        query.executeUpdate();
    }

    @Override
    public String previewMessage(long messageId) {
        String nativeQuery = "" +
                "SELECT content FROM messages m" +
                " WHERE m.id = :messageId";
        Query query = entityManager
                .createNativeQuery(nativeQuery)
                .setParameter("messageId", messageId);
        return query.getSingleResult().toString();
    }

    @Override
    public List<Message> getAllUnprocessedMessages() {
        String nativeQuery = "" +
                "SELECT * FROM messages m " +
                "WHERE m.sent = 0 " +
                "AND m.sending_date BETWEEN " +
                "(NOW() - INTERVAL 1 MINUTE) AND NOW()";
        Query query = entityManager
                .createNativeQuery(nativeQuery, Message.class);
        return query.getResultList();
    }
}
