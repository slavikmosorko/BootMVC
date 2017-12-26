package com.example.utils.hibernate;

import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class HibernateUtil {
    private static final Logger logger = Logger.getLogger(HibernateUtil.class);

    private static SessionFactory staticSessionFactory;
    @Autowired
    private SessionFactory sessionFactory;

    private HibernateUtil() {
    }

    public static final SessionFactory getSessionFactory() {
        return staticSessionFactory;
    }

    public static final Session getSession() {
        try {
            return staticSessionFactory.getCurrentSession();
        } catch (Exception e) {
            logger.error(e.getMessage());
            return staticSessionFactory.openSession();

        }
    }

    @PostConstruct
    private final void initStaticSessionFactory() {
        staticSessionFactory = this.sessionFactory;
    }

}
