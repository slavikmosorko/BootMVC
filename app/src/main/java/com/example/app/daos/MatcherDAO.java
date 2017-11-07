package com.example.app.daos;

import com.example.app.models.Matcher;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class MatcherDAO implements IMatcherDAO {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Matcher> getAllMatchers() {
        String hql = "FROM Matcher as mt ORDER BY mt.id";
        return (List<Matcher>) entityManager.createQuery(hql).getResultList();
    }
}
