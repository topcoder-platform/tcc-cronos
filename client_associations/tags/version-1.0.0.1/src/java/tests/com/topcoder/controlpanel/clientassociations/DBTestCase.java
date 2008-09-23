/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.controlpanel.clientassociations;

import org.hibernate.Query;
import org.hibernate.Session;

import com.topcoder.controlpanel.clientassociations.dao.hibernate.Client;
import com.topcoder.controlpanel.clientassociations.dao.hibernate.HibernateHelper;

import junit.framework.TestCase;

/**
 * <p>
 * The class is the base test case for database-related test cases.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public abstract class DBTestCase extends TestCase {

    /**
     * <p>
     * Setup the testing environment.
     * </p>
     *
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    @Override
    protected void setUp() throws Exception {
        super.setUp();
        cleanDatabase();

        saveEntity(new Client(1, "ivern"));
        saveEntity(new Client(2, "ivern"));
    }

    /**
     * <p>
     * Tear down the testing environment.
     * </p>
     *
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    @Override
    protected void tearDown() throws Exception {
        cleanDatabase();

        super.tearDown();
    }

    /**
     * <p>
     * Clean up the databases.
     * </p>
     */
    public static void cleanDatabase() {
        Session session = HibernateHelper.getSessionFactory().openSession();

        Query query = session.createQuery("delete CompClient");

        query.executeUpdate();

        query = session.createQuery("delete UserClient");

        query.executeUpdate();

        query = session.createQuery("delete Client");

        query.executeUpdate();

        session.flush();

        session.close();
    }

    /**
     * <p>
     * Saves the provided entity into database.
     * </p>
     *
     * @param entity
     *            the entity to save.
     */
    public static void saveEntity(Object entity) {
        Session session = HibernateHelper.getSessionFactory().openSession();

        session.save(entity);

        session.flush();

        session.close();
    }
}
