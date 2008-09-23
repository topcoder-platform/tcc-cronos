/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */

package com.topcoder.controlpanel.clientassociations.stresstests;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

/**
 * <p>
 * Helper class for stress test.
 * </p>
 * <p>
 * <strong>Thread Safety:</strong>
 * </p>
 * 
 * @author smallka
 * @version 1.0
 */
public class StressTestHelper {

    /**
     * <p>
     * Represents the session factory to use.
     * </p>
     */
    private static final SessionFactory SESSION_FACTORY;

    /**
     * <p>
     * The statements to execute to clear the database.
     * </p>
     */
    private static final String[] STATEMENTS =
        new String[] {"delete CompClient", "delete UserClient", "delete Client"};

    static {
        try {
            SESSION_FACTORY = new Configuration().configure().buildSessionFactory();
        } catch (Throwable e) {
            throw new ExceptionInInitializerError(e);
        }
    }

    /**
     * <p>
     * Empty constructor.
     * </p>
     */
    private StressTestHelper() {
    }

    /**
     * <p>
     * Clears the data the databases.
     * </p>
     */
    public static void clearDB() {
        Session session = getSessionFactory().openSession();

        for (String stmt : STATEMENTS) {
            session.createQuery(stmt).executeUpdate();
        }

        session.flush();

        session.close();
    }

    /**
     * <p>
     * Saves the provided entity in database.
     * </p>
     * 
     * @param entity
     *            the entity to save.
     */
    public static void saveEntity(Object entity) {
        Session session = getSessionFactory().openSession();

        session.save(entity);

        session.flush();

        session.close();
    }

    /**
     * <p>
     * Returns a SessionFactory instance.
     * </p>
     * 
     * @return the SessionFactory instance.
     */
    public static SessionFactory getSessionFactory() {
        return SESSION_FACTORY;
    }
}
