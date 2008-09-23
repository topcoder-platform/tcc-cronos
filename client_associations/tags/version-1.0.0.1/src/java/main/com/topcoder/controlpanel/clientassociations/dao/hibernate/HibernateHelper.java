/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.controlpanel.clientassociations.dao.hibernate;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

/**
 * <p>
 * This helper class is used to provide a central SessionFactory for the whole application(like a singleton
 * SessionFactory) as creating a SessionFactory is very expensive and it's safe to share a SessionFactory between
 * different threads concurrently.
 * </p>
 * <p>
 * <b>Thread Safety</b>: This class is thread safe as it's immutable.
 * </p>
 *
 * @author TCSDESIGNER, TCSDEVELOPER
 * @version 1.0
 */
public class HibernateHelper {
    /**
     * <p>
     * Represents the SessionFactory from which we will get Sessions.
     * </p>
     * <p>
     * This field is initialized in a static code block and will not change afterwards, and it will never be null. The
     * reason to keep this field static is creating a SessionFactory is really expensive, and multiple daos could share
     * the same SessionFactory without causing any problem. This field is accessed in the getSessionFactory() static
     * method.
     * <p>
     */
    private static final SessionFactory SESSION_FACTORY;

    /**
     * <p>
     * Staticly built the session factory used in this component.
     * </p>
     */
    static {
        try {
            SESSION_FACTORY = new Configuration().configure().buildSessionFactory();
        } catch (Throwable e) {
            throw new ExceptionInInitializerError(e);
        }
    }

    /**
     * An empty do-nothing constructor. This constructor is private to prevent this class being instantiated.
     */
    private HibernateHelper() {
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
