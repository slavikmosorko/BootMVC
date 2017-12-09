package com.example.worker.daos;

import com.example.worker.models.EmailDto;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;
import java.util.List;

@Repository
public class EmailDao implements IEmailDao {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    @Transactional(value = Transactional.TxType.REQUIRED, rollbackOn = Exception.class)
    public void sendEmail(EmailDto email) {
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
    public List<EmailDto> getAllUnprocessedEmails() {
        String timeZonePrefix = "(date_sub(NOW(), INTERVAL -9 HOUR))";
        String nativeQuery = "" +
                "SELECT id, content, subject addressee FROM messages m " +
                "WHERE m.sent = 0 " +
                "AND m.deleted = 0 " +
                "AND (m.sending_date BETWEEN " +
                "(" + timeZonePrefix +" + INTERVAL 1 MINUTE) AND NOW() OR m.sending_date < " + timeZonePrefix+ ")";
        Query query = entityManager
                .createNativeQuery(nativeQuery, EmailDto.class);
        return query.getResultList();
    }
}
