/*
 * Copyright (C) 2009 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.studio.contest;

import javax.persistence.EntityManager;
import javax.persistence.Persistence;

/**
 * <p>
 * Helper class to get the singleton hibernate <code>EntityManager</code>.
 * </p>
 *
 * @author cyberjag, TCSDEVELOPER
 * @version 1.2
 */
public class HibernateUtil {
    /**
     * Represents the EntityManager. It is initialized in the static block.
     */
    private static final EntityManager MANAGER = Persistence.createEntityManagerFactory("contest_submission")
            .createEntityManager();;

    /**
     * No instance allowed.
     */
    private HibernateUtil() {
        // empty
    }

    /**
     * Returns the singleton instance.
     *
     * @return the entity manager
     */
    public static EntityManager getManager() {
        return MANAGER;
    }
}