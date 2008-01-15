package com.topcoder.controlpanel.clientassociations.dao.hibernate;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

class HibernateHelper {
    private static final SessionFactory sessionFactory;

    static {
        try {
            sessionFactory = new Configuration().configure().buildSessionFactory();
        } catch (Throwable e) {
            throw new ExceptionInInitializerError(e);
        }
    }

    private HibernateHelper() {
    }

    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public static void shutdown() {
        // Close caches and connection pools
        getSessionFactory().close();
    }
}
