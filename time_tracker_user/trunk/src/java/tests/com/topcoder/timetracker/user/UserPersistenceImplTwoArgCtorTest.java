/*
 * Copyright (C) 2005 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.user;

import com.topcoder.db.connectionfactory.DBConnectionFactory;
import com.topcoder.db.connectionfactory.DBConnectionFactoryImpl;

/**
 * <p>
 * Tests the UserPersistenceImpl class, when instantiated with the two-arg constructor.
 * Most test methods are actually in UserPersistenceImplTestCase.
 * </p>
 *
 * @see UserPersistenceImpl
 * @see UserPersistenceImplTestCase
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class UserPersistenceImplTwoArgCtorTest extends UserPersistenceImplTestCase {

    /**
     * This dummy factory has no producers defined, and is only used for testing other exception
     * cases.
     */
    private static final DBConnectionFactory EMPTY_DB_CONNECTION_FACTORY = new DBConnectionFactoryImpl();

    /**
     * Provides a new instance of a UserPersistenceImpl object. In this class, the two-argument
     * constructor is used to instantiate the object.  The standard Connection name (InformixConnection)
     * is used, and a properly-configured DBConnectionFactory is used, as the two arguments
     * to the constructor.
     * @return a new instance of the UserPersistenceImpl class
     * @throws Exception never under normal conditions.
     */
    protected UserPersistenceImpl createUserPersistenceImpl() throws Exception {
        return new UserPersistenceImpl(CONNECTION_NAME, new DBConnectionFactoryImpl(NAMESPACE));
    }


    /**
     * Test that the 2-arg constructor throws NullPointerException if connectionName or dbFactory is
     * null.
     *
     * @throws Exception Never under normal conditions.
     */
    public void test2ArgCtorThrowsNPE() throws Exception {

        try {
            new UserPersistenceImpl(null, null);
            fail("2-arg ctor didn't throw NullPointerException");
        } catch (NullPointerException expected) {
            assertNotNull(expected);
        }

        try {
            new UserPersistenceImpl(CONNECTION_NAME, null);
            fail("2-arg ctor didn't throw NullPointerException");
        } catch (NullPointerException expected) {
            assertNotNull(expected);
        }

        try {
            new UserPersistenceImpl(null, EMPTY_DB_CONNECTION_FACTORY);
            fail("2-arg ctor didn't throw NullPointerException");
        } catch (NullPointerException expected) {
            assertNotNull(expected);
        }
    }


    /**
     * Test that the 2-arg constructor throws IllegalArgumentException if connectionName is the
     * empty String.
     *
     * @throws Exception Never under normal conditions.
     */
    public void test2ArgCtorThrowsIAE() throws Exception {

        try {
            new UserPersistenceImpl("", EMPTY_DB_CONNECTION_FACTORY);
            fail("2-arg ctor didn't throw IllegalArgumentException");
        } catch (IllegalArgumentException expected) {
            assertNotNull(expected);
        }
    }


    /**
     * Test that the 2-arg constructor throws PersistenceException if the connectionName was
     * not recognized by the DBConnectionFactory.
     *
     * @throws Exception Never under normal conditions.
     */
    public void test2ArgCtorThrowsPersistenceException() throws Exception {

        try {
            new UserPersistenceImpl("name", new DBConnectionFactoryImpl(NAMESPACE));
            fail("2-arg ctor didn't throw PersistenceException");
        } catch (PersistenceException expected) {
            assertNotNull(expected);
        }

        try {
            // empty connection factory!
            new UserPersistenceImpl("name", new DBConnectionFactoryImpl());
            fail("2-arg ctor didn't throw PersistenceException");
        } catch (PersistenceException expected) {
            assertNotNull(expected);
        }
    }
}
