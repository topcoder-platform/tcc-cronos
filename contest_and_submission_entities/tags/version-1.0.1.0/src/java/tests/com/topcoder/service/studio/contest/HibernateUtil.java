/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.studio.contest;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import org.hibernate.ejb.Ejb3Configuration;

/**
 * <p>
 * Helper class to get the singleton hibernate <code>EntityManager</code>.
 * </p>
 *
 * @author cyberjag
 * @version 1.0
 */
public class HibernateUtil {
    /**
     * Represents the EntityManager. It is initialized in the static block.
     */
    private static final EntityManager MANAGER;

    /**
     * No instance allowed.
     */
    private HibernateUtil() {
        // empty
    }

    static {
        // Create the SessionFactory from hibernate.cfg.xml
        Ejb3Configuration cfg = new Ejb3Configuration();
        EntityManagerFactory emf = cfg.configure("hibernate.cfg.xml").buildEntityManagerFactory();
        MANAGER = emf.createEntityManager();
    }

    /**
     * Returns the singleton instance.
     *
     * @return the session factory
     */
    public static EntityManager getManager() {
        return MANAGER;
    }
}