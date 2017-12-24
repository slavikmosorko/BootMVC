package com.example.app.daos;

import com.example.app.models.UserAccount;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.math.BigInteger;

@Repository
public class RegistrationDAO implements IRegistrationDAO {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public boolean validateUser(String username) {
        String nativeQuery = "" +
                "SELECT COUNT(*) FROM users u" +
                " WHERE u.username = :username";
        Query query = entityManager
                .createNativeQuery(nativeQuery)
                .setParameter("username", username);
        return ((BigInteger) query.getSingleResult()).intValue() == 0;
    }

    @Override
    public void registerUser(UserAccount userAccount) {
        entityManager.persist(userAccount);
        String nativeQuery = "" +
                "INSERT INTO group_members (user_id, group_id) " +
                "VALUES (:user_id, :group_id);";
        Query query = entityManager
                .createNativeQuery(nativeQuery)
                .setParameter("user_id", userAccount.getId())
                .setParameter("group_id", 2);
        query.executeUpdate();
    }

    @Override
    public boolean activateUser(String ac) {
        String nativeQuery = "" +
                "SELECT enabled FROM users u" +
                " WHERE u.activation_code = :ac";
        Query query = entityManager
                .createNativeQuery(nativeQuery)
                .setParameter("ac", ac);
        if (!((Boolean) query.getSingleResult())) {
            nativeQuery = "UPDATE users u" +
                    " SET u.enabled = 1" +
                    " WHERE u.activation_code = :ac";
            query = entityManager
                    .createNativeQuery(nativeQuery)
                    .setParameter("ac", ac);
            try {
                query.executeUpdate();
                return true;
            } catch (Exception e) {
                return false;

            }
        }
        return false;
    }
}
