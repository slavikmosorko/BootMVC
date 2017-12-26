package com.example.worker.daos;

import com.example.worker.models.Message;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

@Repository
public class EmailDao implements IEmailDao {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public void sendEmail(Message email) {
        String nativeQuery = "" +
                "UPDATE messages m" +
                " SET m.sent = 1" +
                " WHERE m.id = :emailId";
        Query query = entityManager
                .createNativeQuery(nativeQuery)
                .setParameter("emailId", email.getId());
        query.executeUpdate();
    }

    @Override
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public void setEmailAsInvalid(String emailId) {
        String nativeQuery = "" +
                "UPDATE messages m" +
                " SET m.valid = 0" +
                " WHERE m.id = :emailId";
        Query query = entityManager
                .createNativeQuery(nativeQuery)
                .setParameter("emailId", emailId);
        query.executeUpdate();
    }

    @Override
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED, readOnly = true)
    public List<Message> getAllUnprocessedEmails() {
        //String timeZonePrefix = "(date_sub(NOW(), INTERVAL -9 HOUR))";
        String timeZonePrefix = "(date_sub(NOW(), INTERVAL -0 HOUR))";
        String nativeQuery = "" +
                "SELECT * FROM messages m " +
                "WHERE m.sent = 0 " +
                "AND m.deleted = 0 " +
                "AND m.valid = 1 " +
                "AND (m.sending_date BETWEEN " +
                "(" + timeZonePrefix + " + INTERVAL 1 MINUTE) AND NOW() OR m.sending_date < " + timeZonePrefix + ")";
        Query query = entityManager
                .createNativeQuery(nativeQuery, Message.class);
        return query.getResultList();
    }
}
