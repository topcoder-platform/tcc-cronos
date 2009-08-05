/*
 * Copyright (C) 2009 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.studio.accuracytests;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

/**
 * <p>
 * Helper class to get the singleton hibernate
 * <code>SessionFactory</code>.
 * </p>
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class HibernateUtil {
    /**
     * Represents the SessionFactory. It is initialized in the static
     * block.
     */
    private static final SessionFactory SESSION_FACTORY;

    static {
        // Create the SessionFactory from hibernate.cfg.xml
        SESSION_FACTORY = new Configuration().configure().buildSessionFactory();
    }

    /**
     * No instance allowed.
     */
    private HibernateUtil() {
        // empty
    }

    /**
     * Returns the singleton instance.
     * @return the session factory
     */
    public static SessionFactory getSessionFactory() {
        return SESSION_FACTORY;
    }
}
